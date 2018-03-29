package Restaurant;

public enum WorkerType {

  SERVER {
    @Override
    public String toString() {
      return "Server";
    }
  },
  COOK{
    @Override
    public String toString() {
    return "Cook";
  }
  },
  MANAGER{
    @Override
    public String toString() {
      return "Manager";
    }
  }

}
