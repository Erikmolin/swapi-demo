package se.erikmolin.shipsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.erikmolin.shipsapi.api.Swapi;
import se.erikmolin.shipsapi.api.UpstreamErrorException;
import se.erikmolin.shipsapi.api.json.Starship;
import se.erikmolin.shipsapi.controller.SortedShipsController;

@SpringBootTest
class ShipsapiApplicationTests {

  @MockBean
  Swapi swapi;

  @Autowired
  SortedShipsController controller;
  private List<Starship> reasonableList = Arrays.asList(
      new Starship("1000"),
      new Starship("1200"),
      new Starship("1300"),
      new Starship("1500")
  );

  private List<Starship> nonNumericValuesList = Arrays.asList(
      new Starship("1000"),
      new Starship("ping"),
      new Starship("pong"),
      new Starship("1500")
  );


  @Test
  void upstreamErrorReturnsBadGateway() {
    when(swapi.getStarships()).thenThrow(new UpstreamErrorException());

    ResponseEntity<List<Starship>> error = controller.getSortedShips(10);
    assertEquals(error.getStatusCode(), HttpStatus.BAD_GATEWAY);
  }

  @Test
  void excludesNonNumericValues() {
    when(swapi.getStarships()).thenReturn(nonNumericValuesList);
    ResponseEntity<List<Starship>> sortedShips = controller.getSortedShips(10);

    assertEquals(2, sortedShips.getBody().size());

  }

  @Test
  void returnsSorted() {
    when(swapi.getStarships()).thenReturn(reasonableList);
    ResponseEntity<List<Starship>> sortedShips = controller.getSortedShips(10);
    assertEquals(4, sortedShips.getBody().size());

    for (Starship starship : sortedShips.getBody()) {
      BigDecimal costInCredits = starship.getCostInCredits();
    }

  }

}
