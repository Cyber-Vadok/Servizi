package APIGateway;

/**
 * subclass of IPriceClient that return the price value
 */
public class PriceClientImp implements IPriceClient{
  
  @Override
  public String getPrice(){
    return "23.4 €";
  }
}
