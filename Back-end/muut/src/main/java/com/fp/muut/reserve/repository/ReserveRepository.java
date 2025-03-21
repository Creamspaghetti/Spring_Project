package com.fp.muut.reserve.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fp.muut.entity.Customer;
import com.fp.muut.entity.Musical;
import com.fp.muut.entity.Performance;
import com.fp.muut.entity.Reservation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReserveRepository {

	private final EntityManager em;

	public Performance getPerformanceById(Long performance_id) {
		return em.find(Performance.class, performance_id);
	}

	public Musical getMusicalById(Long performance_id) {
		return em.createQuery("select p.musical from Performance p where p.id=:performance_id",Musical.class)
				.setParameter("performance_id", performance_id).getSingleResult();
	}

	public String getHallName(Long performance_id) {
		return em.createQuery("select hi.hall_name from Performance p join p.hall_Info hi where p.id=:performance_id",String.class)
				.setParameter("performance_id", performance_id).getSingleResult();
	}

	public Customer getCustomerByEmail(String customer_email) {
		return em.createQuery("select c from Customer c where c.customer_id=:customer_email",Customer.class)
				.setParameter("customer_email", customer_email).getSingleResult();
	}

	public void saveReserve(Reservation reservation) {
		em.persist(reservation);
	}

	public String findSoldSeats(Long performance_id) {
		return em.createQuery("select r.seat_num from Reservation r join r.performance p where p.id=:performance_id",String.class)
				.setParameter("performance_id", performance_id).getSingleResult();
	}

	public String getReserveSeats(Long reservation_num) {
		return em.createQuery("select r.seat_num from Reservation r where r.reservation_num=:reservation_num",String.class)
				.setParameter("reservation_num", reservation_num).getSingleResult();
	}
	
	
}
