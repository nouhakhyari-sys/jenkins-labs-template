# Java calculator – example project for Jenkins

A tiny Java project that Jenkins builds, tests and packages. No Maven, no library: only the JDK that Jenkins already uses.

```
lab2/java-app/
├── src/Calculator.java        the application
├── test/CalculatorTest.java   4 tests, results written for the JUnit plugin
├── Jenkinsfile                the pipeline (Jenkins in Docker, Linux, macOS)
└── Jenkinsfile-windows        same pipeline with bat (Jenkins installed on Windows)
```

| Stage | What it does | Plugin |
|---|---|---|
| Build | `javac` compiles the code | |
| Test | runs the tests, then shows the results | JUnit |
| Package | builds `calculator.jar`, runs it, keeps it | Artifacts |
| (all) | time on each log line, files deleted at the end | Timestamper, Workspace Cleanup |

Lab guide: https://nouhakhyari-sys.github.io/jenkins-lab/lab3.html
