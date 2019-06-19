package com.server.commIt.controller;

import com.server.commIt.errors.IllegalNameException;
import com.server.commIt.errors.IllegalPriceException;
import com.server.commIt.errors.QuoteNotFoundException;
import com.server.commIt.model.Quote;
import com.server.commIt.errors.DuplicateQuoteException;
import com.server.commIt.model.QuoteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api")
public class quoteController {

    DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");

    @Autowired
    private com.server.commIt.repository.quoteRepository quoteRepository;
    @Autowired
    private com.server.commIt.repository.quoteLogRepository quoteLogRepository;

    /**
     * Get all quotes.
     *
     * @return the list
     */
    @GetMapping("/quotes")
    public List<Quote> getAllQuotes() {
        List<Quote> allQutes = quoteRepository.findAll();
        allQutes.removeIf(quote -> quote.getDel() == 1);
        return allQutes;
    }

    /**
     * Gets quotes by name.
     *
     * @param quoteName the quote name
     * @return the quote by the name
     */
    @GetMapping("/quotes/{quote_name}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable(value = "quote_name") String quoteName) throws QuoteNotFoundException {
        Optional<Quote> quote = quoteRepository.findById(quoteName);
        if (quote.isPresent() && quote.get().getDel() != 1) {
            return ResponseEntity.ok().body(quote.get());
        }
        throw new QuoteNotFoundException("Quote Not Found!");

    }

    /**
     * Create Quote quote.
     *
     * @param quote the quote to create
     * @return the quote
     */
    @PostMapping("/quotes")
    public ResponseEntity<Quote> createQuote(@Valid @RequestBody Quote quote) throws DuplicateQuoteException, IllegalNameException, IllegalPriceException {
        Date date = new Date();
        Optional<Quote> optionalQuote = quoteRepository.findById(quote.getQuoteName());
        if (optionalQuote.isPresent()) {
            quoteLogRepository.save(new QuoteLog(date, quote.getQuoteName(), QuoteLog.Operation.CREATE, 1, "Duplicated Quote"));
            throw new DuplicateQuoteException();
        } else if (quote.getQuoteName() == null || quote.getQuoteName().isEmpty()) {
            quoteLogRepository.save(new QuoteLog(date, quote.getQuoteName(), QuoteLog.Operation.CREATE, 2, "Illegal Name"));
            throw new IllegalNameException();
        } else if (quote.getPrice() < 0) {
            quoteLogRepository.save(new QuoteLog(date, quote.getQuoteName(), QuoteLog.Operation.CREATE, 3, "Illegal Price"));
            throw new IllegalPriceException();
        }
        quoteLogRepository.save(new QuoteLog(date, quote.getQuoteName(), QuoteLog.Operation.CREATE, 0, "Successful Created"));
        return ResponseEntity.ok().body(quoteRepository.save(quote));
    }

    /**
     * Update quote entity.
     *
     * @param quoteName    the quote name
     * @param quoteDetails the quote details
     * @return the response entity
     */
    @PutMapping("/quotes/{quote_name}")
    public ResponseEntity<Quote> updateQuote(@PathVariable(value = "quote_name") String quoteName, @Valid @RequestBody Quote quoteDetails) throws IllegalNameException, QuoteNotFoundException, IllegalPriceException, DuplicateQuoteException {
        Date date = new Date();
        Optional<Quote> optionalQuote = quoteRepository.findById(quoteName);
        if (optionalQuote.isPresent() && optionalQuote.get().getDel() != 1) {
            if (!quoteDetails.getQuoteName().equals(quoteName) && quoteRepository.findById(quoteDetails.getQuoteName()).isPresent()) {
                quoteLogRepository.save(new QuoteLog(date, quoteDetails.getQuoteName(), QuoteLog.Operation.UPDATE, 1, "Duplicated Quote"));
                throw new DuplicateQuoteException();
            }
            if (quoteDetails.getQuoteName() == null || quoteDetails.getQuoteName().isEmpty()) {
                quoteLogRepository.save(new QuoteLog(date, quoteName, QuoteLog.Operation.UPDATE, 2, "Illegal Name"));
                throw new IllegalNameException();
            } else if (quoteDetails.getPrice() < 0) {
                quoteLogRepository.save(new QuoteLog(date, quoteDetails.getQuoteName(), QuoteLog.Operation.UPDATE, 3, "Illegal Price"));
                throw new IllegalPriceException();
            }
            Quote quote = optionalQuote.get();
            quote.setQuoteName(quoteDetails.getQuoteName());
            quote.setPrice(quoteDetails.getPrice());
            quote.setItems(quoteDetails.getItems());
            Quote updatedQuote = quoteRepository.save(quote);
            quoteLogRepository.save(new QuoteLog(date, quote.getQuoteName(), QuoteLog.Operation.UPDATE, 0, "Successful Updated"));
            return ResponseEntity.ok().body(updatedQuote);
        }
        quoteLogRepository.save(new QuoteLog(date, quoteName, QuoteLog.Operation.UPDATE, 404, "Not Found"));
        throw new QuoteNotFoundException("Quote Not Found!");
    }

    /**
     * Delete quote.
     *
     * @param quoteName the quote name
     * @return the quote
     * @throws Exception the exception
     */
    @DeleteMapping("/quotes/{quote_name}")
    public ResponseEntity<Quote> deleteQuote(@PathVariable(value = "quote_name") String quoteName) throws Exception {
        Date date = new Date();
        Optional<Quote> optionalQuote = quoteRepository.findById(quoteName);
        if (optionalQuote.isPresent() && optionalQuote.get().getDel() != 1) {
            Quote quote = optionalQuote.get();
            quote.setDel(1);
            quoteRepository.save(quote);
            quoteLogRepository.save(new QuoteLog(date, quote.getQuoteName(), QuoteLog.Operation.DELETE, 0, "Successful Deleted"));
            return ResponseEntity.ok().body(quote);
        }
        quoteLogRepository.save(new QuoteLog(date, quoteName, QuoteLog.Operation.DELETE, 404, "Not Found"));
        throw new QuoteNotFoundException("Quote Not Found!");
    }
}

