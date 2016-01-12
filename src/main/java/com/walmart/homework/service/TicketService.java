package com.walmart.homework.service;

import com.walmart.homework.data.Optional;
import com.walmart.homework.data.SeatHold;
import com.walmart.homework.exceptions.InvalidLevelException;
import com.walmart.homework.exceptions.NotAbleToHoldSeatsException;
import com.walmart.homework.exceptions.NotAbleToReserveSeatException;

public interface TicketService {
	/**
	* The number of seats in the requested level that are neither held nor reserved
	*
	* @param venueLevel a numeric venue level identifier to limit the search
	* @return the number of tickets available on the provided level
	 * @throws InvalidLevelException 
	*/
	int numSeatsAvailable(Optional<Integer> venueLevel) throws InvalidLevelException;
	
	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold
	* @param minLevel the minimum venue level
	* @param maxLevel the maximum venue level
	* @param customerEmail unique identifier for the customer
	* @return a SeatHold object identifying the specific seats and related
	information
	 * @throws NotAbleToHoldSeatsException 
	*/
	SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
	Optional<Integer> maxLevel, String customerEmail) throws NotAbleToHoldSeatsException;
	
	/**
	* Commit seats held for a specific customer
	*
	* @param seatHoldId the seat hold identifier
	* @param customerEmail the email address of the customer to which the seat hold
	is assigned
	* @return a reservation confirmation code
	 * @throws NotAbleToReserveSeatException 
	*/
	String reserveSeats(int seatHoldId, String customerEmail) throws NotAbleToReserveSeatException;
}