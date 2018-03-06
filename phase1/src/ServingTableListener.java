public interface ServingTableListener {


  /**
   * That dish has been changed
   * @param dish - MenuItem that has been changed (i.e cooked, update, served)
   */
  public void update(Dish dish);


}
