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
public class PrototypeSubPubRabbitmqApplication {

  static final String topicExchangeName = "spring-boot-exchange";
  static final String queueName = "spring-boot";

  /**
   * metodo che crea una AMQP queue che non esisterà più dopo aver spento il server
   * @return della queue
   */
  @Bean
  Queue queue() {
    return new Queue(queueName, false);
  }

  /**
   * metodo che crea un topic
   * @return del topic
   */
  @Bean
  TopicExchange exchange() {
    return new TopicExchange(topicExchangeName);
  }

  /**
   * metodo che definisce il comportamento da avere quando `RabbitTemplate` effettua un
   *   publish su un topic associa al una certa queue un certo topic con routing key che
   *   include tutti i messaggi inviati alle routing key che iniziano per "foo.bar."
   * @param queue
   * @param exchange (topic)
   * @return dell'oggeto che collega i due parametri
   */
  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
  }

  /**
   * metodo per settare il listener con:
   * @param connectionFactory (effettiva connessione creata)
   * @param listenerAdapter (listener "ad alto livello" ceh delega ad altri listener)
   * @return del container
   */
  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                           MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  /**
   * metodo che effettua il listening che delega al receiver chiamando poi
   *   il metodo Receiver.receiveMessage()
   * @param receiver
   * @return de listener
   */
  @Bean
  MessageListenerAdapter listenerAdapter(Receiver receiver) {
    return new MessageListenerAdapter(receiver, "receiveMessage");
  }

  public static void main(String[] args) {
    SpringApplication.run(PrototypeSubPubRabbitmqApplication.class, args);
  }

}
