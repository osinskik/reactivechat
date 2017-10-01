package com.osinskik.springReactiveWeb.model;

import com.osinskik.springReactiveWeb.dto.ChatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author kamil.osinski
 */
@Repository
public class ChatEventService {

  @Value("${app.collectionName}")
  private String collectionName;

  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;

  public Flux<ChatEvent> getAllIn(final String roomId) {
    final Query findByRoomId = Query.query(Criteria.where("roomId").is(roomId));
    return this.reactiveMongoTemplate
        .tail(findByRoomId, ChatEvent.class, collectionName)
        .doOnError(System.err::println);
  }

  public Mono<ChatEvent> save(final Mono<ChatEvent> chatEvent) {
    return chatEvent
        .flatMap(this.reactiveMongoTemplate::save)
        .doOnNext(ms -> System.out.println("Saved to mongo DB " + chatEvent))
        .doOnError(err -> System.err.println("Not saved to mongoDB " + chatEvent +",\n error: " + err.getMessage()));
  }
}
