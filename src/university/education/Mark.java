package university.education;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double attestation1;
    private double attestation2;
    private double finalExam;

    public Mark(double attestation1, double attestation2, double finalExam) {
        validateRange("Attestation 1", attestation1, 30);
        validateRange("Attestation 2", attestation2, 30);
        validateRange("Final exam", finalExam, 40);
        this.attestation1 = attestation1;
        this.attestation2 = attestation2;
        this.finalExam = finalExam;
    }

    public double getFinalGrade() {
        return attestation1 + attestation2 + finalExam;
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

    public boolean isPassed() {
        return getFinalGrade() >= 50 && finalExam >= 20;
    }

    public String getLetterGrade() {
        double grade = getFinalGrade();
        if (grade < 50 || finalExam < 9.5) {
            return "Fail";
        }
        if (finalExam < 20) {
            return "FX";
        }
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
        return "D";
    }

    private void validateRange(String label, double value, double max) {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException(label + " must be between 0 and " + max);
        }
    }

    @Override
    public String toString() {
        return String.format("A1=%.1f, A2=%.1f, Final=%.1f, Grade=%.1f, Letter=%s",
                attestation1, attestation2, finalExam, getFinalGrade(), getLetterGrade());
    }
}
