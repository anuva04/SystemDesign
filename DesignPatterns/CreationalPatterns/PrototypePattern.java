/**
 * Prototype pattern is used when we want to create a multiple copies of an object without having to copy each parameter separately.
 * It can be used to create both shallow and deep copies.
 */

import java.util.Formatter;
public class PrototypePattern {
    public static void main(String[] args) {
        Car car = new Car(15, "black", 5);

        Car deepCopy = car.deepClone();
        Car shallowCopy = car.shallowClone();

        Formatter formatter = new Formatter();
        formatter.format("------------------------------------------------------------------------------------------\n");
        formatter.format("%20s %20s %20s %20s\n", "Parameter", "Original", "DeepClone", "ShallowClone");
        formatter.format("------------------------------------------------------------------------------------------\n");
        formatter.format("%20s %20s %20s %20s\n", "Object", car, deepCopy, shallowCopy);
        formatter.format("%20s %20s %20s %20s\n", "Mileage", car.mileage, deepCopy.mileage, shallowCopy.mileage);
        formatter.format("%20s %20s %20s %20s\n", "Color", car.color, deepCopy.color, shallowCopy.color);
        formatter.format("%20s %20s %20s %20s\n", "Seats", car.seats, deepCopy.seats, shallowCopy.seats);

        System.out.println(formatter);
    }
}

class Car {
    public int mileage;
    public String color;
    public int seats;

    public Car(int mileage, String color, int seats) {
        this.mileage = mileage;
        this.color = color;
        this.seats = seats;
    }
    public Car deepClone() {
        Car car = new Car(this.mileage, this.color, this.seats);
        return car;
    }

    public Car shallowClone() {
        return this;
    }
}

