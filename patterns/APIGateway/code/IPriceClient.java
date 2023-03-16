package APIGateway;

/**
 * interface to access the price value (client view)
 */
public interface IPriceClient {
  /**
   * get the product's price
   * @return product's price
   */
  String getPrice();
}
