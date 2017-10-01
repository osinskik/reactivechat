package com.osinskik.springReactiveWeb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Request mapping for returning clientPage.
 * @author kamil.osinski
 */
@Controller
public class ClientWebController {

  @GetMapping("/main.html")
  public String page() {
    return "clientPage.html";
  }
}
