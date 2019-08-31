package example;

/**
 * A lecturer.
 * @author Robert Clifton-Everest
 *
 */
public class Lecturer extends StaffMember {

    private String school;
    private char academicStatus;

    /**
     * Create a lecturer with the given name, salary, school and academic level.
     * @param name The full name of the staff member.
     * @param salary The staff member's yearly salary in dollars.
     * @param school The lecturer's school
     * @param academicStatus The letter code for the lecturer's academic status.
     */
    public Lecturer(String name, int salary, String school, char academicStatus) {
        super(name, salary);
        this.school = school;
        this.academicStatus = academicStatus;
    }

    /**
     * Get the code for the lecturer's academic status.
     * @return
     */
    public char getAcademicStatus() {
        return academicStatus;
    }

    /**
     * Change the lecturer's academic status.
     * @param academicStatus The letter code for the new status.
     */
    public void setAcademicStatus(char academicStatus) {
        this.academicStatus = academicStatus;
    }

    @Override
    public String toString() {
        return super.toString() + "[school=" + school + ", academicStatus="
                + academicStatus + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        Lecturer other = (Lecturer) obj;
        if (school.equals(other.school) &&
                academicStatus == other.academicStatus)
            return true;
        return false;
    }

    /**
     * Get the lecturer's school.
     * @return The name of the school.
     */
    public String getSchool() {
        return school;
    }
}
