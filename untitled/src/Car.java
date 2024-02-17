public class Car {
    private String brand;
    private int year;
    private int mileage;

    public Car(String brand, int year, int mileage) {
        this.brand = brand;
        this.year = year;
        this.mileage = mileage;
    }

    public void displayCarList() {
        System.out.println("Car: " + brand);
        System.out.println("Year: " + year);
        System.out.println("Probeg: " + mileage);
    }
    public String getBrand() {
        return brand;
    }
    public int getYear() {
        return year;
    }
    public int getProbeg() {
        return mileage;
    }
}
