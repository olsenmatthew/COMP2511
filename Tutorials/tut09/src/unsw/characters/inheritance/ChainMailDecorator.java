/**
 *
 */
package unsw.characters.inheritance;

/**
 * Decorates a character with chain mail.
 *
 * @author Robert Clifton-Everest
 *
 */
public class ChainMailDecorator extends CharacterDecorator {

    public ChainMailDecorator(Character character) {
        super(character);
    }

    @Override
    public void damage(int points) {
        super.damage(points/2);
    }

}
