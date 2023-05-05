package it.unisalento.pas.smartcitywastemanagement.di;

import org.springframework.stereotype.Component;

/**
 * classe che identifica il comportamento di MySQL (in grado di capire quale interfaccia viene implementata nel
 * progetto e nel caso in cui non sia lei, si fa da parte dinamicamente, cioè senza che glielo si venga a dire
 * direttamente (@Component))
 */
@Component
public class MySQLDBConnection implements IDBConnection{

    public void connect(){
        System.out.println("Connected to MySQL db");
    }

}
