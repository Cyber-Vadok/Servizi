package IoC;

/**
 * subclass of IProduct that return the drink product
 */
public class DrinkProduct implements IProduct{
  private String description = "drink";

  @Override
  public String getDescription(){
    return this.description;
  }
}