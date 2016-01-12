package com.walmart.homework.utils;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.walmart.homework.service.TicketReleaseService;

@Component
public class TicketReleaseWorkerThread implements Runnable {
	
	@Resource
	private TicketReleaseService ticketReleaseService;

	@Override
	public void run() {
		ticketReleaseService.releaseExpiredSeats();
	}

}
