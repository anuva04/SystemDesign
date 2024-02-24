/**
 * Visitor pattern is used in cases we need to add new methods for a group of classes but we don't want to make major changes in the class's code.
 * In that case, the new methods are added in a separate class called visitor.
 * The visitor visits the concerned class using its accept method.
 * So, honestly, we do need to add this accept() method in classes (which is basically a code change) but it is very minimal and risk-free.
 * In this example, the different shapes implement accept() method to allow a visitor to execute its visit() method on the shape.
 * The visitor class has methods tailored for different types of shapes, and are leveraged using method overloading.
 */

import java.util.*;
class VisitorPattern {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(2));
        shapes.add(new Circle(3));
        shapes.add(new Square(4));
        shapes.add(new Rectangle(2, 3));

        ShapeVisitor visitor = new ShapeVisitor();

        for(Shape shape : shapes) {
            shape.accept(visitor);
        }
    }
}

interface Shape {
    public void accept(ShapeVisitor v);
}

class Circle implements Shape {
    public int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public void accept(ShapeVisitor v) {
        v.visit(this);
    }
}

class Square implements Shape {
    public int length;

    public Square(int length) {
        this.length = length;
    }

    @Override
    public void accept(ShapeVisitor v) {
        v.visit(this);
    }
}

class Rectangle implements Shape {
    public int length, width;

    public Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public void accept(ShapeVisitor v) {
        v.visit(this);
    }
}

class ShapeVisitor {
    public void visit(Circle circle) {
        double perimeter = 2 * 3.14 * circle.radius;
        double area = 3.14 * circle.radius * circle.radius;
        System.out.println("[Circle] Radius: " + circle.radius + ", Perimeter: " + perimeter + ", Area: " + area);
    }

    public void visit(Square square) {
        int perimeter = 4 * square.length;
        int area = square.length * square.length;
        System.out.println("[Square] Length: " + square.length + ", Perimeter: " + perimeter + ", Area: " + area);
    }

    public void visit(Rectangle rectangle) {
        int perimeter = 2 * (rectangle.length + rectangle.width);
        int area = rectangle.length * rectangle.width;
        System.out.println("[Rectangle] Length: " + rectangle.length + ", Width: " + rectangle.width + ", Perimeter: " + perimeter + ", Area: " + area);
    }
}