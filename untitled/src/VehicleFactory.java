public class VehicleFactory {
    public static Car createCar(String brand, int year, int mileage) {
        return new Car(brand, year, mileage);
    }
}
