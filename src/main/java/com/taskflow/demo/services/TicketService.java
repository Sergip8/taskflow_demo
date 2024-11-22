package com.taskflow.demo.services;

import com.taskflow.demo.entities.Ticket;
import com.taskflow.demo.records.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TicketService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TicketDto> findTicketWithUserByProjectId(String storyId) {
        //System.out.println(storyId);
        try {
            AggregationOperation match = Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("storyId").is(new ObjectId(storyId)));
            AggregationOperation lookup = Aggregation.lookup("users", "assignedUserId", "_id", "user");
            //System.out.println(mongoTemplate.aggregate(Aggregation.newAggregation(match), "userStories", TicketDto.class).getRawResults());
            AggregationOperation unwind = Aggregation.unwind("user");
            AggregationOperation project = Aggregation.project("id", "title", "description", "storyId", "assignedUserId", "status", "comments")
                    .and("user.username").as("assignedUserName");


            Aggregation aggregation = Aggregation.newAggregation(match, lookup, unwind, project);

            AggregationResults<TicketDto> results = mongoTemplate.aggregate(aggregation, "tickets", TicketDto.class);
            return results.getMappedResults();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public CreateResponse updateTicket(TicketUpdateDto ticketUpdateDto) {
        try {
            Query query = new Query(Criteria.where("_id").is(ticketUpdateDto.id()));

            Update update = new Update();
            if (ticketUpdateDto.title() != null) update.set("title", ticketUpdateDto.title());
            if (ticketUpdateDto.description() != null) update.set("description", ticketUpdateDto.description());
            if (ticketUpdateDto.status() != null) update.set("status", ticketUpdateDto.status());

            // Aplicar la actualización
            mongoTemplate.updateFirst(query, update, Ticket.class);

            // Retornar el ticket actualizado (opcional)
            return new CreateResponse(null, "Success", "el ticket se ha actualizado");
        } catch (Exception e) {
            return new CreateResponse(null, "Error", "no se pudo actualizar el ticket");
        }


    }

    public CreateResponse createTicket(CreateTicketRequest request) {
        var currentStrDate = LocalDateTime.now().toString();
        try {
            var ticket = new Ticket(
                    null,
                    request.title(),
                    request.description(),
                    new ObjectId(request.storyId()),
                    "Activo",
                    new ArrayList<>(),
                    Instant.now(),
                    currentStrDate,
                    request.assignedUserId()
            );
            mongoTemplate.insert(ticket, "tickets");
            return new CreateResponse(null, "Success", "El ticket se a guardado");
        } catch (Exception e) {
            return new CreateResponse(null, "Error", "ocurrio un error al guardar el ticket");
        }

    }

    public Object getTicketDetails(String ticketId) {

        // Pipeline de agregación
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(ticketId)),
                Aggregation.lookup("userStories", "storyId", "_id", "userStory"),
                Aggregation.unwind("userStory"),
                Aggregation.lookup("users", "userStory.assignedUserId", "_id", "userStory.assignedUser"),
                Aggregation.unwind("userStory.assignedUser", true),
                Aggregation.lookup("projects", "userStory.projectId", "_id", "project"),
                Aggregation.unwind("project", true),
                Aggregation.lookup("users", "assignedUserId", "_id", "ticketUser"),
                Aggregation.unwind("ticketUser", true)
        );
        var results = mongoTemplate.aggregate(aggregation, "tickets", TicketDetailsDto.class);

        return results.getMappedResults();
    }

    public Object filterTickets(TicketFilter filterRequest) {
        Criteria criteria = new Criteria();
        System.out.println(filterRequest);
        if (filterRequest.search() != null && !filterRequest.search().isEmpty()) {
            criteria.orOperator(
                    Criteria.where("title").regex(filterRequest.search(), "i"),
                    Criteria.where("description").regex(filterRequest.search(), "i")
            );
        }
        if (filterRequest.status() != null && !filterRequest.status().isEmpty() && !filterRequest.status().equals("all")) {
            criteria.and("status").is(filterRequest.status());
        }

        Query query = new Query(criteria);
        if (filterRequest.order() != null && !filterRequest.order().isEmpty()) {
            if (Objects.equals(filterRequest.orderDirection(), "asc")) {
                query.with(Sort.by(Sort.Direction.ASC, filterRequest.order()));
            } else {
                System.out.println(filterRequest.order());
                query.with(Sort.by(Sort.Direction.DESC, filterRequest.order()));
            }
        }

        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.size());
        query.with(pageable);

        var tickets = mongoTemplate.find(query, Document.class, "tickets");
        long total = mongoTemplate.count(query.skip(-1).limit(-1), "tickets");


        return new PagedResponseDto(tickets, total, filterRequest.page());
    }

    public CreateResponse deleteTicket(String userStoryId) {
        try {
        Query query = new Query(Criteria.where("_id").is(userStoryId));
        mongoTemplate.remove(query, "tickets");
            return new CreateResponse(null, "Success", "El ticket se ha borrado");
        }catch (Exception e){
            return new CreateResponse(null, "Error", "ocurrio un error al borrar el ticket");
        }
    }

}
