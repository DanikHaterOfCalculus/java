import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {
    private List<Car> cars;
    private List<Rent> checkedoutcars;
    private TransactionHistory transactionHistory;

    public ManagementSystem() {
        this.cars = new ArrayList<>();
        this.checkedoutcars = new ArrayList<>();
        this.transactionHistory = new TransactionHistory();
        cars.add(new Car("Toyota Camry", 2021, 63000));
        cars.add(new Car("Honda Accord", 2020, 30000));
    }

    public void addcar(Car car) {
        cars.add(car);
        System.out.println("Car added");
    }

    public void getallcars() {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.println((i + 1) + ". Brand: " + car.getBrand() + ", Year: " + car.getYear() + ", Mileage: " + car.getMileage());
        }
    }

    public List<Car> GetAllcars() {
        return cars;
    }

    public void checkOutCar(Rent rent) {
        boolean found = false;
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car.getBrand().equals(rent.getCar())) {
                found = true;
                cars.remove(car);
                checkedoutcars.add(rent);
                System.out.println("Car checked out successfully!");
                addToTransactionHistory(rent);
                break;
            }
        }
        if (!found) {
            System.out.println("Car is not available for rent.");
        }
    }

    public void displayrentedCars() {
        if (checkedoutcars.isEmpty()) {
            System.out.println("No cars rented yet.");
        } else {
            System.out.println("Rented cars:");
            for (Rent checkedOutRent : checkedoutcars) {
                System.out.println("Customer: " + checkedOutRent.getCustomer() + ", Car: " + checkedOutRent.getCar() + ", Duration: " + checkedOutRent.getDuration() + " days");
                System.out.println("-------------------------");
            }
        }
    }

    public void addToTransactionHistory(Rent rent) {
        transactionHistory.addTransaction("Rent", rent.getCar(), rent.getCustomer());
    }
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}







