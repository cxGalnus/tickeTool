package com.cx.gal.tickeTooli.zendeskClient;

import com.cx.gal.tickeTooli.exceptions.TickeToolException;
import org.slf4j.Logger;
import org.zendesk.client.v2.model.Ticket;
import com.cx.gal.tickeTooli.zendeskClient.dto.CxConfig;
import com.cx.gal.tickeTooli.zendeskClient.dto.User;
import com.cx.gal.tickeTooli.zendeskClient.dto.UsersResponse;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Galn on 10/21/2018.
 */
public class ZendeskClient {
    private CxHttpClient httpClient;
    private Logger log;
    private CxConfig config;
    private User user;

    public ZendeskClient(CxConfig config, Logger log) throws MalformedURLException {
        this.config = config;
        this.log = log;
        this.httpClient = new CxHttpClient(config.getEmail(), config.getPassword(), log);
    }


    public void login() throws IOException, TickeToolException {
        UsersResponse users = httpClient.login();
        for (User useri : users.getUsers()) {
            if (useri.getEmail().equals(config.getEmail())) {
                user = useri;
                break;
            }
        }
        //todo log log log
    }

    public boolean createTicket(Ticket ticket){
        return true;
    }

}
