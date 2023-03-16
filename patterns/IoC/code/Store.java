package IoC;

/**
 * class of a single store
 */
public class Store{
  private String storeDescription;
  private IProduct product;

   /**
    * costrutor
    * @param storeDescription
    * @param product
    */
  public Store(String storeDescription, IProduct product){
    this.storeDescription = storeDescription;
    this.product = product;
  }

  /**
   * product getter
   * @return a product
   */
  public IProduct getProduct(){
    return product;
  }

  /**
   * product's description getter
   * @return store's product description
   */
  public String getStoreDescription(){
    return storeDescription;
  }
}
