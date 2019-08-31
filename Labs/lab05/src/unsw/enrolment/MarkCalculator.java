/**
 *
 */
package unsw.enrolment;

import java.util.List;

/**
 * A method for calculating a mark based on submarks.
 *
 * @author Robert Clifton-Everest
 *
 */
public interface MarkCalculator {
    public int calculateMark(List<Mark> marks);

    public int calculateMaximum(List<Mark> marks);
}
