package com.walmart.homework.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_data")
public class TicketData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "level_id")
	private Integer levelId;
	
	@Column(name = "level_name")
	private String levelName;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "rows")
	private Integer rows;
	
	@Column(name = "seats_in_row")
	private Integer seatsInRow;
	
	@Column(name = "total_seats")
	private Integer totalSeats;

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getSeatsInRow() {
		return seatsInRow;
	}

	public void setSeatsInRow(Integer seatsInRow) {
		this.seatsInRow = seatsInRow;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}
}
