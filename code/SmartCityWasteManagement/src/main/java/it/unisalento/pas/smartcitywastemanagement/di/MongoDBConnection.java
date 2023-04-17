package it.unisalento.pas.smartcitywastemanagement.di;

import org.springframework.stereotype.Component;

@Component
public class MongoDBConnection implements IDBConnection{
    @Override
    public void connect() {
        System.out.println("Connected to Mongo db");
    }
}
