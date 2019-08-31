package unsw.movies;

public class Regular implements Price {

    @Override
    public double getCharge(int daysRented) {
        double charge = 2;
        if (daysRented > 2)
            charge += (daysRented - 2) * 1.5;
        return charge;
    }

    @Override
    public Price makeRegular() {
        return this;
    }

    @Override
    public Price makeChildrens() {
        throw new UnsupportedOperationException("You can't make a regular movie a children's movie");
    }

    @Override
    public Price makeClassic() {
        return new Classic();
    }

}
