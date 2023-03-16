package APIGateway;

/**
 * class that offer a personalized entry point to clients
 */
public class APIGateway {
  private IImageClient imageClient;
  private IPriceClient priceClient;

  /**
   * constructor
   */
  public APIGateway(){
    this.imageClient = new ImageClientImp();
    this.priceClient = new PriceClientImp();
  }

  /**
   * desktop product getter (set view for desktop clients)
   * @return a desktop's view
   */
  public DesktopProduct getProductDesktop(){
    DesktopProduct desktopProduct = new DesktopProduct();

    desktopProduct.setImagePath(imageClient.getImagePath()); // hiding the microservice URI
    desktopProduct.setPrice(priceClient.getPrice()); // hiding the microservice URI

    return desktopProduct;
  }

  /**
   * mobile product getter (set view for mobile clients)
   * @return a mobile's view
   */
  public MobileProduct getProductMobile(){
    MobileProduct mobileProduct = new MobileProduct();

    mobileProduct.setPrice(priceClient.getPrice()); // hiding the microservice URI

    return mobileProduct;
  }
}
