package org.acme.zk.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class AcmeZkClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AcmeZkClientApplication.class).run(args);
    }

}
