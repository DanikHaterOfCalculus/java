import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Car car = new Car("Toyota", 2020, 5000);
        Rent rent=new Rent(1, "Era", "Toyota", 1);
        while (true){
            System.out.println("\nCar rental management system:");
            System.out.println("1. Add a new car");
            System.out.println("2. Rent a car");
            int choice=scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1: car.displaycar();
                case 2: rent.displayRentDetails();
            }
        }








    }
}