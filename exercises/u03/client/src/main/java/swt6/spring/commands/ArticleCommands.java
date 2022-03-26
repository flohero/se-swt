package swt6.spring.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;
import swt6.spring.dtos.ArticleDto;
import swt6.spring.model.BiddingState;

@ShellComponent
@ShellCommandGroup("Article Commands")
public class ArticleCommands {

    private final WebClient webClient;

    public ArticleCommands(WebClient webClient) {
        this.webClient = webClient;
    }

    @ShellMethod("Show all articles")
    public String listArticles(@ShellOption(defaultValue = "SOLD") BiddingState biddingState) {
        // Have to block this call, since spring shell will just toString the Flux
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("state", biddingState.toString())
                        .build()
                )
                .retrieve()
                .bodyToFlux(ArticleDto.class)
                .map(Object::toString)
                .reduce((rest, article) -> String.join(" \n", rest, article))
                .block();
    }
}
