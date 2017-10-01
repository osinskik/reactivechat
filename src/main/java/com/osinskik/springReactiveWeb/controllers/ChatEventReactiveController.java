package com.osinskik.springReactiveWeb.controllers;

import java.util.Date;

import com.osinskik.springReactiveWeb.model.ChatEventService;
import com.osinskik.springReactiveWeb.dto.ChatEvent;
import com.osinskik.springReactiveWeb.dto.IncomingMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Request mappings for reactive rest request to application from client.
 * @author kamil.osinski
 */
@RestController
@RequestMapping
public class ChatEventReactiveController {

  private final ChatEventService chatEventService;

  ChatEventReactiveController(ChatEventService chatEventService) {
      this.chatEventService = chatEventService;
    }

  @GetMapping(value ="/room/{roomId}")
  Flux<ChatEvent> roomStream(@PathVariable final String roomId) {
    System.out.println("New user connected to room: " + roomId);
    return chatEventService.getAllIn(roomId).log();
  }

  @PostMapping("/room/{roomId}")
  public Mono<ChatEvent> create(@PathVariable final String roomId, @RequestBody Mono<IncomingMessage> message) {
    return message
        .map(ms -> this.transformIntoChatEvent(ms, roomId))
        .publish(chatEventService::save)
        .log();
  }

  private ChatEvent transformIntoChatEvent(final IncomingMessage incomingMessage, final String roomId) {
    return ChatEvent.builder()
        .message(incomingMessage.getMessage())
        .date(new Date())
        .roomId(roomId)
        .userName(incomingMessage.getUserName())
        .build();
  }
}
