package APIGateway;

/**
 * demo for mobile clients
 */
public class APIGatewayMobilePatternDemo {
  public static void main(String[] args) {
    APIGateway apiGateway = new APIGateway();

    System.out.println(apiGateway.getProductMobile().getPrice());
  }
}
