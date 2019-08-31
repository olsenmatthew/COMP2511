package unsw.movies;

public class NewRelease implements Price {

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3;
    }

    @Override
    public Price makeRegular() {
        return new Regular();
    }

    @Override
    public Price makeChildrens() {
        return new Childrens();
    }

    @Override
    public Price makeClassic() {
        throw new UnsupportedOperationException("You can't make a new release movie a classic");
    }

}
