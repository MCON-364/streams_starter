package edu.touro.las.mcon364.streams.exercises;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Unit tests for StreamExercise.
 * 
 * Run with: mvn test -Dtest=StreamExerciseTest
 */
class StreamExerciseTest {
    
    private StreamExercise exercise;
    
    @BeforeEach
    void setUp() {
        exercise = new StreamExercise();
    }
    
    // =========================================================================
    // PART 1: Basic Queries
    // =========================================================================
    
    @Test
    @DisplayName("1.1: getAllStudentNames returns sorted list")
    void testGetAllStudentNames() {
        List<String> names = exercise.getAllStudentNames();
        assertNotNull(names, "Should not return null");
        assertEquals(8, names.size(), "Should have 8 students");
        assertEquals(List.of("Alice", "Bob", "Carol", "David", "Eva", "Frank", "Grace", "Henry"),
                names, "Names should be sorted alphabetically");
    }
    
    @Test
    @DisplayName("1.2: countStudents returns correct count")
    void testCountStudents() {
        assertEquals(8, exercise.countStudents());
    }
    
    @Test
    @DisplayName("1.3: getStudentGrades returns correct grades")
    void testGetStudentGrades() {
        assertEquals(List.of(95, 87, 92, 88, 91), exercise.getStudentGrades("Alice"));
        assertEquals(List.of(100, 98, 95, 97, 99), exercise.getStudentGrades("Grace"));
    }
    
    @Test
    @DisplayName("1.3: getStudentGrades returns empty for unknown student")
    void testGetStudentGradesUnknown() {
        List<Integer> grades = exercise.getStudentGrades("Unknown");
        assertNotNull(grades, "Should not return null for unknown student");
        assertTrue(grades.isEmpty(), "Should return empty list for unknown student");
    }
    
    // =========================================================================
    // PART 2: Grade Analysis
    // =========================================================================
    
    @Test
    @DisplayName("2.1: calculateAverage returns correct average")
    void testCalculateAverage() {
        assertEquals(90.6, exercise.calculateAverage("Alice"), 0.01);
        assertEquals(97.8, exercise.calculateAverage("Grace"), 0.01);
        assertEquals(57.4, exercise.calculateAverage("Frank"), 0.01);
    }
    
    @Test
    @DisplayName("2.1: calculateAverage returns 0 for unknown student")
    void testCalculateAverageUnknown() {
        assertEquals(0.0, exercise.calculateAverage("Unknown"), 0.001);
    }
    
    @Test
    @DisplayName("2.2: getAllGradesFlattened returns sorted list")
    void testGetAllGradesFlattened() {
        List<Integer> grades = exercise.getAllGradesFlattened();
        assertNotNull(grades);
        assertEquals(40, grades.size(), "Should have 40 total grades");
        
        // Check sorted
        for (int i = 1; i < grades.size(); i++) {
            assertTrue(grades.get(i-1) <= grades.get(i), "List should be sorted");
        }
    }
    
    @Test
    @DisplayName("2.3: findHighestGrade returns 100")
    void testFindHighestGrade() {
        assertEquals(100, exercise.findHighestGrade());
    }
    
    @Test
    @DisplayName("2.4: findLowestGrade returns 52")
    void testFindLowestGrade() {
        assertEquals(52, exercise.findLowestGrade());
    }
    
    @Test
    @DisplayName("2.5: getTotalGradeCount returns 40")
    void testGetTotalGradeCount() {
        assertEquals(40, exercise.getTotalGradeCount());
    }
    
    // =========================================================================
    // PART 3: Filtering and Grouping
    // =========================================================================
    
    @Test
    @DisplayName("3.1: getPassingStudents returns correct students")
    void testGetPassingStudents() {
        List<String> passing = exercise.getPassingStudents(80);
        assertNotNull(passing);
        assertTrue(passing.contains("Alice"));
        assertTrue(passing.contains("Carol"));
        assertTrue(passing.contains("Eva"));
        assertTrue(passing.contains("Grace"));
        assertFalse(passing.contains("Frank"));
        assertFalse(passing.contains("David"));
    }
    
    @Test
    @DisplayName("3.2: getFailingStudents returns correct students")
    void testGetFailingStudents() {
        List<String> failing = exercise.getFailingStudents(70);
        assertNotNull(failing);
        assertEquals(2, failing.size());
        assertTrue(failing.contains("Frank"));
        assertTrue(failing.contains("David"));
    }
    
    @Test
    @DisplayName("3.3: groupByPerformance groups correctly")
    void testGroupByPerformance() {
        Map<String, List<String>> groups = exercise.groupByPerformance();
        assertNotNull(groups);
        
        assertTrue(groups.get("A").containsAll(List.of("Alice", "Carol", "Grace")));
        assertTrue(groups.get("B").contains("Eva"));
        assertTrue(groups.get("C").containsAll(List.of("Bob", "Henry")));
        assertTrue(groups.get("D").contains("David"));
        assertTrue(groups.get("F").contains("Frank"));
    }
    
    @Test
    @DisplayName("3.4: getStudentAverages returns correct map")
    void testGetStudentAverages() {
        Map<String, Double> averages = exercise.getStudentAverages();
        assertNotNull(averages);
        assertEquals(8, averages.size());
        assertEquals(90.6, averages.get("Alice"), 0.01);
        assertEquals(97.8, averages.get("Grace"), 0.01);
    }
    
    @Test
    @DisplayName("3.5: findTopPerformer returns Grace")
    void testFindTopPerformer() {
        assertEquals("Grace", exercise.findTopPerformer());
    }
    
    // =========================================================================
    // BONUS
    // =========================================================================
    
    @Test
    @DisplayName("Bonus 1: getStudentsWithPerfectScore")
    void testGetStudentsWithPerfectScore() {
        List<String> perfect = exercise.getStudentsWithPerfectScore();
        if (perfect != null) {
            assertTrue(perfect.contains("Carol"));
            assertTrue(perfect.contains("Grace"));
            assertEquals(2, perfect.size());
        }
    }
    
    @Test
    @DisplayName("Bonus 2: calculateClassAverage")
    void testCalculateClassAverage() {
        double avg = exercise.calculateClassAverage();
        if (avg > 0) {
            assertEquals(81.275, avg, 0.01);
        }
    }
}
