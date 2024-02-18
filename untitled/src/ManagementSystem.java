import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {
    private List<Car> cars;
    private List<Rent> checkedoutcars;
    public ManagementSystem(){
        this.cars= new ArrayList<>();
        this.checkedoutcars= new ArrayList<>();
        cars.add(new Car("Toyota Camry", 2021, 63000));
        cars.add(new Car("Honda Accord", 2020, 30000));
    }
    public void addcar(Car car){
        cars.add(car);
        System.out.println("Car added");
    }
    public void getallcars() {
        for (int i=0; i<cars.size();i++) {
            Car car=cars.get(i);
            System.out.println((i+1)+". Brand: " + car.getBrand() + ", Year: " + car.getYear() + ", Mileage: " + car.getMileage());
        }
    }
    public List<Car> GetAllcars(){
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
                break;
            }
        }
        if (found = false) {
            System.out.println("Car is not available for rent.");
        }}
    public void displayCheckedOutCars () {
            if (checkedoutcars.isEmpty()) {
                System.out.println("No cars checked out.");
            } else {
                System.out.println("Checked Out Cars:");
                for (Rent checkedOutRent : checkedoutcars) {
                    System.out.println("Customer: " + checkedOutRent.getCustomer() + ", Car: " + checkedOutRent.getCar() + ", Duration: " + checkedOutRent.getDuration());
                    System.out.println("-------------------------");
                }
            }
        }
    }






