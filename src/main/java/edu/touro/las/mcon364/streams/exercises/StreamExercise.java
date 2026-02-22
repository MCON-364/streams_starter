package edu.touro.las.mcon364.streams.exercises;

import java.util.*;
import java.util.stream.*;

/**
 * In-Class Exercise: Working with Streams
 * 
 * Time: ~40 minutes
 * 
 * This exercise focuses on applying stream operations to analyze a gradebook
 * represented as a Map<String, List<Integer>> where:
 * - Key: Student name
 * - Value: List of grades (0-100)
 * 
 * Complete all methods marked with TODO.
 * Use stream operations - no explicit loops allowed!
 * 
 * See EXERCISES_README.md for detailed instructions.
 */
public class StreamExercise {
    
    // The gradebook: student name -> list of grades
    private final Map<String, List<Integer>> gradebook;
    
    /**
     * Constructor initializes the gradebook with sample data.
     */
    public StreamExercise() {
        gradebook = new LinkedHashMap<>();
        gradebook.put("Alice", List.of(95, 87, 92, 88, 91));
        gradebook.put("Bob", List.of(78, 82, 75, 80, 79));
        gradebook.put("Carol", List.of(92, 95, 98, 94, 100));
        gradebook.put("David", List.of(65, 70, 68, 72, 66));
        gradebook.put("Eva", List.of(88, 85, 90, 87, 89));
        gradebook.put("Frank", List.of(55, 60, 58, 62, 52));
        gradebook.put("Grace", List.of(100, 98, 95, 97, 99));
        gradebook.put("Henry", List.of(72, 75, 70, 78, 74));
    }
    
    // =========================================================================
    // PART 1: Basic Queries (10 minutes)
    // =========================================================================
    
    /**
     * Task 1.1: Return a sorted list of all student names.
     * 
     * Expected output: [Alice, Bob, Carol, David, Eva, Frank, Grace, Henry]
     */
    public List<String> getAllStudentNames() {
        return gradebook.keySet().stream()
                .sorted()
                .toList();
    }
    
    /**
     * Task 1.2: Count the total number of students.
     * 
     * Expected output: 8
     */
    public long countStudents() {
        return gradebook.keySet().stream()
                .count();
    }
    
    /**
     * Task 1.3: Get grades for a specific student.
     * Return empty list if student not found.
     * 
     * Example: getStudentGrades("Alice") -> [95, 87, 92, 88, 91]
     * Example: getStudentGrades("Unknown") -> []
     *
     * What is the time complexity of this method?
     * What would be a more efficient way to implement this if we cared about performance?
     */
    public List<Integer> getStudentGrades(String studentName) {
        return Optional.ofNullable(gradebook.get(studentName))
                .orElse(List.of());
    }
    
    // =========================================================================
    // PART 2: Grade Analysis
    // =========================================================================
    
