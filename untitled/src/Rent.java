import java.util.Scanner;
public class Rent {
    private static int nextId = 1;
    private int rentalId;
    private String customer;
    private Vehicle vehicle;
    private int duration;

    public Rent(String customer, Vehicle vehicle, int duration) {
        this.rentalId = nextId++;
        this.customer = customer;
        this.vehicle = vehicle;
        this.duration = duration;
    }

    public int getRentalId() {
        return rentalId;
    }

    public String getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDuration() {
        return duration;
    }

}