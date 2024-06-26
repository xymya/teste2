package br.com.vepp.desafiobackvotos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = 	@Info(
                title = "${project.name}",
                version = "${project.version}",
                description = "${project.description}"
        )
)
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableScheduling
@EnableFeignClients
public class DesafioBackVotosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioBackVotosApplication.class, args);
    }

}
