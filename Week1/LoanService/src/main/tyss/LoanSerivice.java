package tyss;

public class LoanSerivice {

    public boolean isEligible(int age, double salary) {
        return age >= 21 && age <= 60 && salary >= 25000;
    }

    public double calculateEMI(double loan, int tenureYears) {

        if (loan <= 0 || tenureYears <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        return loan / (tenureYears * 12);
    }

    public String getLoanCategory(int creditScore) {

        if (creditScore >= 750)
            return "Premium";

        if (creditScore >= 600)
            return "Standard";

        return "High Risk";
    }
}
