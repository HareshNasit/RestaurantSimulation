package startupscreen;

public class LoginObject<T, U> {

  private T type;
  private U userCredentials;

  public LoginObject(T type, U userCredentials){
    this.type = type;
    this.userCredentials = userCredentials;
  }

  public T getType() {
    return type;
  }

  @Override
  public boolean equals(Object obj) {
    return userCredentials.equals((U) obj);
  }

  public U getUserCredentials(){
     return userCredentials;
  }
}
