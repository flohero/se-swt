package swt6.spring.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swt6.spring.dtos.ArticleDto;
import swt6.spring.dtos.BidDto;
import swt6.spring.dtos.CustomerDto;

import java.time.LocalDate;

@ShellComponent
@ShellCommandGroup("Bid Commands")
public class BidCommands {
    private final WebClient webClient;

    public BidCommands(WebClient webClient) {
        this.webClient = webClient;
    }

    @ShellMethod("List bids for an article with id")
    public String bidsForArticle(@ShellOption Long id) {
        return webClient.get()
                .uri(builder -> builder
                        .path("/{id}/bids")
                        .build(id)
                )
                .retrieve()
                .bodyToFlux(BidDto.class)
                .map(Object::toString)
                .reduce((rest, bid) -> rest + "\n" + bid)
                .onErrorResume(e -> Mono.just(String.format("Could not retrieve bids for article %s", id)))
                .block();
    }

    @ShellMethod("Place bid for article with id")
    public String placeBid(@ShellOption Long id,
                           @ShellOption Long asCustomer,
                           @ShellOption Double amount) {
        BidDto bid = new BidDto(amount, LocalDate.now(), new ArticleDto(id), new CustomerDto(asCustomer));
        return webClient.post()
                .uri(builder -> builder.path("/{id}/bids").build(id))
                .body(Mono.just(bid), BidDto.class)
                .retrieve()
                .bodyToMono(BidDto.class)
                .map(a -> String.format("Created Bid %s", a.getId()))
                .onErrorResume(e -> Mono.just("Could not create bid"))
                .block();
    }
}
