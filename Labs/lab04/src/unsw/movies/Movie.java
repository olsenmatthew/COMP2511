package unsw.movies;

public class Movie {
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    public static final int CHILDRENS = 2;

    private String title;
    private Price price;

    public Movie(String title) {
        this.title = title;
        this.price = new NewRelease();
    }

    public String getTitle() {
        return title;
    }

    public double getCharge(int daysRented) {
        return price.getCharge(daysRented);
    }

    public void makeRegular() {
        price = price.makeRegular();
    }

    public void makeChildrens() {
        price = price.makeChildrens();
    }

    public void makeClassic() {
        price = price.makeClassic();
    }

}