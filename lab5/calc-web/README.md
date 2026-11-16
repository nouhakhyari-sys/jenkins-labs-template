# Calculator web app (Lab 5)

| File | Role |
|---|---|
| `src/Calculator.java` | The calculator (`-DsimulateBug=true` adds a bug) |
| `src/CalculatorServer.java` | Tiny web server on port 8080: `/` shows the version, `/add/2/3` answers `5` |
| `test/CalculatorTest.java` | Unit tests, JUnit XML report in `build/test-results` |
| `test/ServerTest.java` | Integration tests over HTTP, JUnit XML report in `build/test-results` |
| `Dockerfile` | Image of the app (needs `build/calculator-web.jar`) |

Build and run by hand:

```bash
javac -d build/classes src/*.java test/*.java
java -cp build/classes CalculatorTest
java -cp build/classes ServerTest
jar cfe build/calculator-web.jar CalculatorServer -C build/classes .
docker build --build-arg APP_VERSION=1.0.0 -t calc-web:1.0.0 .
docker run -d --name calc -p 8081:8080 calc-web:1.0.0     # then open http://localhost:8081
```
