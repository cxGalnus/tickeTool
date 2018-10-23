package com.cx.gal.tickeTooli;

/**
 * Created by Galn on 10/22/2018.
 */
import com.cx.gal.tickeTooli.model.Event;
import com.cx.gal.tickeTooli.model.Ticket;
import com.cx.gal.tickeTooli.model.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final TicketRepository repository;

    public Initializer(TicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(name ->
                repository.save(new Ticket(name))
        );

        Ticket djug = repository.findByName("Denver JUG");
        Event e = Event.builder().title("Full Stack Reactive")
                .description("Reactive with Spring Boot + React")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();
        djug.setEvents(Collections.singleton(e));
        repository.save(djug);

        repository.findAll().forEach(System.out::println);
    }
}