    /**
     * Task 2.1: Calculate the average grade for a specific student.
     * Return 0.0 if student not found.
     * 
     * Example: calculateAverage("Alice") -> 90.6
     * Example: calculateAverage("Unknown") -> 0.0
     */
    public double calculateAverage(String studentName) {
        return Optional.ofNullable(gradebook.get(studentName))
                .orElse(List.of())
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Task 2.2: Flatten all grades into a single sorted list.
     * 
     * Expected: A sorted list of all grades from all students
     */
    public List<Integer> getAllGradesFlattened() {
        return gradebook.values().stream()
                .flatMap(List::stream)
                .sorted()
                .toList();
    }
    
    /**
     * Task 2.3: Find the highest grade across all students.
     * 
     * Expected output: 100 (Grace has perfect scores)
     */
    public int findHighestGrade() {
        return gradebook.values().stream()
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }
    
    /**
     * Task 2.4: Find the lowest grade across all students.
     * 
     * Expected output: 52 (Frank's lowest)
     */
    public int findLowestGrade() {
        return gradebook.values().stream()
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .min()
                .orElse(0);
    }
    
    /**
     * Task 2.5: Count total number of grades across all students.
     * 
     * Expected output: 40 (8 students × 5 grades each)
     */
    public long getTotalGradeCount() {
        return gradebook.values().stream()
                .flatMap(List::stream)
                .count();
    }
    
    // =========================================================================
    // PART 3: Filtering and Grouping (15 minutes)
    // =========================================================================
    
    /**
     * Task 3.1: Get names of students whose average is >= threshold.
     * 
     * Example: getPassingStudents(80) -> [Alice, Carol, Eva, Grace]
     */
    public List<String> getPassingStudents(double threshold) {
        return gradebook.entrySet().stream()
                .filter(entry -> calculateAverage(entry.getKey()) >= threshold)
                .map(Map.Entry::getKey)
                .toList();
    }
    
    /**
     * Task 3.2: Get names of students whose average is < threshold.
     * 
     * Example: getFailingStudents(70) -> [Frank]
     */
    public List<String> getFailingStudents(double threshold) {
        return gradebook.entrySet().stream()
                .filter(entry -> calculateAverage(entry.getKey()) < threshold)
                .map(Map.Entry::getKey)
                .toList();
    }
    
    /**
     * Task 3.3: Group students by letter grade based on their average.
     * 
     * Grading scale:
     * - "A": 90+
     * - "B": 80-89
     * - "C": 70-79
     * - "D": 60-69
     * - "F": below 60
     * 
     * Expected output structure:
     * {
     *   "A" -> [Alice, Carol, Grace],
     *   "B" -> [Eva],
     *   "C" -> [Bob, Henry],
     *   "D" -> [David],
     *   "F" -> [Frank]
     * }
     */
    public Map<String, List<String>> groupByPerformance() {
        return gradebook.entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> getLetterGrade(calculateAverage(entry.getKey())),
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
    }
    
    /**
     * Task 3.4: Create a map of student name to their average grade.
     * 
     * Expected: {Alice=90.6, Bob=78.8, Carol=95.8, ...}
     */
    public Map<String, Double> getStudentAverages() {
        return gradebook.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> calculateAverage(entry.getKey())
                ));
    }
    
    /**
     * Task 3.5: Find the name of the student with the highest average.
     * 
     * Expected output: "Grace" (average 97.8)
     */
    public String findTopPerformer() {
        return gradebook.entrySet().stream()
                .max(Comparator.comparingDouble(entry -> calculateAverage(entry.getKey())))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    // =========================================================================
    // BONUS CHALLENGES (if time permits)
    // =========================================================================
    
    /**
     * Bonus 1: Find all students who have at least one perfect score (100).
     * 
     * Expected: [Carol, Grace]
     */
    public List<String> getStudentsWithPerfectScore() {
        return gradebook.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(grade -> grade == 100))
                .map(Map.Entry::getKey)
                .toList();
    }
    
    /**
     * Bonus 2: Calculate the class average (average of ALL grades).
     * 
     * Expected: approximately 80.625
     */
    public double calculateClassAverage() {
        return gradebook.values().stream()
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Bonus 3: Find the student with the most consistent grades
     * (lowest standard deviation).
     * 
     * Hint: Standard deviation = sqrt(sum((x - mean)^2) / n)
     */
    public String findMostConsistentStudent() {
        return gradebook.entrySet().stream()
                .min(Comparator.comparingDouble(entry -> {
                    List<Integer> grades = entry.getValue();
                    double mean = grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                    double variance = grades.stream()
                            .mapToDouble(grade -> Math.pow(grade - mean, 2))
                            .average()
                            .orElse(0.0);
                    return Math.sqrt(variance);
                }))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    // =========================================================================
    // HELPER METHOD - Letter grade classifier
    // =========================================================================
    
    /**
     * Helper method to convert numeric average to letter grade.
     * You may use this in your groupByPerformance() implementation.
     */
    private String getLetterGrade(double average) {
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }
    
    // =========================================================================
    // MAIN METHOD - Test your implementations
    // =========================================================================
    
    public static void main(String[] args) {
        StreamExercise exercise = new StreamExercise();
        
        System.out.println("=".repeat(60));
        System.out.println("STREAM EXERCISE - Testing Your Implementations");
        System.out.println("=".repeat(60));
        
        // Part 1: Basic Queries
        System.out.println("\n--- PART 1: Basic Queries ---");
        System.out.println("1.1 All student names: " + exercise.getAllStudentNames());
        // Expected: [Alice, Bob, Carol, David, Eva, Frank, Grace, Henry]
        
        System.out.println("1.2 Student count: " + exercise.countStudents());
        // Expected: 8
        
        System.out.println("1.3 Alice's grades: " + exercise.getStudentGrades("Alice"));
        // Expected: [95, 87, 92, 88, 91]
        
        System.out.println("1.3 Unknown's grades: " + exercise.getStudentGrades("Unknown"));
        // Expected: []
        
        // Part 2: Grade Analysis
        System.out.println("\n--- PART 2: Grade Analysis ---");
        System.out.println("2.1 Alice's average: " + exercise.calculateAverage("Alice"));
        // Expected: 90.6
        
        System.out.println("2.2 All grades flattened: " + exercise.getAllGradesFlattened());
        // Expected: Sorted list of all 40 grades
        
        System.out.println("2.3 Highest grade: " + exercise.findHighestGrade());
        // Expected: 100
        
        System.out.println("2.4 Lowest grade: " + exercise.findLowestGrade());
        // Expected: 52
        
        System.out.println("2.5 Total grade count: " + exercise.getTotalGradeCount());
        // Expected: 40
        
        // Part 3: Filtering and Grouping
        System.out.println("\n--- PART 3: Filtering and Grouping ---");
        System.out.println("3.1 Passing students (>=80): " + exercise.getPassingStudents(80));
        // Expected: [Alice, Carol, Eva, Grace]
        
        System.out.println("3.2 Failing students (<70): " + exercise.getFailingStudents(70));
        // Expected: [Frank]
        
        System.out.println("3.3 Grouped by performance: " + exercise.groupByPerformance());
        // Expected: {A=[Alice, Carol, Grace], B=[Eva], C=[Bob, Henry], D=[David], F=[Frank]}
        
        System.out.println("3.4 Student averages: " + exercise.getStudentAverages());
        // Expected: Map with each student's average
        
        System.out.println("3.5 Top performer: " + exercise.findTopPerformer());
        // Expected: Grace
        
        // Bonus Challenges
        System.out.println("\n--- BONUS CHALLENGES ---");
        System.out.println("Bonus 1 - Perfect scores: " + exercise.getStudentsWithPerfectScore());
        // Expected: [Carol, Grace]
        
        System.out.println("Bonus 2 - Class average: " + exercise.calculateClassAverage());
        // Expected: ~80.625
        
        System.out.println("Bonus 3 - Most consistent: " + exercise.findMostConsistentStudent());
        // Expected: Eva or Alice (low variance)
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Check your results against the expected values above!");
        System.out.println("=".repeat(60));
    }
}
