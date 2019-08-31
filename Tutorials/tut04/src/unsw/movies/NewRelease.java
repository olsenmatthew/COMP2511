package unsw.movies;

public class NewRelease implements Price {

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3;
    }

}
