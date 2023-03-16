package IoC;

import java.util.ArrayList;
import java.util.List;

/**
 * class to create more products reducing dependencies with stores
 */
public class ProductFactory {
  /**
   * create a new product
   * @param ProductType
   * @return a new product
   */
  public static IProduct getProduct(String ProductType){
    if (ProductType == null){
      return null;
    }
    if (ProductType.equalsIgnoreCase("Drink")){
      return new DrinkProduct();
    } else if (ProductType.equalsIgnoreCase("Food")){
      return new FoodProduct();
    }
    return null;
  }

  /**
   * create a list of products
   * @param ListProductType
   * @return a list of products
   */
  public static List<IProduct> getListProduct(String ListProductType){
    if (ListProductType == null){
      return null;
    }
    if (ListProductType.equalsIgnoreCase("Food and Drink")){
      List<IProduct> listProduct = new ArrayList<IProduct>();
      listProduct.add(new FoodProduct());
      listProduct.add(new DrinkProduct());
    }
    return null;
  }
}
