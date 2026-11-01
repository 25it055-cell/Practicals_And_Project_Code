// Base abstract class
abstract class Shape {
    public abstract double area();
}


class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle (r=" + radius + ")";
    }
}


class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle (" + width + "x" + height + ")";
    }
}


class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    @Override
    public String toString() {
        return "Triangle (b=" + base + ", h=" + height + ")";
    }
}

public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 8.0),
            new Circle(2.0)
        };

        double runningTotal = 0;
        Shape largestShape = null;
        double maxArea = -1;

        System.out.println("--- Processing Shapes ---");
        for (Shape s : shapes) {
            double currentArea = s.area();
            runningTotal += currentArea;

            System.out.printf("%s -> Area: %.2f | Running Total: %.2f%n", 
                    s.toString(), currentArea, runningTotal);

            if (currentArea > maxArea) {
                maxArea = currentArea;
                largestShape = s;
            }
        }

        System.out.println("\n--- Summary ---");
        System.out.printf("Final Total Area: %.2f%n", runningTotal);
        if (largestShape != null) {
            System.out.printf("Largest Shape: %s with Area: %.2f%n", largestShape, maxArea);
        }
    }
}
