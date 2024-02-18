public class Rent {
    private static int nextId = 1;
    private int rentalId;
    private String customer;
    private Car car;
    private int year;
    private int duration;

    public Rent(String customer, Car car, int year, int duration) {
        this.rentalId = nextId++;
        this.customer = customer;
        this.car = car;
        this.year = year;
        this.duration = duration;
    }

    public int getRentalId() {
        return rentalId;
    }

    public String getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }
}