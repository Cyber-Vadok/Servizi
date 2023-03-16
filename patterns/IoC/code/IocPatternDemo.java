package IoC;

public class IocPatternDemo {
  public static void main(String[] args) {
    /**
     * food store
     */
    Store foodStore = StoreFactory.getStore("Food store");
    try {
      System.out.println(foodStore.getProduct().getDescription());
    } catch (Exception e) {
      e.printStackTrace();
    }

    /**
     * drink store
     */
    Store drinkStore = StoreFactory.getStore("Drink store");
    try {
      System.out.println(drinkStore.getProduct().getDescription());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
