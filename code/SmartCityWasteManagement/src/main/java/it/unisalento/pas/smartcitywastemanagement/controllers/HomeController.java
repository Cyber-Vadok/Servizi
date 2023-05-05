package it.unisalento.pas.smartcitywastemanagement.controllers;

import it.unisalento.pas.smartcitywastemanagement.configuration.IoTprotocol;
import it.unisalento.pas.smartcitywastemanagement.di.IDBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * classe che fa da controller generico passando gli indirizzi come pagine HTML (@Controller), dove quindi istanzio degli
 * oggetti che dovranno essere utilizzati (gli stessi oggetti) in altre classi (@Autowired)
 */

@Controller
public class HomeController {

    @Autowired
    IDBConnection mySQLDBConnection;

    @Autowired
    IDBConnection mongoDBConnection;

    @Autowired
    IoTprotocol tcpProtocol;

    @RequestMapping("/home") // per mappare a "/home" le chiamate nella funzione
    public String home(){
        mySQLDBConnection.connect();
        mongoDBConnection.connect();
        tcpProtocol.initialize();
        return "home";
    }

}
