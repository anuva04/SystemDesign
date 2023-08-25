/**
    Factory pattern is used when we want to hide the logic of object creation from users.
    In this pattern, a factory class containing a method "getObject" is exposed to the user.
    In the implementation, "getObject" method returns an object of an abstract class.
    Based on input provided by users at runtime, "getObject" chooses the type of concrete object to create.
    This eliminates the need of coupling concrete object creation logic with client code and hence promotes loose-coupling.
**/


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
        pizza.getRecipe();
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

    protected abstract void getRecipe();

    protected void printPizzaDetails() {
        System.out.println("Cheese: " + this.cheese);
        System.out.println("Toppings: " + this.toppings);
        System.out.println("Crust: " + this.crust);
    }
}

class ThreeCheesePizza extends Pizza {
    @Override
    public void getRecipe() {
        this.cheese = "Mozzarella + Cheddar + Parmesan";
        this.toppings = "Only cheese!";
        this.crust = "Handmade extra thin crust";
        System.out.println("Cooking a three-cheese pizza...");
        printPizzaDetails();
    }
}

class ChicagoDeepDishPizza extends Pizza {
    @Override
    public void getRecipe() {
        this.cheese = "Mozzarella + Parmesan";
        this.toppings = "Bacon + Pepperoni + Tomato";
        this.crust = "Thick cornmeal crust";
        System.out.println("Cooking a Chicago Deep Dish pizza...");
        printPizzaDetails();
    }
}

