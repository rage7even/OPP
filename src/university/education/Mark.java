package university.education;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double attestation1;
    private double attestation2;
    private double finalExam;

    public Mark(double attestation1, double attestation2, double finalExam) {
        this.attestation1 = attestation1;
        this.attestation2 = attestation2;
        this.finalExam = finalExam;
    }

    public double getFinalGrade() {
        return attestation1 * 0.3 + attestation2 * 0.3 + finalExam * 0.4;
    }

    public double getAttestation1() {
        return attestation1;
    }

    public double getAttestation2() {
        return attestation2;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public String getLetterGrade() {
        double grade = getFinalGrade();
        if (grade >= 95) {
            return "A";
        }
        if (grade >= 90) {
            return "A-";
        }
        if (grade >= 85) {
            return "B+";
        }
        if (grade >= 80) {
            return "B";
        }
        if (grade >= 75) {
            return "B-";
        }
        if (grade >= 70) {
            return "C+";
        }
        if (grade >= 65) {
            return "C";
        }
        if (grade >= 60) {
            return "C-";
        }
        if (grade >= 55) {
            return "D+";
        }
        if (grade >= 50) {
            return "D";
        }
        return "Fail";
    }

    @Override
    public String toString() {
        return String.format("A1=%.1f, A2=%.1f, Final=%.1f, Grade=%.1f, Letter=%s",
                attestation1, attestation2, finalExam, getFinalGrade(), getLetterGrade());
    }
}
