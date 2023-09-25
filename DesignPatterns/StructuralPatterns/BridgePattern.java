/**
 * Bridge pattern helps in separating out orthogonal properties into separate classes so as to reduce the number of combinations.
 * In this example, if Type is added as a property in Card class, for every new Type, a new class would have to be created for every Card.
 * Similarly, for every new Card, a new class would have to created for every Type.
 * This would lead to creation of N(number of cards) * M(number of types) classes
 * However, using this pattern, a new Type or Card can be created independently.
 * Hence, there will be a total of N+M classes only.
 */
class BridgePattern {
    public static void main(String[] args) {
        Card sbiGoldCard = new SbiCard(new GoldType());
        sbiGoldCard.withdraw(50000);
        sbiGoldCard.withdraw(120000);

        Card axisPlatinumCard = new AxisCard(new PlatinumType());
        axisPlatinumCard.withdraw(80000);
    }
}

abstract class Card {
    protected Type type;
    protected Card(Type type) {
        this.type = type;
    }
    protected void withdraw(int amount) {
        if(amount > type.getLimit()) {
            System.out.println("Failure. Amount " + amount + " exceeds daily limit.");
        } else {
            this.withdrawFromBank(amount);
        }
    }

    protected abstract void withdrawFromBank(int amount);
}

class SbiCard extends Card {
    public SbiCard(Type type) {
        super(type);
    }
    @Override
    public void withdrawFromBank(int amount) {
        System.out.println("Calling SBI API... to withdraw amount: " + amount);
    }
}

class AxisCard extends Card {
    public AxisCard(Type type) {
        super(type);
    }
    @Override
    public void withdrawFromBank(int amount) {
        System.out.println("Calling Axis Bank API... to withdraw amount: " + amount);
    }
}

class Type {
    private int limit;

    protected Type(int limit) {
        this.limit = limit;
    }
    public int getLimit() {
        return limit;
    }
}

class StandardType extends Type {
    public StandardType() {
        super(20000);
    }
}

class GoldType extends Type {
    public GoldType() {
        super(50000);
    }
}

class PlatinumType extends Type {
    public PlatinumType() {
        super(100000);
    }
}