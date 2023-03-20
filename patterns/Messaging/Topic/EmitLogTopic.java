package Topic;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * class that define connetion with an exchange
 */
public class EmitLogTopic {
  private final static String EXCHANGE_NAME = "topic_logs";

  public static void main(String[] args) {
    // create a connection to the server (localhost)
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("localhost"); //connection to rabbitmq

    try (
      // connection to queue's channel
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      ) {
        // declare topic exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // create a routing key
        String routingKey = (args.length < 1) ? "routing.anonymus" : args[0];

        // create a message
        String message;
        if (args.length < 2){
          message = "System message";
        } else {
          // extract the message from args
          StringBuilder word = new StringBuilder(args[1]);
          for (int i = 2; i < args.length; i++) {
            word.append(" ").append(args[i]);
          }
          message = word.toString();
        }

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));

        System.out.println("Message sended: " + message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
