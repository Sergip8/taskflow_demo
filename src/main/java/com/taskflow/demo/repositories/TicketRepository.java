package com.taskflow.demo.repositories;

import com.taskflow.demo.entities.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}
