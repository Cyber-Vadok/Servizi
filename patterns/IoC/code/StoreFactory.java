package IoC;

/**
 * class to create more stores reducing dependencies with products
 */
public class StoreFactory {
  /**
   * create a new store
   * @param storeType: type of store
   * @return a new store
   */
  public static Store getStore(String storeType){
    if (storeType.equalsIgnoreCase("Food store")){
      return new Store("Food store", ProductFactory.getProduct("Food")); // dependency injection
    } else if (storeType.equalsIgnoreCase("Drink store")){
      return new Store("Drink store", ProductFactory.getProduct("Drink")); // dependency injection
    }

    return null;
  }
}
