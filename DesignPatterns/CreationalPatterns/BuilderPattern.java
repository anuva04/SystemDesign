/**
 * Builder pattern is used in cases where object creation is a complex process
 * Complexity arises when a class has several fields and not all of them are necessary during object creation
 * This pattern provides separate methods for setting each of the fields
 * Once the final build() method is called, the object is created and thereafter it is immutable.`
 */

public class BuilderPattern {
    public static void main(String[] args){
        System.out.println("Preparing medium Caesar salad...");
        Salad caesarSalad = new Salad.Builder(Size.MEDIUM)
                .addLettuce()
                .addBrocolli()
                .addJalapeno()
                .addTomato()
                .addDressing(Dressing.THOUSAND_ISLAND)
                .build();
        System.out.println(caesarSalad);

        System.out.println("Preparing large Russian salad...");
        Salad russianSalad = new Salad.Builder(Size.LARGE)
                .addLettuce()
                .addBrocolli()
                .addTomato()
                .addMushroom()
                .addDressing(Dressing.MAYO)
                .addJalapeno()
                .build();
        System.out.println(russianSalad);
    }
}

class Salad {
    private Size size;
    private boolean lettuce;
    private boolean brocolli;
    private boolean tomato;
    private boolean jalapeno;
    private boolean mushroom;
    private Dressing dressing;

    private int price;

    public static class Builder {
        private Size size;
        private boolean lettuce = false;
        private boolean brocolli = false;
        private boolean tomato = false;
        private boolean jalapeno = false;
        private boolean mushroom = false;
        private Dressing dressing = null;

        private int price = 0;

        public Builder(Size size) {
            this.size = size;

            switch(size) {
                case SMALL:
                    price += 20;
                    break;
                case MEDIUM:
                    price += 30;
                    break;
                case LARGE:
                    price += 40;
                    break;
                default:
                    break;
            }
        }

        public Builder addLettuce() {
            lettuce = true;
            price += 5;
            return this;
        }

        public Builder addBrocolli() {
            brocolli = true;
            price += 15;
            return this;
        }

        public Builder addTomato() {
            tomato = true;
            price += 5;
            return this;
        }

        public Builder addJalapeno() {
            jalapeno = true;
            price += 7;
            return this;
        }

        public Builder addMushroom() {
            mushroom = true;
            price += 10;
            return this;
        }

        public Builder addDressing(Dressing dressing) {
            this.dressing = dressing;
            price += 12;
            return this;
        }

        public Salad build() {
            return new Salad(this);
        }
    }
    private Salad(Builder builder) {
        size = builder.size;
        lettuce = builder.lettuce;
        brocolli = builder.brocolli;
        tomato = builder.tomato;
        jalapeno = builder.jalapeno;
        mushroom = builder.mushroom;
        dressing = builder.dressing;

        price = builder.price;
    }

    @Override
    public String toString() {
        String out = "Here's your salad with: ";
        if(lettuce) out += "lettuce, ";
        if(brocolli) out += "brocolli, ";
        if(tomato) out += "tomato, ";
        if(jalapeno) out += "jalapeno, ";
        if(mushroom) out += "mushroom, ";
        if(dressing != null) out += (dressing);

        out += ("\nPrice: " + price);

        return out;
    }
}

enum Size {
    SMALL, MEDIUM, LARGE
}

enum Dressing {
    HONEY_MUSTARD, MAYO, THOUSAND_ISLAND
}