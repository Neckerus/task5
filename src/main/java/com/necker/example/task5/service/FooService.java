package com.necker.example.task5.service;

import com.necker.example.task5.model.Foo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FooService {

    public Flux<Foo> findAll() {
        return Flux.fromIterable(generateFoo(5));
    }

    private List<Foo> generateFoo(int times) {
        List<Foo> list = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            list.add(new Foo(new Random().nextInt(5), UUID.randomUUID().toString()));
        }

        return list;
    }
}