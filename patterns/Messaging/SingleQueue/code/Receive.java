package Messaging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * class to receive messages
 */
public class Receive {
  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) throws IOException, TimeoutException{
    // create a connection to the server (localhost)
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("localhost"); // connection to rabbitmq
    Connection connection = factory.newConnection();

    // create the channel for the queue
    Channel channel = connection.createChannel();

    // declare queue
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    System.out.println("Waiting massages; ^C to quit");

    // create the callback (extract payload of delivery's body and use it like a message)
    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      // specify string codify
      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
      System.out.println("Message recived: " + message);
    };

    // how to consume messages
    channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
  }
}
