package Restaurant;

/**
 * This represents the status of the dish, i.e. whether it is being cooked, served, returned or rejected
 */
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
      return "Sent";
    }
  },

}
