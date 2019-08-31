package unsw.movies;

public class Movie {
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    public static final int CHILDRENS = 2;

    private String title;
    private Price price;

    public Movie(String title, Price price) {
        this.title = title;
        this.price = price;
    }

    public void setPrice(Price arg) {
        price = arg;
    }

    public String getTitle() {
        return title;
    }

    public double getCharge(int daysRented) {
//        double thisAmount = 0;
//
//        switch (priceCode) {
//        case Movie.REGULAR:
//            thisAmount += 2;
//            if (daysRented > 2)
//                thisAmount += (daysRented - 2) * 1.5;
//            break;
//        case Movie.NEW_RELEASE:
//            thisAmount += daysRented * 3;
//            break;
//        case Movie.CHILDRENS:
//            thisAmount += 1.5;
//            if (daysRented > 3)
//                thisAmount += (daysRented - 3) * 1.5;
//            break;
//        }
//        return thisAmount;
        return price.getCharge(daysRented);
    }

}