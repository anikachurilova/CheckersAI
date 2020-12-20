package main;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.CompletableFuture;
import checkers.CheckersGame;
import checkers.minimaxAI;
import checkers.Config;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            CheckersGame bot1 = new CheckersGame("Люся", new minimaxAI(), Config.TIME);
           // CheckersGame bot2 = new CheckersGame("Bot", new minimaxAI(),Config.TIME);

            CompletableFuture.runAsync(() -> {
                try {
                    bot1.runGame(restTemplate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).thenRun(()->{
                System.exit(0);
            });

//            CompletableFuture.runAsync(() -> {
//                try {
//                    bot2.runGame(restTemplate);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).thenRun(()->{
//                System.exit(0);
//            });
        };
    }
}

