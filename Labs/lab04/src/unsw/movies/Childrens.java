package unsw.movies;

public class Childrens implements Price {

    @Override
    public double getCharge(int daysRented) {
        double charge = 1.5;
        if (daysRented > 3)
            charge += (daysRented - 3) * 1.5;
        return charge;
    }

    @Override
    public Price makeRegular() {
        throw new UnsupportedOperationException("You can't make a children's movie regular");
    }

    @Override
    public Price makeChildrens() {
        return this;
    }

    @Override
    public Price makeClassic() {
        return new Classic();
    }

}
