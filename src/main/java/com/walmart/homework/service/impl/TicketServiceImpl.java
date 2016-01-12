package com.walmart.homework.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.walmart.homework.data.Optional;
import com.walmart.homework.data.SeatHold;
import com.walmart.homework.data.SeatHoldMapping;
import com.walmart.homework.data.TicketData;
import com.walmart.homework.exceptions.InvalidLevelException;
import com.walmart.homework.exceptions.NotAbleToHoldSeatsException;
import com.walmart.homework.exceptions.NotAbleToReserveSeatException;
import com.walmart.homework.repository.SeatHoldRepository;
import com.walmart.homework.repository.TicketDataRepository;
import com.walmart.homework.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Resource
	private TicketDataRepository ticketDataRepository;
	
	@Resource
	private SeatHoldRepository seatHoldRepository;

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) throws InvalidLevelException {
		
		int numberOfSeatsAvailable = 0;
		
		if( venueLevel != null ) {
			TicketData ticketData = ticketDataRepository.findByLevelId(venueLevel.getValue());
			if( ticketData != null ) {
				numberOfSeatsAvailable = ticketData.getTotalSeats();
			} else {
				throw new InvalidLevelException();
			}
		} else {
			numberOfSeatsAvailable = ticketDataRepository.findAllAvailableSeats();
		}
		
		return numberOfSeatsAvailable;
	}

	@Transactional
	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) throws NotAbleToHoldSeatsException {
		
		SeatHold seatHold = null;
		List<TicketData> listOfTicketsToHold = Collections.emptyList();
		
		if( minLevel != null && maxLevel != null ) {
			
			if( numSeats <= ticketDataRepository.findNumberOfSeatsByMinAndMaxLevel(minLevel.getValue(), maxLevel.getValue()) ) {
				listOfTicketsToHold = ticketDataRepository.findByMinAndMaxLevel(minLevel.getValue(), maxLevel.getValue());
			}
			
		} else if( minLevel == null && maxLevel != null ) {
			
			if( numSeats <= ticketDataRepository.findNumberOfSeatsByMaxLevel(maxLevel.getValue()) ) {
				listOfTicketsToHold = ticketDataRepository.findByMaxLevel(maxLevel.getValue());
			}
			
		} else if( minLevel != null && maxLevel == null ) {
			
			if( numSeats <= ticketDataRepository.findNumberOfSeatsByMaxLevel(minLevel.getValue()) ) {
				listOfTicketsToHold = ticketDataRepository.findByMinLevel(minLevel.getValue());
			}
			
		} else {
			//Both are null
			if( numSeats <= ticketDataRepository.findAllAvailableSeats() ) {
				listOfTicketsToHold = ticketDataRepository.findAll();
			}
		}

		int tempNumSeats = numSeats;
		
		seatHold = new SeatHold();
		seatHold.setCustomerEmail(customerEmail);
		seatHold.setHoldTime(new Date());
		seatHold.setPaymentPending(true);
		Set<SeatHoldMapping> seatHoldMappings = new HashSet<>();
		seatHold.setSeatHoldMappings(seatHoldMappings);
		
		for( TicketData ticketData : listOfTicketsToHold ) {
			
			if( tempNumSeats <= 0 ) {
				break;
			}
			
			if( ticketData.getTotalSeats() <= tempNumSeats ) {
				SeatHoldMapping seatHoldMapping = new SeatHoldMapping();
				seatHoldMapping.setLevel(ticketData.getLevelId());
				seatHoldMapping.setNumberOfSeats(ticketData.getTotalSeats());
				seatHoldMapping.setSeatHold(seatHold);
				seatHoldMappings.add(seatHoldMapping);
				
				tempNumSeats -= ticketData.getTotalSeats();
				ticketData.setTotalSeats(0);
				
			} else {
				SeatHoldMapping seatHoldMapping = new SeatHoldMapping();
				seatHoldMapping.setLevel(ticketData.getLevelId());
				seatHoldMapping.setNumberOfSeats(tempNumSeats);
				seatHoldMapping.setSeatHold(seatHold);
				seatHoldMappings.add(seatHoldMapping);
				
				ticketData.setTotalSeats(ticketData.getTotalSeats() - tempNumSeats);
				tempNumSeats = 0;
			}
		}
		
		if( !seatHold.getSeatHoldMappings().isEmpty() ) {
			seatHold = seatHoldRepository.save(seatHold);
		} else {
			throw new NotAbleToHoldSeatsException();
		}
		
		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) throws NotAbleToReserveSeatException {
		
		SeatHold findOne = seatHoldRepository.findOne(Long.valueOf(seatHoldId));
		if( findOne != null ) {
			findOne.setPaymentPending(false); //Payment done, do not release.
			seatHoldRepository.save(findOne);
		} else {
			throw new NotAbleToReserveSeatException();
		}
		
		return String.valueOf(findOne.getId());
	}

}
