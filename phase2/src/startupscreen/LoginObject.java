package startupscreen;

/**
 * This class handles the login screen
 * @param <T> the worker type
 * @param <U> the worker's user credentials
 */
public class LoginObject<T, U> {

  private T type;
  private U userCredentials;

  public LoginObject(T type, U userCredentials){
    this.type = type;
    this.userCredentials = userCredentials;
  }

  /**
   * returns the worker's type
   * @return the worker's type
   */
  public T getType() {
    return type;
  }

  /**
   * Checks if the entered credentials match the systems user credentials
   * @param obj the user's credentials
   * @return returns true if they match and false if they do not
   */
  @Override
  public boolean equals(Object obj) {
    return userCredentials.equals((U) obj);
  }

  /**
   * gets the user's credentials
   * @return the user's credentials
   */
  public U getUserCredentials(){
     return userCredentials;
  }
}
