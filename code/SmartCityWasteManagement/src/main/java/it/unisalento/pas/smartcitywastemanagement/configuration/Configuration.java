package it.unisalento.pas.smartcitywastemanagement.configuration;

import org.springframework.context.annotation.Bean;

/**
 * classe per settare delle logiche, in questo caso l'instanziamento dei protocolli MQTT e TCP
 */

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public IoTprotocol mqttProtocol() {
        // nuova istanza di MQTTprotocol
        return new MQTTprotocol();
    }

    @Bean
    public IoTprotocol tcpProtocol() {
        // nuova istanza di TCPprotocol
        return new TCPprotocol();
    }
}
