package APIGateway;

/**
 * class that identify a product seen from desktop device
 */
public class DesktopProduct {
  private String price;
  private String imagePath;

  /**
   * costructor
   */
  public DesktopProduct(){
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

  /**
   * get product's image path
   * @return the product's image path
   */
  public String getImagePath(){
    return imagePath;
  }

  /**
   * set product's image path
   * @param imagePath of the product
   */
  public void setImagePath(String imagePath){
    this.imagePath = imagePath;
  }
}
