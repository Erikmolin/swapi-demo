package se.erikmolin.shipsapi.api.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

public class Starship {

  public String name;
  public String model;
  public String starship_class;
  public String manufacturer;
  public String cost_in_credits;
  public String length;
  public String crew;
  public String passengers;
  public String max_atmosphering_speed;
  public String hyperdrive_rating;
  public String MGLT;
  public String cargo_capacity;
  public String consumables;
  public List<String> films;
  public List<String> pilots;
  public String url;
  public LocalDateTime created;
  public LocalDateTime edited;

  public Starship() {
  }

  public Starship(String cost_in_credits) {
    this.cost_in_credits = cost_in_credits;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public BigDecimal getCostInCredits() {
    try {
      return new BigDecimal(cost_in_credits);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Starship.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("cost_in_credits='" + cost_in_credits + "'")
        .toString();
  }
}
