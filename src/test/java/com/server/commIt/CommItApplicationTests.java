package com.server.commIt;

import com.server.commIt.model.Quote;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommItApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommItApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllQuotes() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/quotes",
                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testCreateQuote() {
        Quote quote = new Quote();
        quote.setQuoteName("unitTest1");
        quote.setPrice(10);
        quote.setItems("items");

        ResponseEntity<Quote> postResponse = restTemplate.postForEntity(getRootUrl() + "/quotes", quote, Quote.class);
        Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Assert.assertEquals(quote.getQuoteName(), Objects.requireNonNull(postResponse.getBody()).getQuoteName());
        Assert.assertEquals(quote.getPrice(), Objects.requireNonNull(postResponse.getBody()).getPrice());
        Assert.assertEquals(quote.getItems(), Objects.requireNonNull(postResponse.getBody()).getItems());
        Assert.assertEquals(0, Objects.requireNonNull(postResponse.getBody()).getDel());
    }

    @Test
    public void testIllegalNameCreateQuote() {
        Quote quote = new Quote();
        quote.setQuoteName("");
        quote.setPrice(10);
        quote.setItems("items");

        ResponseEntity<Quote> postResponse = restTemplate.postForEntity(getRootUrl() + "/quotes", quote, Quote.class);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, postResponse.getStatusCode());
    }

    @Test
    public void testIllegalPriceCreateQuote() {
        Quote quote = new Quote();
        quote.setQuoteName("UnitTest-1");
        quote.setPrice(-1);
        quote.setItems("items");

        ResponseEntity<Quote> postResponse = restTemplate.postForEntity(getRootUrl() + "/quotes", quote, Quote.class);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, postResponse.getStatusCode());
    }

    @Test
    public void testGetQuoteByName() {
        Quote quote = restTemplate.getForObject(getRootUrl() + "/quotes/unitTest1", Quote.class);
        Assert.assertEquals("unitTest1", quote.getQuoteName());
        Assert.assertEquals(10, quote.getPrice());
        Assert.assertEquals("items", quote.getItems());
    }

    @Test
    public void testUpdatePost() {
        Quote quote = restTemplate.getForObject(getRootUrl() + "/quotes/unitTest1", Quote.class);
        quote.setPrice(20);
        restTemplate.put(getRootUrl() + "/quotes/unitTest1", quote);

        Quote updatedQuote = restTemplate.getForObject(getRootUrl() + "/quotes/unitTest1", Quote.class);
        Assert.assertEquals(20, updatedQuote.getPrice());
    }

    @Test
    public void testDeletePost() {
        Quote quote = restTemplate.getForObject(getRootUrl() + "quotes/unitTest1", Quote.class);
        Assert.assertNotNull(quote);

        restTemplate.delete(getRootUrl() + "/quotes/" + quote.getQuoteName());

        quote = restTemplate.getForObject(getRootUrl() + "/quotes/" + quote.getQuoteName(), Quote.class);
        Assert.assertEquals(1, quote.getDel());
    }

}


