public interface ServerListener {

    /**
     * Server calls manager if a customer has a problem with the food
     * @param manager The manager that is being called
     */
    public void callManager(Manager manager);
}
