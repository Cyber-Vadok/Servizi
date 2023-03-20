package Topic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * class to receive messages
 */
public class ReceiveLogTopic {
  private final static String EXCAHNGE_NAME = "topic_logs";

  public static void main(String[] args) throws IOException, TimeoutException{
    // create a connection to the server (localhost)
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("localhost"); //connection to rabbitmq
    Connection connection = factory.newConnection();

    // create the channel for the queue
    Channel channel = connection.createChannel();

    // declate queue
    channel.exchangeDeclare(EXCAHNGE_NAME, BuiltinExchangeType.TOPIC);

    // extract queue name
    String queueName = channel.queueDeclare().getQueue();

    // check arguments
    if (args.length < 1){
      System.out.println("usage: ReceiveLogTopic [binding key]...");
      System.exit(1);
    }
    
    // associating binding key to a queue
    for (String bindingKey : args){
      channel.queueBind(queueName, EXCAHNGE_NAME, bindingKey);
    }

    // bind exchange with queue
    channel.queueBind(queueName, EXCAHNGE_NAME, "");

    System.out.println("Waiting massages; Ctrl-C to quit");

    // create the callback (extract payload of delivery's body and use it like a message)
    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      // specify string codify
      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
      System.out.println("Message recived: " + message);
    };

    // how to consume messages
    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
  }
}
