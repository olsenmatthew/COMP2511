package example;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A staff member
 * @author Robert Clifton-Everest
 *
 */
public class StaffMember {
    private String name;
    private int salary;
    private LocalDate hireDate, endDate;

    /**
     * Creates a staff member with the given name and salary.
     * @param name The full name of the staff member.
     * @param salary The staff member's yearly salary in dollars.
     */
    public StaffMember(String name, int salary) {
        this.name = name;
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.endDate = null; //Staff member is currently employed.
    }

    /**
     * Returns the staff member's name.
     * @return The full name of the staff member.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the staff member's name.
     * @param name The staff member's new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the staff member's salary.
     * @return The staff member's yearly salary in dollars.
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Get the staff member's hire date.
     * @return The date the staff member was hired.
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Get the date the staff member's employment ceased.
     * @return The date the staff member's employment ended or null if they are
     *         still employed.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Cease the staff members employment and record the date on which they
     * left.
     */
    public void ceaseEmployment() {
        endDate = LocalDate.now();
    }

    /**
     * Assuming the staff member employment previously ended, re-hire them
     * preserving their details.
     */
    public void rehire() {
        hireDate = LocalDate.now();
        endDate = null;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + name + ", salary=" + salary +
                ", hireDate=" + hireDate + ", endDate=" + endDate + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StaffMember other = (StaffMember) obj;
        if (name.equals(other.name) && salary == other.salary &&
                hireDate.equals(other.hireDate) &&
                Objects.equals(endDate, other.endDate))
            return true;
        return false;
    }
}
