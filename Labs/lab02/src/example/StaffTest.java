package example;

/**
 * Testing for the staff classes.
 * @author Robert Clifton-Everest
 *
 */
public class StaffTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        StaffMember staffMember = new StaffMember("Johnny Admin", 40000);
        Lecturer lecturer = new Lecturer("Sarah Casual", 60000, "CSE", 'A');

        printStaffDetails(staffMember);
        printStaffDetails(lecturer);

        StaffMember staff1, staff2;
        StaffMember lecturer1, lecturer2;

        staff1 = new StaffMember("Test Member", 1);
        if (!staff1.equals(staff1))
            System.out.println("TEST FAILED");

        staff2 = new StaffMember("Test Member", 1);
        if (!staff1.equals(staff2))
            System.out.println("TEST FAILED");
        if (!staff2.equals(staff1))
            System.out.println("TEST FAILED");

        lecturer1 = new Lecturer("Test Member", 1, "CSE", 'A');
        if (!lecturer1.equals(lecturer1))
            System.out.println("TEST FAILED");

        lecturer2 = new Lecturer("Test Member", 1, "CSE", 'A');
        if (!lecturer1.equals(lecturer2))
            System.out.println("TEST FAILED");
        if (!lecturer2.equals(lecturer1))
            System.out.println("TEST FAILED");

        if (lecturer1.equals(staff1))
            System.out.println("TEST FAILED");
        if (staff1.equals(lecturer1))
            System.out.println("TEST FAILED");

        if (staff1.equals(null))
            System.out.println("TEST FAILED");

        if (lecturer1.equals(null))
            System.out.println("TEST FAILED");
    }

    private static void printStaffDetails(StaffMember staffMember) {
        System.out.println(staffMember);
    }

}
