package unsw.enrolment;

/**
 * A mark in the enrolment system.
 *
 * @author Robert Clifton-Everest
 *
 */
public interface Mark {

    /**
     * The assessment for this mark.
     * @return
     */
    public String getAssessment();

    /**
     * The mark that has been assigned or calculated. Assumed to be less than
     * the maximum.
     * @return
     */
    public int getMark();

    /**
     * The maximum obtainable mark.
     * @return
     */
    public int getMaximum();

    /**
     * Attempt to update the mark for the given assessment. If no mark exists
     * with this label or the mark cannot be updated this method returns false.
     * @param assessment
     * @param mark
     * @param max
     * @return
     */
    public boolean updateMark(String assessment, int mark, int max);

    /**
     * Removes and returns the mark for the given assessment, if this mark has
     * submarks. If no mark exists for that assessment, this method returns
     * null.
     * @param label
     * @return
     */
    public Mark removeMark(String label);
}
