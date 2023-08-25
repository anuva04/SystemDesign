import java.util.*;
public class FactoryPattern {
    public static void main(String[] args) {
        String pizzaName = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter pizza name: ");
        pizzaName = sc.next();
        PizzaFactory pizzaFactory = new PizzaFactory();
        Pizza pizza = pizzaFactory.getPizza(pizzaName);
        if(pizza == null) {
            System.out.println(pizzaName + " is not made in our restaurant!");
            return;
        }
        System.out.println("Cheese: " + pizza.getCheese());
        System.out.println("Toppings: " + pizza.getToppings());
        System.out.println("Crust: " + pizza.getCrust());
    }
}

class PizzaFactory {
    public Pizza getPizza(String name) {
        if(name.equalsIgnoreCase("ThreeCheesePizza")) {
            return new ThreeCheesePizza();
        }
        if(name.equalsIgnoreCase("ChicagoDeepDishPizza")) {
            return new ChicagoDeepDishPizza();
        }
        return null;
    }
}

abstract class Pizza {
    protected String cheese;
    protected String toppings;
    protected String crust;

    protected Pizza(String cheese, String toppings, String crust) {
        this.cheese = cheese;
        this.toppings = toppings;
        this.crust = crust;
    }

    public String getCheese() {
        return this.cheese;
    }

    public String getToppings() {
        return this.toppings;
    }

    public String getCrust() {
        return this.crust;
    }
}

class ThreeCheesePizza extends Pizza {
    public ThreeCheesePizza() {
        super("Mozzarella + Cheddar + Parmesan", "Only cheese!", "Handmade thin crust");
    }
}

class ChicagoDeepDishPizza extends Pizza {
    public ChicagoDeepDishPizza() {
        super("Mozzarella + Parmesan", "Bacon + Pepperoni + Tomato", "Thick cornmeal crust");
    }
}

