import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Car car = new Car("Toyota", 2020, 5000);
        ManagementSystem managementSystem= new ManagementSystem();
        Rent rent=new Rent(1, "Era", "Toyota", 1);
        while (true){
            System.out.println("\nCar rental management system:");
            System.out.println("1. Add a new car");
            System.out.println("2. Rent a car");
            System.out.println("3. Display all cars");
            int choice=scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1: System.out.println("Enter car brand:");
                String brand=scanner.nextLine();
                System.out.println("Enter car year:");
                int year=scanner.nextInt();
                System.out.println("Enter car mileage:");
                int mileage= scanner.nextInt();
                managementSystem.addcar(new Car(brand, year, mileage));
                case 2: rent.displayRentDetails();
                case 3: System.out.println("All cars:");
                managementSystem.getallcars();
            }
        }








    }
}