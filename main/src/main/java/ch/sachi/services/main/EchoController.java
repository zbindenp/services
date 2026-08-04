package ch.sachi.services.main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EchoController {
  @GetMapping("echo")
  public ResponseEntity<List<HeaderDto>> echo(
          @RequestHeader Map<String, String> headers
  ) {
    List<HeaderDto> dtos = headers.entrySet().stream().map(es -> new HeaderDto(es.getKey(), es.getValue())).toList();
    return ResponseEntity.ok(dtos);
  }
}
