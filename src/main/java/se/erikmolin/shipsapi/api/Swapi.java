package se.erikmolin.shipsapi.api;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException.BadGateway;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import se.erikmolin.shipsapi.api.json.ResponsePage;
import se.erikmolin.shipsapi.api.json.Starship;

@Component
public class Swapi {


  Logger LOGGER = LoggerFactory.getLogger(Swapi.class);

  RestTemplate restTemplate;

  @Value("${swapi.baseUrl}")
  String baseUrl;

  @Autowired
  public Swapi() {
    this.restTemplate = new RestTemplateBuilder().build();
  }

  public List<Starship> getStarships() {
    List<Starship> loadedShips = new ArrayList<>();
    String url = baseUrl + "starships";

    try {
      ResponseEntity<ResponsePage<Starship>> response;
      do {
        response = fetch(url);
        loadedShips.addAll(response.getBody().results);
        url = response.getBody().next;
      } while (response.getBody().next != null);

      return loadedShips;

    } catch (RestClientException e) {
      LOGGER.info("exception in api handling:", e);
      throw new UpstreamErrorException();
    }

  }

  private ResponseEntity<ResponsePage<Starship>> fetch(
      String s
  ) {
    ParameterizedTypeReference<ResponsePage<Starship>> returnType = new ParameterizedTypeReference<ResponsePage<Starship>>() {};
    return restTemplate
        .exchange(
            s,
            HttpMethod.GET,
            null,
            returnType
        );
  }
}
