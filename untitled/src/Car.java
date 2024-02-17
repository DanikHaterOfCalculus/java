class Car {
    private String marka;
    private int year;
    private int probeg;

    public Car(String marka, int year, int probeg) {
        this.marka=marka;
        this.year=year;
        this.probeg=probeg;
    }
    public void displaycar() {
        System.out.println("marka " + marka);
        System.out.println("year " + year);
        System.out.println("probeg" + probeg);
    }
}