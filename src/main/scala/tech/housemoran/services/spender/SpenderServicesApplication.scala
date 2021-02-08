package tech.housemoran.services.spender

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpenderServicesApplication

object SpenderServicesApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[SpenderServicesApplication])
  }
}