package com.aadesh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "volunteers")
@NamedQueries({ @NamedQuery(name = "com.aarogyadan.entity.Volunteer.findAll", query = "SELECT t FROM Volunteer t"),
		@NamedQuery(name = "com.aarogyadan.entity.Volunteer.findById", query = "SELECT t FROM Volunteer t where t.id = :id"),
		@NamedQuery(name = "com.aarogyadan.entity.Volunteer.findByEmail", query = "SELECT t FROM Volunteer t where t.volunteerEmail = :volunteerEmail")})

public class Volunteer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "volunteer_name", nullable = false)
	private String volunteerName;
	
	@Column(name = "volunteer_address", nullable = false)
	private String volunteerAddress;

	@Column(name = "volunteer_phone")
	private String volunteerPhone;

	@Column(name = "volunteer_email")
	private String volunteerEmail;

	@Column(name = "volunteer_occupation")
	private String volunteerOccupation;

	@Column(name = "volunteer_vision")
	private String volunteerVision;
	
	@Column(name = "volunteer_status")
	private String volunteerStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVolunteerName() {
		return volunteerName;
	}

	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}

	public String getVolunteerAddress() {
		return volunteerAddress;
	}

	public void setVolunteerAddress(String volunteerAddress) {
		this.volunteerAddress = volunteerAddress;
	}

	public String getVolunteerPhone() {
		return volunteerPhone;
	}

	public void setVolunteerPhone(String volunteerPhone) {
		this.volunteerPhone = volunteerPhone;
	}

	public String getVolunteerEmail() {
		return volunteerEmail;
	}

	public void setVolunteerEmail(String volunteerEmail) {
		this.volunteerEmail = volunteerEmail;
	}

	public String getVolunteerOccupation() {
		return volunteerOccupation;
	}

	public void setVolunteerOccupation(String volunteerOccupation) {
		this.volunteerOccupation = volunteerOccupation;
	}

	public String getVolunteerVision() {
		return volunteerVision;
	}

	public void setVolunteerVision(String volunteerVision) {
		this.volunteerVision = volunteerVision;
	}

	public String getVolunteerStatus() {
		return volunteerStatus;
	}

	public void setVolunteerStatus(String volunteerStatus) {
		this.volunteerStatus = volunteerStatus;
	}
	
}
