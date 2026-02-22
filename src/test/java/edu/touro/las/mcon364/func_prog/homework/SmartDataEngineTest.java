package edu.touro.las.mcon364.func_prog.homework;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class SmartDataEngineTest {

    // ============================================================
    // PART 1 — PIPELINE TEST
    // ============================================================

    @Test
    void testPipelineFiltersAndMapsCorrectly() {

        AtomicInteger counter = new AtomicInteger(0);

        SmartDataEngine.pipeline(
                List.of(1, 2, 3, 4, 5),
                n -> n % 2 == 0,     // keep even
                n -> n * 10,        // multiply
                n -> counter.addAndGet(n)
        );

        // even numbers: 2,4 → 20 + 40 = 60
        assertEquals(60, counter.get());
    }

    // ============================================================
    // PART 2 — OPTIONAL TESTS
    // ============================================================

    @Test
    void testSafeDivideSuccess() {
        Optional<Double> result = SmartDataEngine.safeDivide(10, 2);
        assertTrue(result.isPresent());
        assertEquals(5.0, result.get());
    }

    @Test
    void testSafeDivideByZero() {
        Optional<Double> result = SmartDataEngine.safeDivide(10, 0);
        assertTrue(result.isEmpty());
    }

    @Test
    void testProcessDivisionSuccess() {
        double result = SmartDataEngine.processDivision(10, 2);
        // 10/2 = 5 → *10 = 50
        assertEquals(50.0, result);
    }

    @Test
    void testProcessDivisionFailure() {
        double result = SmartDataEngine.processDivision(10, 0);
        assertEquals(-1.0, result);
    }

    // ============================================================
    // PART 3 — SWITCH PATTERN MATCHING
    // ============================================================

    @Test
    void testTransformInteger() {
        Object result = SmartDataEngine.transformObject(4);
        assertEquals(16, result);
    }

    @Test
    void testTransformString() {
        Object result = SmartDataEngine.transformObject("hello");
        assertEquals("HELLO", result);
    }

    @Test
    void testTransformDouble() {
        Object result = SmartDataEngine.transformObject(3.7);
        assertEquals((long)4, result);
    }

    @Test
    void testTransformUnsupported() {
        Object result = SmartDataEngine.transformObject(true);
        assertEquals("Unsupported", result);
    }

    // ============================================================
    // PART 4 — FUNCTION CHAINING
    // ============================================================

    @Test
    void testBuildStringLengthPipeline() {
        Function<String, Integer> pipeline =
                SmartDataEngine.buildStringLengthPipeline();

        int result = pipeline.apply("  HELLO  ");
        assertEquals(5, result);
    }

    // ============================================================
    // PART 5 — SCORE PROCESSOR
    // ============================================================

    @Test
    void testRunScoreProcessorDoesNotThrow() {
        assertDoesNotThrow(() -> SmartDataEngine.runScoreProcessor());
    }
}

