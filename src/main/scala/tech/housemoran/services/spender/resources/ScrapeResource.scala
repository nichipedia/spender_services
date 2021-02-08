package tech.housemoran.services.spender.resources

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{CrossOrigin, GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@CrossOrigin(Array("*"))
@RequestMapping(Array("/items"))
class ScrapeResource {
  @GetMapping(Array("/{site}"))
  def getSiteItems(@PathVariable("site") site: String): Unit = {
    ScrapeResource.log.info(s"Gettings items for ${site}")

  }
}


object ScrapeResource {
  val log = LoggerFactory.getLogger(classOf[ScrapeResource])
}
