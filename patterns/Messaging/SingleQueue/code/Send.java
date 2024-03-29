package Messaging;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * class to send messages
 */
public class Send {
  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) {
    // create a connection to the server (localhost)
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("localhost"); //connection to rabbitmq

    try (
      // connection to queue's channel
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      ) {
        // declare queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // create a message
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

        System.out.println("Message sended: " + message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
