package se.erikmolin.shipsapi.controller;


import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.BadGateway;
import se.erikmolin.shipsapi.api.Swapi;
import se.erikmolin.shipsapi.api.UpstreamErrorException;
import se.erikmolin.shipsapi.api.json.Starship;

@RestController
public class SortedShipsController {

  Swapi swapi;

  @Autowired
  public SortedShipsController(Swapi swapi) {
    this.swapi = swapi;
  }

  @GetMapping("/starships")
  public ResponseEntity<List<Starship>> getSortedShips(
      @RequestParam(value = "limit", defaultValue = "10") int limit
  ) {
    try {
      return ResponseEntity.ok(
          swapi.getStarships()
              .stream()
              .filter((ship) -> this.isNumber(ship.cost_in_credits))
              .sorted(Comparator.comparing(Starship::getCostInCredits)
                  .reversed()
                  .thenComparing(Starship::getCreated
                  ))
              .limit(limit)
              .collect(
                  Collectors.toList())
      );
    } catch (UpstreamErrorException e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    } catch (Throwable e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private boolean isNumber(String cost_in_credits) {
    try {
      new BigDecimal(cost_in_credits);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
