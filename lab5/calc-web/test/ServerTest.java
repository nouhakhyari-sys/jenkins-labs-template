import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Integration test: starts the web server on a free port and calls it over HTTP,
 * like a user would. Writes a JUnit XML report, like CalculatorTest.
 */
public class ServerTest {

    private static final List<String> testcases = new ArrayList<>();
    private static int failures = 0;

    private static String get(int port, String path) throws IOException {
        HttpURLConnection c = (HttpURLConnection) URI.create("http://localhost:" + port + path).toURL().openConnection();
        try (InputStream in = c.getResponseCode() < 400 ? c.getInputStream() : c.getErrorStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
    }

    private static void check(String name, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("PASS  " + name);
            testcases.add("  <testcase classname=\"ServerTest\" name=\"" + name + "\" time=\"0\"/>");
        } else {
            String message = "expected '" + expected + "' but was '" + actual + "'";
            System.out.println("FAIL  " + name + ": " + message);
            testcases.add("  <testcase classname=\"ServerTest\" name=\"" + name + "\" time=\"0\">\n"
                    + "    <failure message=\"" + message.replace("'", "&apos;") + "\">" + message + "</failure>\n"
                    + "  </testcase>");
            failures++;
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new CalculatorServer().start(0);   // 0 = any free port
        int port = server.getLocalPort();

        check("home_page_answers", "Calculator dev is running", get(port, "/"));
        check("http_add_2_and_3", "5", get(port, "/add/2/3"));
        check("unknown_page_gives_404", "Not found. Try /add/2/3", get(port, "/hello"));
        server.close();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<testsuite name=\"ServerTest\" tests=\"" + testcases.size()
                + "\" failures=\"" + failures + "\" errors=\"0\" skipped=\"0\" time=\"0\">\n"
                + String.join("\n", testcases) + "\n"
                + "</testsuite>\n";
        Path report = Path.of("build", "test-results", "TEST-ServerTest.xml");
        Files.createDirectories(report.getParent());
        Files.writeString(report, xml);

        System.out.println(testcases.size() + " tests, " + failures + " failure(s). Report: " + report);
    }
}
