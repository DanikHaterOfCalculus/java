public class Main {
    public static void main(String[] args) {

        Car car = new Car("Toyota", 2020, 5000);
        Rent rent=new Rent(1, "Era", "Toyota", 1);
        car.displaycar();
        rent.displayRentDetails();


    }
}