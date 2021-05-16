package com.fisk.woven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WovenfileserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(WovenfileserverApplication.class, args);
  }
}
