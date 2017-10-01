package com.osinskik.springReactiveWeb.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Incoming message DTO used as data sent by client app to server.
 * @author kamil.osinski
 */
@Data
@ToString
public class IncomingMessage {
  private String userName;
  private String message;
}
