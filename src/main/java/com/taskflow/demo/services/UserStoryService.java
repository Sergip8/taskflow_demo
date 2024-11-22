package com.taskflow.demo.services;

import com.taskflow.demo.entities.Ticket;
import com.taskflow.demo.entities.UserStory;
import com.taskflow.demo.records.*;
import com.taskflow.demo.repositories.UserStoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserStoryService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserStoryDto> findUserStoriesWithUserByProjectId(String projectId) {
        try{
        AggregationOperation match = Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("projectId").is(new ObjectId(projectId)));
        AggregationOperation lookup = Aggregation.lookup("users", "assignedUserId", "_id", "user");
        //System.out.println(mongoTemplate.aggregate(Aggregation.newAggregation(match, lookup), "userStories", UserStory.class).getRawResults());
        AggregationOperation unwind = Aggregation.unwind("user");
        AggregationOperation project = Aggregation.project("id", "title", "description", "projectId", "assignedUserId")
                .and("user.username").as("assignedUserName");


        Aggregation aggregation = Aggregation.newAggregation(match, lookup, unwind, project);

        AggregationResults<UserStoryDto> results = mongoTemplate.aggregate(aggregation, "userStories", UserStoryDto.class);
        return results.getMappedResults();

        }catch (Exception e){
            return  new ArrayList<>();
        }
    }
    @Transactional
    public CreateResponse createUserStoryWithTicket(CreateUserStoryRequest request) {
        var currentStrDate = LocalDateTime.now().toString();
        try {
            var userStory = new UserStory(
                    null,
                    request.title(),
                    request.description(),
                    new ObjectId(request.projectId()),
                    currentStrDate,
                    currentStrDate,
                    new ObjectId(request.assignedUserId())
            );

            UserStory savedUserStory = mongoTemplate.insert(userStory, "userStories");

            var ticket = new Ticket(
                    null,
                    request.ticketTitle(),
                    request.ticketDescription(),
                    new ObjectId(savedUserStory.getId()),
                    "Activo",
                    new ArrayList<>(),
                    Instant.now(),
                    currentStrDate,
                    new ObjectId(request.assignedUserId())
            );

            mongoTemplate.insert(ticket, "tickets");

            return new CreateResponse(null, "Success", "La historia de usuario se a guardado");
        }catch (Exception e){
            return new CreateResponse(null, "Error", "La historia de usuario no se pudo guardar");
        }
    }
    public CreateResponse updateUserStory(UpdateUserStoryRequest userStoryUpdateDto) {
        try{
            Query query = new Query(Criteria.where("_id").is(userStoryUpdateDto.id()));

            Update update = new Update();
            if (userStoryUpdateDto.title() != null) update.set("title", userStoryUpdateDto.title());
            if (userStoryUpdateDto.description() != null) update.set("description", userStoryUpdateDto.description());


            // Aplicar la actualización
            mongoTemplate.updateFirst(query, update, UserStory.class);

            // Retornar el ticket actualizado (opcional)
            return new CreateResponse(null, "Success", "La historia de usuario se ha actualizado");
        }
        catch (Exception e){
            return new CreateResponse(null, "Error", "no se pudo actualizar la historia de usuario");
        }


    }


}
