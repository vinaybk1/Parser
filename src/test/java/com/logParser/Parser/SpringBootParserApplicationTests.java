package com.logParser.Parser;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringBootParserApplicationTests 
{
	@LocalServerPort
    int randomServerPort;
	
    @Test
    public void testGetTotalRequests() throws URISyntaxException 
    {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/getTotalRequests";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<Long> result = restTemplate.getForEntity(uri, Long.class);
         
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().longValue()>0);
    }  
    
    @Test
    public void testGetFailedRequests() throws URISyntaxException 
    {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/getFailedRequests";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<Long> result = restTemplate.getForEntity(uri, Long.class);
         
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().longValue()>0);    } 
    
    @Test
    public void testGetAverageResponseTime() throws URISyntaxException 
    {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/getAverageResponseTime";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<Double> result = restTemplate.getForEntity(uri, Double.class);
         
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().doubleValue()>0);
    } 
    
    @Test
    public void testGet99Percentile() throws URISyntaxException 
    {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/get99Percentile";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<Double> result = restTemplate.getForEntity(uri, Double.class);
         
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().doubleValue()>0);
    } 
    
    @Test
    public void testGet95Percentile() throws URISyntaxException 
    {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/get99Percentile";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<Double> result = restTemplate.getForEntity(uri, Double.class);
         
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().doubleValue()>0);
    } 
}