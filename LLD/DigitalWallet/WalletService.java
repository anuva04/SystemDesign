import java.util.*;
class WalletService {
    private final Map<String, User> userIdToUserMap;
    private final Map<String, Wallet> userIdToWalletMap;
    int userIdCounter = 1;

    public WalletService() {
        this.userIdToWalletMap = new HashMap<>();
        this.userIdToUserMap = new HashMap<>();
    }

    public Wallet registerUser(String name) {
        String userId = "user" + userIdCounter++;
        User user = new User(userId, name);
        Wallet wallet = new Wallet(userId);

        userIdToUserMap.put(userId, user);
        userIdToWalletMap.put(userId, wallet);
        return wallet;
    }

    public User getUser(String userId) {
        return userIdToUserMap.get(userId);
    }
}