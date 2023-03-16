package APIGateway;

/**
 * subclass of IImageClient that return the image path
 */
public class ImageClientImp implements IImageClient{
  
  @Override
  public String getImagePath(){
    return "/img/prod1.jpg";
  }
}
