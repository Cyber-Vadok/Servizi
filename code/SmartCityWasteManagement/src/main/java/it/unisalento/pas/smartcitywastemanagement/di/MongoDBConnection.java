package it.unisalento.pas.smartcitywastemanagement.di;

import org.springframework.stereotype.Component;

/**
 * classe che identifica il comportamento di MongoDB (in grado di capire quale interfaccia viene implementata nel
 * progetto e nel caso in cui non sia lei, si fa da parte dinamicamente, cioè senza che glielo si venga a dire
 * direttamente (@Component))
 */

@Component
public class MongoDBConnection implements IDBConnection{
    @Override
    public void connect() {
        System.out.println("Connected to Mongo db");
    }
}
