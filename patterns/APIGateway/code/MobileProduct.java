package APIGateway;

/**
 * class that identify a product seen from mobile device
 */
public class MobileProduct {
  private String price;

  /**
   * costructor
   */
  public MobileProduct(){
  }

  /**
   * get product's price
   * @return the product's price
   */
  public String getPrice(){
    return price;
  }

  /**
   * set product's price
   * @param price of the product
   */
  public void setPrice(String price){
    this.price = price;
  }
}
