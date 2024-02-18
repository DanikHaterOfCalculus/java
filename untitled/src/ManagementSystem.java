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
        for (Car car : cars) {
            System.out.println("Brand: " + car.getBrand() + ", Year: " + car.getYear() + ", Mileage: " + car.getMileage());
        }
    }



}

