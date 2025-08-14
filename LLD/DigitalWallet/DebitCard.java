class DebitCard implements PaymentSource {
    @Override
    public boolean transferMoney(double amount, String walletId) {
        return true;
    }
}