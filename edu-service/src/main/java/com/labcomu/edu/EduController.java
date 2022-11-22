package com.labcomu.edu;

import com.labcomu.edu.resource.Organization;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/edu")
@Validated
@RequiredArgsConstructor
public class EduController {
  private final EduService service;

  @GetMapping("organization/{url}")
  @CircuitBreaker(name = "eduService", fallbackMethod = "getLog")
  public Organization getOrganization(@NotNull @PathVariable String url) {
    return service.getOrganization(url);
  }

  public Organization getLog (Exception e){
    System.out.println("Entrou no cirtuit");
    Organization org = new Organization();
    org.setName("mika");
    org.setUrl("mmooo.ufrn.br");
    System.out.println("ORG:::::::: " + org);
    return org;
  }
}
