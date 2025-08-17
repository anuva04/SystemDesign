class Screen {
    private final String screenId;
    private final int rows;
    private final int columns;
    private final Theatre theatre;

    public Screen(String screenId, Theatre theatre, int rows, int columns) {
        this.screenId = screenId;
        this.theatre = theatre;
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public String getScreenId() {
        return screenId;
    }
}