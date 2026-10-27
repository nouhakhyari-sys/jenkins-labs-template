import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for Calculator. No library needed: the results are written
 * in the JUnit XML format, so that the Jenkins JUnit plugin can read them.
 */
public class CalculatorTest {

    private static final List<String> testcases = new ArrayList<>();
    private static int failures = 0;

    private static void check(String name, int expected, int actual) {
        if (expected == actual) {
            System.out.println("PASS  " + name);
            testcases.add("  <testcase classname=\"CalculatorTest\" name=\"" + name + "\" time=\"0\"/>");
        } else {
            String message = "expected " + expected + " but was " + actual;
            System.out.println("FAIL  " + name + ": " + message);
            testcases.add("  <testcase classname=\"CalculatorTest\" name=\"" + name + "\" time=\"0\">\n"
                    + "    <failure message=\"" + message + "\">" + message + "</failure>\n"
                    + "  </testcase>");
            failures++;
        }
    }

    public static void main(String[] args) throws IOException {
        Calculator calc = new Calculator();

        check("add_2_and_3", 5, calc.add(2, 3));
        check("add_a_negative_number", -1, calc.add(2, -3));
        check("multiply_4_and_5", 20, calc.multiply(4, 5));
        check("multiply_by_zero", 0, calc.multiply(7, 0));

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<testsuite name=\"CalculatorTest\" tests=\"" + testcases.size()
                + "\" failures=\"" + failures + "\" errors=\"0\" skipped=\"0\" time=\"0\">\n"
                + String.join("\n", testcases) + "\n"
                + "</testsuite>\n";
        Path report = Path.of("build", "test-results", "TEST-CalculatorTest.xml");
        Files.createDirectories(report.getParent());
        Files.writeString(report, xml);

        System.out.println(testcases.size() + " tests, " + failures + " failure(s). Report: " + report);
    }
}
