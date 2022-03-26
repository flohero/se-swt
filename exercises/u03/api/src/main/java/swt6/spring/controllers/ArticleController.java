package swt6.spring.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swt6.spring.dtos.BidDto;
import swt6.spring.dtos.BidForCreationDto;
import swt6.spring.model.Bid;
import swt6.spring.services.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final BidService bidService;
    private final ModelMapper mapper;

    public ArticleController(BidService bidService, ModelMapper mapper) {
        this.bidService = bidService;
        this.mapper = mapper;
    }

    @GetMapping("{id}/bids")
    public Stream<BidDto> bidsForArticle(@PathVariable Long id) {
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
        }
    }

}
