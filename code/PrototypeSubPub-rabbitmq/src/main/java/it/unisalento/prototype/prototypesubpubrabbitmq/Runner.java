package it.unisalento.prototype.prototypesubpubrabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * classe per inviare un messaggio
 * -impl: indicare che i @Bean devono essere eseguiti se contenunti in @SpringApplication
 */
@Component
public class Runner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Receiver receiver;

  /**
   * COSTRUTTORE
   * @param receiver
   * @param rabbitTemplate
   */
  public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
    this.receiver = receiver;
    this.rabbitTemplate = rabbitTemplate;
  }

  /**
   * metodo per inviare un messaggio associandoci topic e routing key
   * @param args incoming main method arguments
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    rabbitTemplate.convertAndSend(PrototypeSubPubRabbitmqApplication.topicExchangeName,
                         "foo.bar.baz",
                            "Hello from RabbitMQ!");
    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
  }

}
