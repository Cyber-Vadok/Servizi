package PublishSubscribe;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * class that define connetion with an exchange
 */
public class EmitLog {
  private final static String EXCHANGE_NAME = "logs";

  public static void main(String[] args) {
    // create a connection to the server (localhost)
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("localhost"); //connection to rabbitmq

    try (
      // connection to queue's channel
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      ) {
        // declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // create a message
        String message;
        if (args.length < 1){
          message = "System message";
        } else {
          // extract the message from args
          message = String.join(" ", args);
        }

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));

        System.out.println("Message sended: " + message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
