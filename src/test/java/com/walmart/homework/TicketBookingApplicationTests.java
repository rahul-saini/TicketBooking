package com.walmart.homework;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicketBookingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:9999")
public class TicketBookingApplicationTests {

	private RestTemplate rest = new TestRestTemplate();
	private static final String BASE_URL = "http://localhost:9999/movie";
	
	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void testAvailableSeats() throws InterruptedException {
		
		ResponseEntity<String> forEntity = rest.getForEntity(BASE_URL + "/ticket", String.class);
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(350));
		
		forEntity = rest.getForEntity(BASE_URL + "/getAvailableSeats/1", String.class);
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(50));
		
		forEntity = rest.getForEntity(BASE_URL + "/getAvailableSeats/2", String.class);
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(100));
		
		forEntity = rest.getForEntity(BASE_URL + "/getAvailableSeats/3", String.class);
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(100));
		
		forEntity = rest.getForEntity(BASE_URL + "/getAvailableSeats/4", String.class);
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(100));
		
		ResponseEntity<Long> findAndHoldSeats = rest.postForEntity(BASE_URL + "/findAndHoldSeats/50/1/2/a@a.com", null, Long.class, new Object[]{});
		Assert.assertNotNull(findAndHoldSeats.getBody());
		
		forEntity = rest.getForEntity(BASE_URL + "/getAvailableSeats/1", String.class);
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(0));
		System.out.println("Tickets at level 1: " + forEntity.getBody());
		
		System.out.println("Start to sleep.");
		Thread.sleep(35000); //Sleep for 35 seconds to expire.
		System.out.println("Sleep End.");
		
		forEntity = rest.getForEntity(BASE_URL + "/getAvailableSeats/1", String.class);
		System.out.println("Tickets at level 1: " + forEntity.getBody());
		Assert.assertTrue(Integer.valueOf(forEntity.getBody()).equals(50));
		
	}

}
