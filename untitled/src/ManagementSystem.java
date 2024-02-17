import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {
    private List<Car> cars;
    private List<Rent> checkedoutcars;
    public ManagementSystem(){
        this.cars= new ArrayList<>();
        this.checkedoutcars= new ArrayList<>();
    }
    public void addcar(Car car){
        cars.add(car);
        System.out.println("Car added");
    }
    public List<Car> getallcars(){
        return cars;
    }


}

