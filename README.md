# Unit Testing Workshop

Workshop on unit testing in Java using JUnit 5 and Mockito.

## Workshop Structure

### Hour 1: Theory and Fundamentals
- Unit testing concepts and benefits
- Test anatomy (Arrange, Act, Assert)
- JUnit 5 essentials
- Mocking with Mockito
- Test-Driven Development introduction

### Hours 2-4: Hands-on Lab Exercises

## Problems Overview

### Problem 1: Basic Calculator
**Focus**: JUnit basics, assertions, exception testing
- Simple arithmetic operations
- Edge cases and boundary conditions
- Exception handling

### Problem 2: String Utilities
**Focus**: Parameterized tests, multiple test cases
- String manipulation methods
- Parameterized tests with @ValueSource and @CsvSource
- Handling null and empty inputs

### Problem 3: User Service with Mocking
**Focus**: Mocking dependencies, verify interactions
- Service class with repository and email dependencies
- @Mock and @InjectMocks annotations
- Stubbing and verification

### Problem 4: TDD Shopping Cart
**Focus**: Test-Driven Development cycle
- Build shopping cart from scratch using TDD
- Red-Green-Refactor methodology
- Incremental feature development

### Problem 5: Legacy Code Testing
**Focus**: Testing strategies for difficult code
- Characterization tests
- Identifying testing seams
- Dependency breaking techniques

## Getting Started

### Prerequisites
- Java 17+
- Maven or Gradle
- IDE of choice (IntelliJ IDEA, Eclipse, VS Code)

### Setup Instructions

1. **Fork this repository**
   ```bash
   # Click "Fork" on GitHub or clone directly
   git clone https://github.com/your-username/unit-testing-workshop.git
   cd unit-testing-workshop
   ```

2. **Build the project**
   ```bash
   # Using Maven
   mvn clean compile
   
   # Using Gradle
   ./gradlew build
   ```

3. **Run existing tests**
   ```bash
   # Using Maven
   mvn test
   
   # Using Gradle
   ./gradlew test
   ```

4. **Generate test coverage report**
   ```bash
   # Using Maven
   mvn jacoco:report
   # View report at target/site/jacoco/index.html
   
   # Using Gradle
   ./gradlew jacocoTestReport
   # View report at build/reports/jacoco/test/html/index.html
   ```

## Working Through Problems

1. Navigate to each problem directory
2. Read the README.md for specific instructions
3. Implement the tests following the TODO comments
4. Run tests frequently to get immediate feedback

## Additional Resources

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Documentation](https://assertj.github.io/doc/)

## Troubleshooting

### Common Issues
- **Tests not running**: Check JUnit 5 is properly configured
- **Mocks not working**: Ensure @ExtendWith(MockitoExtension.class) is present
- **Build failures**: Verify Java 17+ is being used
 