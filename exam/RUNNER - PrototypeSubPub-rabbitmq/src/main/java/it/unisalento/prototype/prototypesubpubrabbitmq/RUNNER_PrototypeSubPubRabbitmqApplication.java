package it.unisalento.prototype.prototypesubpubrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * classe per il listener di messaggi
 */
@SpringBootApplication
public class RUNNER_PrototypeSubPubRabbitmqApplication {

  static final String topicExchangeName = "spring-boot-exchange";

  /**
   * metodo che crea un topic
   * @return del topic
   */
  @Bean
  TopicExchange exchange() {
    return new TopicExchange(topicExchangeName);
  }

  public static void main(String[] args) {
    SpringApplication.run(RUNNER_PrototypeSubPubRabbitmqApplication.class, args);
  }

}
