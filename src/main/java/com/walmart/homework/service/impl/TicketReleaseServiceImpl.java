package com.walmart.homework.service.impl;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.walmart.homework.data.SeatHold;
import com.walmart.homework.data.SeatHoldMapping;
import com.walmart.homework.data.TicketData;
import com.walmart.homework.repository.SeatHoldRepository;
import com.walmart.homework.repository.TicketDataRepository;
import com.walmart.homework.service.TicketReleaseService;

@Service
public class TicketReleaseServiceImpl implements TicketReleaseService {
	
	@Resource
	private SeatHoldRepository seatHoldRepository;
	
	@Resource
	private TicketDataRepository ticketDataRepository;
	
	@Value("${ticket.expired.seconds}")
	private Integer expiredTime;


	@Transactional(value = TxType.REQUIRES_NEW)
	@Override
	public void releaseExpiredSeats() {

		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, expiredTime);
		
		List<SeatHold> expiredSeats = seatHoldRepository.findExpiredSeats(c.getTime());
		if( expiredSeats != null && !expiredSeats.isEmpty() ) {
			for( SeatHold seatHold : expiredSeats ) {
				Iterator<SeatHoldMapping> seatHoldMappings = seatHold.getSeatHoldMappings().iterator();
				while( seatHoldMappings.hasNext() ) {
					SeatHoldMapping next = seatHoldMappings.next();
					TicketData findByLevelId = ticketDataRepository.findByLevelId(next.getLevel());
					findByLevelId.setTotalSeats(findByLevelId.getTotalSeats() + next.getNumberOfSeats());
					ticketDataRepository.save(findByLevelId);
				}
				seatHoldRepository.delete(seatHold);
			}
		}
	}

}
