package se.erikmolin.shipsapi.api.json;


import java.util.List;

public class ResponsePage<T> {

  public Integer count;
  public String next;
  public String previous;
  public List<T> results;
}
