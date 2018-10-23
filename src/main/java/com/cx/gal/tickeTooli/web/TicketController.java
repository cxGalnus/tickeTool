package com.cx.gal.tickeTooli.web;

/**
 * Created by Galn on 10/22/2018.
 */

import com.cx.gal.tickeTooli.logExtractor.LogExtractor;
import com.cx.gal.tickeTooli.logExtractor.LogTypes;
import com.cx.gal.tickeTooli.model.LogExtract;
import com.cx.gal.tickeTooli.model.Ticket;
import com.cx.gal.tickeTooli.model.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class TicketController {

    private final Logger log = LoggerFactory.getLogger(TicketController.class);
    private TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/tickets")
    Collection<Ticket> tickets() {
        return ticketRepository.findAll();
    }

    @PostMapping("/logs")
        ResponseEntity<?> createLogs(@Valid @RequestBody LogExtract logsToExtract) throws URISyntaxException, ParseException {
        log.info("Request to create logs: {}", logsToExtract);
        List<LogTypes> types = new ArrayList<>();
        types.add(LogTypes.WEB_CLIENT);
        types.add(LogTypes.JOB_MANAGER);
        types.add(LogTypes.SCAN_MANAGER);
        LogExtractor extractor = new LogExtractor(logsToExtract.getLowerStr(), logsToExtract.getUpperStr(), logsToExtract.getLogsPath(),types);
        extractor.scanFolderForLogs(new File(logsToExtract.getLogsPath()), false);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/ticket/{id}")
    ResponseEntity<?> getTicket(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tickets")
    ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket ticket) throws URISyntaxException {
        log.info("Request to create ticket: {}", ticket);
//        CxConfig config = new CxConfig("cx123456", "cxappdevsupport@checkmarx.com");
        //ZendeskClient client = new ZendeskClient(config, logi);
        //client.login();
        Ticket result = ticketRepository.save(ticket);
        return ResponseEntity.created(new URI("/api/ticket/" + result.getId()))
                .body(result);
    }

    @PutMapping("/ticket/{id}")
    ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Valid @RequestBody Ticket ticket) {
        ticket.setId(id);
        log.info("Request to update ticket: {}", ticket);
        Ticket result = ticketRepository.save(ticket);
        return ResponseEntity.ok().body(ticket);
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        log.info("Request to delete ticket: {}", id);
        ticketRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}