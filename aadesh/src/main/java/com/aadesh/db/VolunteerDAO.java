package com.aadesh.db;

import java.util.List;
import javax.ws.rs.core.Response;
import org.hibernate.SessionFactory;

import com.aadesh.entity.Volunteer;

import io.dropwizard.hibernate.AbstractDAO;

public class VolunteerDAO extends AbstractDAO<Volunteer> {

	public VolunteerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Volunteer> findAll() {
		return list(namedQuery("com.aarogyadan.entity.Volunteer.findAll"));
	}

	public Response create(Volunteer volunteer) {
		Response response = null;
		Volunteer getVolunteer = findByEmail(volunteer.getVolunteerEmail());
		if (getVolunteer != null) {
			response = Response.notModified("A volunteer with this code already exists").build();
		} else {
			persist(volunteer);
			response = Response.ok().build();
		}
		return response;
	}


	public Volunteer update(Volunteer volunteer) {
		return persist(volunteer);
	}

	public Volunteer findById(long volunteerID) {
		return uniqueResult(namedQuery("com.aarogyadan.entity.Volunteer.findById").setParameter("id", volunteerID));
	}

	public Volunteer findByEmail(String email) {
		return uniqueResult(namedQuery("com.aarogyadan.entity.Volunteer.findByEmail").setParameter("volunteerEmail", email));
	}

	public void delete(long volunteerID) {
		currentSession().delete(findById(volunteerID));
	}
}
