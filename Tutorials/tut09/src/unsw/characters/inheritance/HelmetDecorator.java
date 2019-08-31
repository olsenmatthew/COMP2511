package unsw.characters.inheritance;

/**
 * Decorates a character with a helmet.
 *
 * @author Robert Clifton-Everest
 *
 */
public class HelmetDecorator extends CharacterDecorator {

    public HelmetDecorator(Character character) {
        super(character);
    }

    @Override
    public void damage(int points) {
        super.damage(Math.max(points - 1, 0));
    }

}
