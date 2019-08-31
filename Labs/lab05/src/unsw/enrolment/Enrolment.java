package unsw.enrolment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Enrolment {

    private CourseOffering offering;
    private Student student;
    private List<Session> sessions;

    private CalculatedMark marks;

    public Enrolment(CourseOffering offering, Student student,
            Session... sessions) {
        this.offering = offering;
        this.student = student;
        student.addEnrolment(this);
        offering.addEnrolment(this);
        this.sessions = new ArrayList<>();
        for (Session session : sessions) {
            this.sessions.add(session);
        }
        this.marks = new CalculatedMark("course", new SumCalculator());
    }

    public Course getCourse() {
        return offering.getCourse();
    }

    public String getTerm() {
        return offering.getTerm();
    }

    public boolean hasPassed() {
        return getGrade() != null && getGrade().isPassing();
    }

    private Grade getGrade() {
        return new Grade(marks.getMark());
    }

    /**
     * Manually assign a mark for a given assessment. This method assumes that
     * already manual marks can be updated but that calculated marks can't be
     * converted into manual ones.
     *
     * @param assessment
     * @param mark
     * @param max
     */
    public void assignMark(String assessment, int mark, int max) {
        if (!marks.updateMark(assessment, mark, max)) {
            ManualMark newMark = new ManualMark(assessment, mark, max);
            logChanges(newMark);
            marks.addSubmark(newMark);
        }
    }

    private void logChanges(ManualMark mark) {
        mark.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                String filename = offering.getCourse().getCourseCode() + "-" +
                        offering.getTerm() + "-" + student.getZID();
                try {
                    File file = new File(filename);
                    PrintStream out = new PrintStream(new FileOutputStream(file, true));
                    out.println(LocalDateTime.now() + " -- " +
                            mark.getAssessment() + " = " + mark.getMark() +
                            "/" + mark.getMaximum());
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Add a calculated mark based on the average of the given submarks. This
     * method assumes a calculated mark for that assessment doesn't already
     * exist.
     *
     * @param assessment
     * @param submarks
     */
    public void addAverageMark(String assessment, String... submarks) {
        addCalculatedMark(assessment, new AverageCalculator(), submarks);
    }

    /**
     * Add a calculated mark based on the sum of the given submarks. This
     * method assumes a calculated mark for that assessment doesn't already
     * exist.
     *
     * @param assessment
     * @param submarks
     */
    public void addSumMark(String assessment, String... submarks) {
        addCalculatedMark(assessment, new SumCalculator(), submarks);
    }

    private void addCalculatedMark(String assessment, MarkCalculator calculator,
            String... submarks) {
        CalculatedMark newMark = new CalculatedMark(assessment, calculator);
        for (String submark : submarks) {
            Mark mark = marks.removeMark(submark);
            if (mark != null)
                newMark.addSubmark(mark);
        }
        marks.addSubmark(newMark);
    }

    // Whole course marks can no longer be assigned this way.
    // public void assignMark(int mark) {
    // grade = new Grade(mark);
    // }

}
