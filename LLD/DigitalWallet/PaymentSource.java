interface PaymentSource {
    boolean transferMoney(double amount, String walletId);
}