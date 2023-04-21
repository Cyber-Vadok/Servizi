package it.unisalento.pas.smartcitywastemanagement.configuration;

import org.springframework.context.annotation.Bean;

/**
 * classe per settare delle logiche, in questo caso l'instanziamento dei protocolli MQTT e TCP
 */

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public IoTprotocol mqttProtocol() {
        return new MQTTprotocol();          // nuova istanza di MQTTprotocol
    }

    @Bean
    public IoTprotocol tcpProtocol() {
        return new TCPprotocol();           // nuova istanza di TCPprotocol
    }
}
