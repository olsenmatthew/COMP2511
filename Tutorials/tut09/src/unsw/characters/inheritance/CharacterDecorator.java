/**
 *
 */
package unsw.characters.inheritance;

/**
 * A decorator for a Character.
 *
 * @author Robert Clifton-Everest
 *
 */
public class CharacterDecorator implements Character {

    private Character character;

    public CharacterDecorator(Character character) {
        this.character = character;
    }

    @Override
    public int getHealthPoints() {
        return character.getHealthPoints();
    }

    @Override
    public int getX() {
        return character.getX();
    }

    @Override
    public int getY() {
        return character.getY();
    }

    @Override
    public void damage(int points) {
        character.damage(points);
    }

    @Override
    public boolean canMove(int dx, int dy) {
        return character.canMove(dx, dy);
    }

    @Override
    public void attack(Character victim) {
        character.attack(victim);
    }

}
