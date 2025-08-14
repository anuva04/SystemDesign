class TransactionResponse {
    boolean isSuccess;
    String message;

    public TransactionResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public String toString() {
        if(isSuccess) {
            return "SUCCESS: " + message;
        } else {
            return "FAILED: " + message;
        }
    }
}