package it.unisalento.pas.smartcitywastemanagement.di;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MySQLDBConnection implements IDBConnection{

    public void connect(){
        System.out.println("Connected to MySQL db");
    }

}
