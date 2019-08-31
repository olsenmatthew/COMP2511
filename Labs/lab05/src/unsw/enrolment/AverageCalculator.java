/**
 *
 */
package unsw.enrolment;

import java.util.List;

/**
 * A mark calculator that averages all marks.
 * @author Robert Clifton-Everest
 *
 */
public class AverageCalculator implements MarkCalculator {

    @Override
    public int calculateMark(List<Mark> marks) {
        int sum = 0;
        for (Mark mark : marks)
            sum += mark.getMark();
        return Math.round(sum/1f*marks.size());
    }

    @Override
    public int calculateMaximum(List<Mark> marks) {
        int sum = 0;
        for (Mark mark : marks)
            sum += mark.getMaximum();
        return Math.round(sum/1f*marks.size());
    }

}
