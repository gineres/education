package com.labcomu.org;

import com.labcomu.faultinjection.annotation.Delay;
import com.labcomu.org.domain.Organization;
import com.labcomu.org.resource.ResourceOrganization;
import com.labcomu.org.resource.ResourceResearcher;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/org")
@Validated
@RequiredArgsConstructor
public class OrgController {
  private static final int CONFLICT = 409;
  private final OrgService service;

  //@Delay(value=5, threshold=0.9)
  @GetMapping("organization/{url}")
  //@RateLimiter(name = "org-service", fallbackMethod = "getLog")
  public ResponseEntity<ResourceOrganization> getOrganization(@NotNull @PathVariable String url) {
    return ResponseEntity.of(service.getOrganization(url));
  }

  public ResponseEntity<ResourceOrganization> getLog (){
    ResourceOrganization org = new ResourceOrganization();
    org.setId(null);
    org.setName(null);
    org.setUrl(null);
    System.out.println(HttpStatus.REQUEST_TIMEOUT + ": o serviço demorou para responder.");
    return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(org);
  }

  @PostMapping("organization/researcher/{url}")
  public ResponseEntity<ResourceResearcher> createResearcher(@NotNull @PathVariable String url, @NotNull @RequestBody ResourceResearcher researcher) {
      return service.createResearcher(url, researcher).map(ResponseEntity::ok).orElse(ResponseEntity.status(CONFLICT).build());
  }

}
