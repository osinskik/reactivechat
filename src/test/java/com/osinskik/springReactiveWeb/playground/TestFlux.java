package com.osinskik.springReactiveWeb.playground;

import static java.lang.Thread.sleep;

import java.time.Duration;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Examples of using fluxes in tests.
 * @author kamil.osinski
 */
public class TestFlux {
  @Test
  public void testAppendBoomError() {
    Flux<String> sourceFlux = Flux.just("San Dimmons", "Dickip K. Phil", "Cohn Jena", "Me!", "Not me!");

    StepVerifier.create(testedFlux(sourceFlux))
        .expectNext("San Dimmons")
        .expectNext("Cohn Jena")
        .expectErrorMessage("It's bad name!")
        .verify();
  }

  private Flux<String> testedFlux(final Flux<String> inputFlux) {
    return inputFlux
        .log()
        .filter(name -> !name.equals("Dickip K. Phil"))
        .delayElements(Duration.ofSeconds(1))
        .map(s -> {
          if (s.equals("Me!")) {
            throw new RuntimeException("It's bad name!");
          }
          return s;
        });
  }
}
