package Restaurant;

public enum DishStatus {
  COOKING {
    @Override
    public String toString() {
      return "Cooking";
    }
  },
  PICKUP {
    @Override
    public String toString() {
      return "Pick Up";
    }

  },
  REJECTED {
    @Override
    public String toString() {
      return "Rejected";
    }
  },
  RETURNED {
    @Override
    public String toString() {
      return "Returned";
    }
  },
  SERVED {
    @Override
    public String toString() {
      return "Serving";
    }
  },
  ORDERED {
    @Override
    public String toString() {
      return "Ordered";
    }
  },
  SENT {
    @Override
    public String toString() {
      return "Ordered";
    }
  },

}
