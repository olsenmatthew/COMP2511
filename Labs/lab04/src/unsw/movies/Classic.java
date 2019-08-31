package unsw.movies;

public class Classic implements Price {

    @Override
    public double getCharge(int daysRented) {
        double charge = 2;
        if (daysRented > 5)
            charge += (daysRented - 5);
        return charge;
    }

    @Override
    public Price makeRegular() {
        throw new UnsupportedOperationException("You can't make a classic movie regular");
    }

    @Override
    public Price makeChildrens() {
        throw new UnsupportedOperationException("You can't make a classic movie a children's movie");
    }

    @Override
    public Price makeClassic() {
        return this;
    }

}
