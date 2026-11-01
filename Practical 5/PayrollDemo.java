// Base abstract class
abstract class Employee {
    private String name;
    private String id;

    public Employee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }

    public abstract double monthlySalary();
}

class FullTime extends Employee {
    private double fixedSalary;

    public FullTime(String name, String id, double fixedSalary) {
        super(name, id);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public double monthlySalary() {
        return fixedSalary;
    }
}


class PartTime extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public PartTime(String name, String id, double hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double monthlySalary() {
        return hoursWorked * hourlyRate;
    }
}

class Intern extends Employee {
    private double stipend;

    public Intern(String name, String id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    public double monthlySalary() {
        return stipend;
    }
}

// Main class to run the program
public class PayrollDemo {
    public static void main(String[] args) {
        
        Employee[] staff = {
            new FullTime("Alice Smith", "FT001", 5000.00),
            new PartTime("Bob Jones", "PT002", 80, 25.00),
            new Intern("Charlie Brown", "IN003", 1200.00),
            new FullTime("Diana Prince", "FT004", 6200.00),
            new Intern("Evan Wright", "IN005", 1000.00)
        };

        double totalPayroll = 0;

        System.out.println("--- Monthly Payroll Report ---");
        for (Employee emp : staff) {
            double salary = emp.monthlySalary();
            totalPayroll += salary;

            // Basic employee info output
            System.out.printf("ID: %s | Name: %-15s | Salary: $%.2f", 
                    emp.getId(), emp.getName(), salary);

            if (emp instanceof Intern) {
                System.out.print("  [NOTE: Intern profile - Stipend validation required]");
            }
            System.out.println();
        }

        System.out.println("\n-------------------------------------------");
        System.out.printf("Total Company Payroll Expenses: $%.2f%n", totalPayroll);
    }
}
