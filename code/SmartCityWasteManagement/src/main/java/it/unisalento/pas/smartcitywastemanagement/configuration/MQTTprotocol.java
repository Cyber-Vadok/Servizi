package it.unisalento.pas.smartcitywastemanagement.configuration;

public class MQTTprotocol implements IoTprotocol {

    @Override
    public void initialize() {
        System.out.println("Initialize MQTT protocoll");
    }
}
