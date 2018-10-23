package com.cx.gal.tickeTooli.model;

/**
 * Created by Galn on 10/22/2018.
 */

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByName(String name);
}