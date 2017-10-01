package com.osinskik.springReactiveWeb.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Chat event DTO used for storing in DB and sending to client.
 * @author kamil.osinski
 */
@Data
@Document
@Builder
@ToString
public class ChatEvent {
  @Id
  private String id;
  private String userName;
  private String message;
  private String roomId;
  private Date date;
}
