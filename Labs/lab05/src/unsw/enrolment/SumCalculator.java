/**
 *
 */
package unsw.enrolment;

import java.util.List;

/**
 * A mark calculator that sums all submarks.
 *
 * @author Robert Clifton-Everest
 *
 */
public class SumCalculator implements MarkCalculator {

    @Override
    public int calculateMark(List<Mark> marks) {
        int sum = 0;
        for (Mark mark : marks)
            sum += mark.getMark();
        return sum;
    }

    @Override
    public int calculateMaximum(List<Mark> marks) {
        int sum = 0;
        for (Mark mark : marks)
            sum += mark.getMaximum();
        return sum;
    }

}
