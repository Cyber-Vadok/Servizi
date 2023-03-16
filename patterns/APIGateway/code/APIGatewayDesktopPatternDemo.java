package APIGateway;

/**
 * demo for desktop clients
 */
public class APIGatewayDesktopPatternDemo {
  public static void main(String[] args) {
    APIGateway apiGateway = new APIGateway();

    System.out.println(apiGateway.getProductDesktop().getImagePath());
    System.out.println(apiGateway.getProductDesktop().getPrice());
  }
}
