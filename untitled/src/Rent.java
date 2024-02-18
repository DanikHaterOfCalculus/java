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
    public String getCustomer() {
        return customer;
    }
    public String getCar() {
        return car;
    }
    public int getDuration() {
        return duration;
    }}

