package com.walmart.homework.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "seat_hold")
public class SeatHold implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "seatHold", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<SeatHoldMapping> seatHoldMappings = new HashSet<>();
	
	@Column(name = "payment")
	private Boolean paymentPending;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hold_time")
	private Date holdTime;
	
	@Column(name = "customer_email")
	private String customerEmail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<SeatHoldMapping> getSeatHoldMappings() {
		return seatHoldMappings;
	}

	public void setSeatHoldMappings(Set<SeatHoldMapping> seatHoldMappings) {
		this.seatHoldMappings = seatHoldMappings;
	}

	public Boolean getPaymentPending() {
		return paymentPending;
	}

	public void setPaymentPending(Boolean paymentPending) {
		this.paymentPending = paymentPending;
	}

	public Date getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Date holdTime) {
		this.holdTime = holdTime;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
}
