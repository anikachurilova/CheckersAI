package main;

import checkers.minimaxAI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;
import checkers.CheckersGame;
@SpringBootApplication
public class Main {

    //  static boolean finished=false;
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
            CheckersGame bot1 = new CheckersGame("Люся", new minimaxAI());
            //CheckersGame bot2 = new CheckersGame("Bot", new minimaxAI());

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

