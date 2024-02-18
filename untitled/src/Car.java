public class Car extends Vehicle {
    public Car(String brand, int year, int mileage) {
        super(brand, year, mileage);
    }
    @Override
    public String toString() {
        return "Car brand:" + getBrand() + ", year:" + getYear() + ", mileage:" + getMileage();
    }
}
