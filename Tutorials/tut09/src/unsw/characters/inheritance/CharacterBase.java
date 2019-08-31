package unsw.characters.inheritance;

/**
 * The base class for characters.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class CharacterBase implements Character {

    private int healthPoints;

    private int x, y;

    public CharacterBase(int x, int y) {
        healthPoints = 100;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getHealthPoints() {
        return healthPoints;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void damage(int points) {
        healthPoints -= points;
    }
}
