
import java.util.Scanner;

public class StudentGradeCalculator {

    public static int[] getMarks(int numSubjects) {
        Scanner scanner = new Scanner(System.in);
        int[] marks = new int[numSubjects];
        
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + ": ");
            marks[i] = scanner.nextInt();
        }
        
        return marks;
    }

    public static int calculateTotalMarks(int[] marks) {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }

    public static double calculateAveragePercentage(int totalMarks, int numSubjects) {
        return (double) totalMarks / numSubjects;
    }

    public static char calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public static void displayResults(int totalMarks, double averagePercentage, char grade) {
        System.out.println("\nTotal Marks: " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        
        int[] marks = getMarks(numSubjects);
        
        int totalMarks = calculateTotalMarks(marks);
        
        double averagePercentage = calculateAveragePercentage(totalMarks, numSubjects);
        
        char grade = calculateGrade(averagePercentage);
        
        displayResults(totalMarks, averagePercentage, grade);
        
        scanner.close();
    }
}
