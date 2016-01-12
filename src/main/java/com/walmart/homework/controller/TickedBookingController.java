package com.walmart.homework.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.homework.data.Optional;
import com.walmart.homework.exceptions.InvalidLevelException;
import com.walmart.homework.exceptions.NotAbleToHoldSeatsException;
import com.walmart.homework.exceptions.NotAbleToReserveSeatException;
import com.walmart.homework.service.TicketService;


@RestController
@RequestMapping(value = "/movie")
public class TickedBookingController {

	@Resource
	private TicketService ticketService;
	
	
	@RequestMapping(value="/getAvailableSeats/{levelId}", method = RequestMethod.GET)
	public ResponseEntity<String> getAvailableSeats(@PathVariable Integer levelId) throws InvalidLevelException {
		return new ResponseEntity<>(String.valueOf(ticketService.numSeatsAvailable(new Optional<Integer>(levelId))), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAvailableSeats", method = RequestMethod.GET)
	public ResponseEntity<String> getAvailableSeats() throws InvalidLevelException {
		return new ResponseEntity<>(String.valueOf(ticketService.numSeatsAvailable(null)), HttpStatus.OK);
	}
	
	@RequestMapping(value="/findAndHoldSeats/{numSeats}/{minLevel}/{maxLevel}/{customerEmail}", method = RequestMethod.POST)
	public ResponseEntity<Long> findAndHoldSeats(@PathVariable Integer numSeats, @PathVariable Integer minLevel, @PathVariable Integer maxLevel, @PathVariable String customerEmail) throws NotAbleToHoldSeatsException {
		return new ResponseEntity<>(ticketService.findAndHoldSeats(numSeats, new Optional<>(minLevel), new Optional<>(maxLevel), customerEmail).getId(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/findAndHoldSeatsWithMinLevel/{numSeats}/{minLevel}/{customerEmail}", method = RequestMethod.POST)
	public ResponseEntity<Long> findAndHoldSeatsWithMinLevel(@PathVariable Integer numSeats, @PathVariable Integer minLevel, @PathVariable String customerEmail) throws NotAbleToHoldSeatsException {
		return new ResponseEntity<>(ticketService.findAndHoldSeats(numSeats, new Optional<>(minLevel), null, customerEmail).getId(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/findAndHoldSeatsWithMaxLevel/{numSeats}/{maxLevel}/{customerEmail}", method = RequestMethod.POST)
	public ResponseEntity<Long> findAndHoldSeatsWithMaxLevel(@PathVariable Integer numSeats, @PathVariable Integer maxLevel, @PathVariable String customerEmail) throws NotAbleToHoldSeatsException {
		return new ResponseEntity<>(ticketService.findAndHoldSeats(numSeats, null, new Optional<>(maxLevel), customerEmail).getId(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/reserveSeats/{seatHoldId}/{customerEmail}", method = RequestMethod.PUT)
	public ResponseEntity<String> reserveSeats(@PathVariable Integer seatHoldId, @PathVariable String customerEmail) throws NotAbleToReserveSeatException {
		return new ResponseEntity<>(ticketService.reserveSeats(seatHoldId, customerEmail), HttpStatus.OK);
	}
	
	@ExceptionHandler(value = {InvalidLevelException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	void handleInvalidLevelException() {
		
	}
	
	@ExceptionHandler(value = {NotAbleToHoldSeatsException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	void handleNotAbleToHoldSeatsException() {
		
	}
	
	@ExceptionHandler(value = {NotAbleToReserveSeatException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	void handleNotAbleToReserveSeatException() {
		
	}
}
