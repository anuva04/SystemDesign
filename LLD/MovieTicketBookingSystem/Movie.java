class Movie {
    private final String name;
    private final String description;
    private final int durationInMins;

    public Movie(String name, String description, int durationInMins) {
        this.name = name;
        this.description = description;
        this.durationInMins = durationInMins;
    }

    public String getName() {
        return name;
    }
}