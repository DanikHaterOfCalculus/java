import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ManagementSystem managementSystem= new ManagementSystem();
        while (true){
            System.out.println("\nCar rental management system:");
            System.out.println("1. Add a new car");
            System.out.println("2. Rent a car");
            System.out.println("3. Display all cars");
            System.out.println("4. Display checked out cars");
            System.out.println("5. Exit");
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
                break;
                case 2:
                    System.out.println("Enter your name:");
                    String customerName = scanner.nextLine();
                    System.out.println("Available Cars:");
                    managementSystem.getallcars();
                    System.out.println("Enter the number of the car you want to rent:");
                    int carChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (carChoice >= 1 && carChoice <= managementSystem.GetAllcars().size()) {
                        Car selectedCar = managementSystem.GetAllcars().get(carChoice - 1);
                        System.out.println("Enter rental duration (in days):");
                        int duration = scanner.nextInt();
                        Rent rent = new Rent(1, customerName, selectedCar.getBrand(), duration);
                        managementSystem.checkOutCar(rent);
                    } else {
                        System.out.println("Invalid car selection.");
                    }
                    break;
                case 3: System.out.println("All cars:");
                managementSystem.getallcars();
                break;
                case 4: System.out.println("Checked out cars:");
                managementSystem.displayCheckedOutCars();
                break;
                case 5: System.out.println("Exiting...");
                System.exit(0);
                break;
                default:System.out.println("Invalid choice. Please enter number between 1 and 5");
            }
        }








    }
}