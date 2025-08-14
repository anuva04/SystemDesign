class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
}