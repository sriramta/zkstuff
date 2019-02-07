package org.acme.zk;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableScheduling
public class AcmeZkApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(AcmeZkApplication.class).run(args);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }
}
