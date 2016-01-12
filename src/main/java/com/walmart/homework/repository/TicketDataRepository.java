package com.walmart.homework.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.walmart.homework.data.TicketData;

@Transactional
public interface TicketDataRepository extends CrudRepository<TicketData, Integer> {
	
	TicketData findByLevelId(Integer levelId);
	
	@Query(value = "select sum(td.totalSeats) from TicketData td")
	Integer findAllAvailableSeats();
	
	@Query(value = "select td from TicketData td order by td.levelId")
	List<TicketData> findAll();
	
	@Query(value = "select td from TicketData td where td.levelId >= :minLevel and td.levelId <= :maxLevel order by td.levelId")
	List<TicketData> findByMinAndMaxLevel(@Param(value="minLevel") Integer minLevel, @Param(value="maxLevel")Integer maxLevel);
	
	@Query(value = "select td from TicketData td where td.levelId >= :minLevel order by td.levelId")
	List<TicketData> findByMinLevel(@Param(value="minLevel") Integer minLevel);
	
	@Query(value = "select td from TicketData td where td.levelId <= :maxLevel order by td.levelId")
	List<TicketData> findByMaxLevel(@Param(value="maxLevel") Integer maxLevel);
	
	@Query(value = "select sum(td.totalSeats) from TicketData td where td.levelId >= :minLevel and td.levelId <= :maxLevel")
	Integer findNumberOfSeatsByMinAndMaxLevel(@Param(value="minLevel") Integer minLevel, @Param(value="maxLevel") Integer maxLevel);
	
	@Query(value = "select sum(td.totalSeats) from TicketData td where td.levelId >= :minLevel")
	Integer findNumberOfSeatsByMinLevel(@Param(value="minLevel") Integer minLevel);
	
	@Query(value = "select sum(td.totalSeats) from TicketData td where td.levelId <= :maxLevel")
	Integer findNumberOfSeatsByMaxLevel(@Param(value="maxLevel") Integer maxLevel);

}
