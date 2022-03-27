package swt6.spring.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swt6.spring.dtos.ArticleDto;
import swt6.spring.dtos.ArticleForCreationDto;
import swt6.spring.dtos.BidDto;
import swt6.spring.dtos.BidForCreationDto;
import swt6.spring.model.Article;
import swt6.spring.model.Bid;
import swt6.spring.model.BiddingState;
import swt6.spring.services.*;
import swt6.spring.services.exceptions.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final BidService bidService;
    private final ArticleService articleService;
    private final ModelMapper mapper;

    public AuctionController(BidService bidService, ArticleService articleService, ModelMapper mapper) {
        this.bidService = bidService;
        this.articleService = articleService;
        this.mapper = mapper;
    }

    @GetMapping("{id}/bids")
    public Stream<BidDto> bidsForArticle(@PathVariable Long id) {
        System.out.println("HERE");
        try {
            return bidService.findBidsForArticleId(id)
                    .stream()
                    .map(bid -> mapper.map(bid, BidDto.class));
        } catch (ArticleNotFoundException ae) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found"
            );
        }
    }

    @PostMapping("{id}/bids")
    public BidDto placeBidForArticle(@PathVariable Long id, @RequestBody BidForCreationDto bidDto) {
        try {
            return mapper.map(
                    bidService.placeBidForArticle(id, mapper.map(bidDto, Bid.class)),
                    BidDto.class
            );
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found"
            );
        } catch (CustomerNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "customer not found"
            );
        } catch (SellerAndBuyerEqualException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "seller and buyer can't be the same customer"
            );
        } catch (DuplicatedBidException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "customer already placed a bid on article"
            );
        } catch (ArticleNotForSaleException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "article not for sale"
            );
        }
    }

    @GetMapping("{id}")
    public ArticleDto getArticle(@PathVariable Long id) {
        try {
            return mapper.map(articleService.findById(id), ArticleDto.class);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found"
            );
        }
    }

    @GetMapping
    public Stream<ArticleDto> getArticles(@RequestParam BiddingState state) {
        return articleService.findArticles(state)
                .stream()
                .map(a -> mapper.map(a, ArticleDto.class));
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody ArticleForCreationDto articleDto) {
        try {
            return mapper.map(articleService.createArticle(mapper.map(articleDto, Article.class)), ArticleDto.class);
        } catch(InvalidArticleException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "article is not valid"
            );
        } catch(CustomerNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "customer not found"
            );
        }
    }

}
