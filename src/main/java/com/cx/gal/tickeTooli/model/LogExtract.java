package com.cx.gal.tickeTooli.model;

/**
 * Created by Galn on 10/22/2018.
 */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "log_ticket")
public class LogExtract {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    String lowerStr = "";//"2018-03-01 11:53:36";
    String upperStr = "";//"2018-03-01 12:09:16";
    String logsPath = "";//"C:\\Users\\galn\\Desktop\\tickeTool\\Logs";

    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<Event> events;
}