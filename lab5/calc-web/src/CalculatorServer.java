import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * The calculator as a tiny web application (no library needed).
 *   GET /          -> "Calculator <version> is running"
 *   GET /add/2/3   -> 5
 * The version comes from the APP_VERSION environment variable,
 * set by the Docker image (see the Dockerfile).
 */
public class CalculatorServer {

    private final Calculator calc = new Calculator();
    private final String version = System.getenv().getOrDefault("APP_VERSION", "dev");

    public ServerSocket start(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        Thread t = new Thread(() -> {
            while (!server.isClosed()) {
                try (Socket client = server.accept()) {
                    handle(client);
                } catch (IOException e) {
                    // connection closed: wait for the next one
                }
            }
        });
        t.setDaemon(true);
        t.start();
        return server;
    }

    private void handle(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
        String request = in.readLine();                 // e.g. "GET /add/2/3 HTTP/1.1"
        String path = request == null ? "/" : request.split(" ")[1];
        int status = 200;
        String body;
        if (path.equals("/")) {
            body = "Calculator " + version + " is running";
        } else if (path.startsWith("/add/")) {
            String[] p = path.split("/");               // ["", "add", "2", "3"]
            try {
                body = String.valueOf(calc.add(Integer.parseInt(p[2]), Integer.parseInt(p[3])));
            } catch (RuntimeException e) {
                status = 400;
                body = "Usage: /add/2/3";
            }
        } else {
            status = 404;
            body = "Not found. Try /add/2/3";
        }
        byte[] bytes = (body + "\n").getBytes(StandardCharsets.UTF_8);
        OutputStream out = client.getOutputStream();
        out.write(("HTTP/1.1 " + status + (status == 200 ? " OK" : " Error") + "\r\n"
                + "Content-Type: text/plain; charset=utf-8\r\n"
                + "Content-Length: " + bytes.length + "\r\n"
                + "Connection: close\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(bytes);
        out.flush();
    }

    public static void main(String[] args) throws Exception {
        new CalculatorServer().start(8080);
        System.out.println("Calculator web app listening on port 8080");
        Thread.currentThread().join();                  // keep running
    }
}
