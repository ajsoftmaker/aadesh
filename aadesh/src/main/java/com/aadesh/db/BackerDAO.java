package com.aadesh.db;

import java.util.List;
import javax.ws.rs.core.Response;
import org.hibernate.SessionFactory;

import com.aadesh.entity.Backer;

import io.dropwizard.hibernate.AbstractDAO;

public class BackerDAO extends AbstractDAO<Backer> {

	public BackerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Backer> findAll() {
		return list(namedQuery("com.aarogyadan.entity.Backer.findAll"));
	}

	public Response create(Backer backer) {
		Response response = null;
		Backer getBacker = findByEmail(backer.getBackerEmail());
		if (getBacker != null) {
			response = Response.notModified("A backer with this code already exists").build();
		} else {
			persist(backer);
			response = Response.ok().build();
		}
		return response;
	}


	public Backer update(Backer backer) {
		return persist(backer);
	}

	public Backer findById(long backerID) {
		return uniqueResult(namedQuery("com.aarogyadan.entity.Backer.findById").setParameter("id", backerID));
	}

	public Backer findByEmail(String email) {
		return uniqueResult(namedQuery("com.aarogyadan.entity.Backer.findByEmail").setParameter("backerEmail", email));
	}

	public void delete(long backerID) {
		currentSession().delete(findById(backerID));
	}
}
