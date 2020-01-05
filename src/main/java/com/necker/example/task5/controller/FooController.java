package com.necker.example.task5.controller;

import com.necker.example.task5.model.Foo;
import com.necker.example.task5.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RestController
public class FooController {

    private FooService fooService;

    @Autowired
    public FooController(FooService fooService) {
        this.fooService = fooService;
    }

    @RequestMapping(value = "/foos", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Foo> getFoos() {
        return fooService.findAll().delayElements(Duration.ofSeconds(1));
    }

    @PostConstruct
    public void doit() {
        WebClient client = WebClient.create("http://localhost:8080");

        Flux<Foo> fooFlux = client.get()
                .uri("/foos")
                .retrieve()
                .bodyToFlux(Foo.class);

        fooFlux.subscribe(System.out::println);
    }
}
