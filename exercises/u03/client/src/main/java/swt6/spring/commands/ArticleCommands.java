package swt6.spring.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swt6.spring.dtos.ArticleDto;
import swt6.spring.dtos.ArticleForCreationDto;
import swt6.spring.dtos.CategoryDto;
import swt6.spring.dtos.CustomerDto;
import swt6.spring.model.BiddingState;

import java.time.LocalDate;

@ShellComponent
@ShellCommandGroup("Article Commands")
public class ArticleCommands {

    private final WebClient webClient;

    public ArticleCommands(WebClient webClient) {
        this.webClient = webClient;
    }

    @ShellMethod("Show all articles")
    public String listArticles(@ShellOption(defaultValue = "") BiddingState biddingState, @ShellOption(defaultValue = "") String query) {
        return webClient.get()
                .uri(uriBuilder -> {
                            if (biddingState != null) {
                                uriBuilder.queryParam("state", biddingState.toString());
                            }
                            return uriBuilder.queryParam("query", query).build();
                        }
                )
                .retrieve()
                .bodyToFlux(ArticleDto.class)
                .map(Object::toString)
                .switchIfEmpty(Mono.just("No articles found"))
                .reduce((rest, article) -> String.join(" \n", rest, article)) // reduce the flux to a readable String
                .onErrorResume(e -> Mono.just("Could not read articles from server")) // Top error handling
                .block(); // Have to block this call, since spring shell will just toString the Flux
    }

    @ShellMethod("Create Article")
    public String createArticle(@ShellOption String name,
                                @ShellOption String description,
                                @ShellOption double startPrice,
                                @ShellOption LocalDate startDate,
                                @ShellOption LocalDate endDate,
                                @ShellOption Long asCustomer,
                                @ShellOption BiddingState state,
                                @ShellOption String category) {
        //create-article bla bla 2000 2022-03-27 2022-03-28 1 FOR_SALE Animals
        ArticleForCreationDto article = new ArticleForCreationDto(
                name,
                description,
                startPrice,
                startDate,
                endDate,
                new CustomerDto(asCustomer),
                state,
                new CategoryDto(category)
        );
        return webClient.post()
                .body(Mono.just(article), ArticleForCreationDto.class)
                .retrieve()
                .bodyToMono(ArticleDto.class)
                .map(a -> String.format("Created Article %s", a.getId()))
                .onErrorResume(e -> Mono.just("Could not create article"))
                .block();
    }
}
