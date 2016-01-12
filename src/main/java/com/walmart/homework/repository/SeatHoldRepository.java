package com.walmart.homework.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.walmart.homework.data.SeatHold;

@Repository
public interface SeatHoldRepository extends CrudRepository<SeatHold, Long> {

	@Query(value = "select sh from SeatHold sh where sh.holdTime < :date and sh.paymentPending = 1")
	List<SeatHold> findExpiredSeats(@Param(value="date") Date date);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from SeatHold where id = :seatHoldId")
	void releaseHold(@Param(value="seatHoldId") Long seatHoldId);
}
