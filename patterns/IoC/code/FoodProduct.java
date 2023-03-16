package IoC;

/**
 * subclass of IProduct that return the food product
 */
public class FoodProduct implements IProduct{
  private String description = "food";

  @Override
  public String getDescription(){
    return this.description;
  }
}