/**
 *
 */
package unsw.enrolment;

import java.util.ArrayList;
import java.util.List;

/**
 * A mark that is calculated from other marks using a 'MarkCalculator'.
 *
 * @author Robert Clifton-Everest
 *
 */
public class CalculatedMark implements Mark {

    private String assessment;
    private List<Mark> submarks;

    private MarkCalculator calculator;

    /**
     * Create a calculated mark for the given assessment and calculation
     * strategy.
     */
    public CalculatedMark(String assessment, MarkCalculator calculator) {
        this.assessment = assessment;
        this.calculator = calculator;
        this.submarks = new ArrayList<>();
    }

    @Override
    public String getAssessment() {
        return assessment;
    }

    @Override
    public int getMark() {
        return calculator.calculateMark(submarks);
    }

    @Override
    public int getMaximum() {
        return calculator.calculateMaximum(submarks);
    }

    public void addSubmark(Mark mark) {
        this.submarks.add(mark);
    }

    @Override
    public Mark removeMark(String assessment) {
        Mark removed = null;
        for (Mark mark : submarks) {
            removed = mark.removeMark(assessment);
            if (removed != null)
                break;
            if (mark.getAssessment().equals(assessment)) {
                removed = mark;
                submarks.remove(removed);
                break;
            }
        }
        return removed;
    }

    @Override
    public boolean updateMark(String assessment, int mark, int max) {
        for (Mark submark : submarks)
            if (submark.updateMark(assessment, mark, max))
                return true;
        return false;
    }

}
