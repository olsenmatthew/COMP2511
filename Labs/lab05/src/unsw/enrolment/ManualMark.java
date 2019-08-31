/**
 *
 */
package unsw.enrolment;

import java.util.Observable;

/**
 * A mark that is entered in manually.
 *
 * @author Robert Clifton-Everest
 *
 */
public class ManualMark extends Observable implements Mark {

    private String assessment;
    private int mark;

    private int maximum;

    /**
     * Create a ManualMark.
     * @param assessment
     * @param mark
     * @param maximum
     */
    public ManualMark(String assessment, int mark, int maximum) {
        this.assessment = assessment;
        this.mark = mark;
        this.maximum = maximum;
    }

    @Override
    public String getAssessment() {
        return assessment;
    }

    @Override
    public int getMark() {
        return mark;
    }

    @Override
    public int getMaximum() {
        return maximum;
    }

    @Override
    public boolean updateMark(String assessment, int mark, int max) {
        if (this.assessment.equals(assessment)) {
            this.mark = mark;
            this.maximum = max;
            setChanged();
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Mark removeMark(String assessment) {
        return null;
    }

}
