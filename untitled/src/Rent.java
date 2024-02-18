public class Rent {
    private static int nextId=1;
    private int rentalId;
    private String customer;
    private String car;
    private int duration;
    public Rent(int rentalId, String customer, String car, int duration) {
        this.rentalId = nextId++;
        this.customer = customer;
        this.car = car;
        this.duration = duration;
    }
    public int getRentalId() {
        return rentalId;
    }
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getCar() {
        return car;
    }
    public void setCar(String car) {
        this.car = car;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void displayRentDetails() {
        System.out.println("Rental ID: " + rentalId);
        System.out.println("Customer: " + customer);
        System.out.println("Car: " + car);
        System.out.println("Duration: " + duration + " days");
    }
}
