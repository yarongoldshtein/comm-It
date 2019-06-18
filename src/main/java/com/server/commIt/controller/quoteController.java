package com.server.commIt.controller;

import com.server.commIt.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api")
public class quoteController {


    @Autowired
    private com.server.commIt.repository.quoteRepository quoteRepository;

    /**
     * Get all quotes.
     *
     * @return the list
     */
    @GetMapping("/quotes")
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    /**
     * Gets quotes by name.
     *
     * @param quoteName the quote name
     * @return the quote by the name
     */
    @GetMapping("/quotes/{quote_name}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable(value = "quote_name") String quoteName) {
        Optional<Quote> quote = quoteRepository.findById(quoteName);
        if (quote.isPresent()) {
            return ResponseEntity.ok().body(quote.get());
        }
        return ResponseEntity.ok().body(null);

    }

    /**
     * Create Quote quote.
     *
     * @param quote the quote to create
     * @return the quote
     */
    @PostMapping("/quotes")
    public Quote createQuote(@Valid @RequestBody Quote quote) {
        Optional<Quote> optionalQuote = quoteRepository.findById(quote.getQuoteName());
        if (optionalQuote.isPresent()) {
            Quote quote1 = new Quote();
            quote1.setQuoteName("bad");
           return quote1;
        }
        return quoteRepository.save(quote);
    }

    /**
     * Update quote entity.
     *
     * @param quoteName the quote name
     * @param quoteDetails the quote details
     * @return the response entity
     */
    @PutMapping("/quotes/{quote_name}")
    public ResponseEntity<Quote> updateQuote(@PathVariable(value = "quote_name") String quoteName, @Valid @RequestBody Quote quoteDetails) {
        Quote quote;
        Optional<Quote> optionalQuote = quoteRepository.findById(quoteName);
        if (optionalQuote.isPresent()) {
            quote = optionalQuote.get();
            quote.setQuoteName(quoteDetails.getQuoteName());
            quote.setPrice(quoteDetails.getPrice());
            quote.setItems(quoteDetails.getItems());
            final Quote updatedQuote = quoteRepository.save(quote);
            return ResponseEntity.ok().body(updatedQuote);
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * Delete quote.
     *
     * @param quoteName the quote name
     * @return the quote
     * @throws Exception the exception
     */
    @DeleteMapping("/quotes/{quote_name}")
    public ResponseEntity<Quote>  deleteQuote(@PathVariable(value = "quote_name") String quoteName) throws Exception {

        Optional<Quote> quote = quoteRepository.findById(quoteName);
        if (quote.isPresent()) {
            quoteRepository.delete(quote.get());
            return ResponseEntity.ok().body(quote.get());
        }
        return ResponseEntity.ok().body(null);
    }
}

