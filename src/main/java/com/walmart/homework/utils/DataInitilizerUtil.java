package com.walmart.homework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.walmart.homework.data.TicketData;
import com.walmart.homework.repository.TicketDataRepository;

@Component
public class DataInitilizerUtil {

	@Resource
	private TicketDataRepository ticketRepository;
	
	private ScheduledFuture<?> future;
	
	@Resource
	private TicketReleaseWorkerThread ticketReleaseWorkerThread;
	
	@Value("${data.clean.thread.period}")
	private Long period;
	
	@PostConstruct
	public void init() {
		List<TicketData> tickets = new ArrayList<>();

		TicketData ticket1 = new TicketData();
		ticket1.setLevelId(1);
		ticket1.setLevelName("Orchestra");
		ticket1.setPrice(100d);
		ticket1.setRows(25);
		ticket1.setSeatsInRow(50);
		ticket1.setTotalSeats(25 * 50);
		
		tickets.add(ticket1);
		
		TicketData ticket2 = new TicketData();
		ticket2.setLevelId(2);
		ticket2.setLevelName("Main");
		ticket2.setPrice(75d);
		ticket2.setRows(20);
		ticket2.setSeatsInRow(100);
		ticket2.setTotalSeats(20 * 100);
		
		tickets.add(ticket2);
		
		TicketData ticket3 = new TicketData();
		ticket3.setLevelId(3);
		ticket3.setLevelName("Balcony 1");
		ticket3.setPrice(50d);
		ticket3.setRows(15);
		ticket3.setSeatsInRow(100);
		ticket3.setTotalSeats(15 * 100);
		
		tickets.add(ticket3);
		
		TicketData ticket4 = new TicketData();
		ticket4.setLevelId(4);
		ticket4.setLevelName("Balcony 2");
		ticket4.setPrice(40d);
		ticket4.setRows(15);
		ticket4.setSeatsInRow(100);
		ticket4.setTotalSeats(15 * 100);
		
		tickets.add(ticket4);
		
		ticketRepository.save(tickets);
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		future = executor.scheduleAtFixedRate(ticketReleaseWorkerThread, 0, period, TimeUnit.SECONDS); 
	}
	
	@PreDestroy
	public void destroy() {
		if( future != null ) {
			future.cancel(true);
		}
	}
	
}
