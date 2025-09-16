# Legacy Code Testing Challenge

## The Problem
You've inherited a legacy `OrderProcessor` class that:
- Is tightly coupled to a database
- Uses static dependencies that can't be mocked
- Mixes business logic with data access
- Has complex methods doing too many things
- Is essentially untestable in its current state

## Your Mission
Learn how to test and refactor legacy code safely using these techniques:

### Phase 1: Characterization Tests
1. Write tests that document current behavior
2. Focus on what you CAN test without external dependencies
3. Identify what you CAN'T test and why

### Phase 2: Analysis
Identify the problems:
- Database coupling
- Static dependencies
- Mixed responsibilities
- Hard-coded values
- Complex methods

### Phase 3: Refactoring Strategy
Plan your approach:
- Extract and Override method
- Dependency injection
- Repository pattern
- Service layer separation
- Interface wrappers for static calls

## Bonus Challenge
If you finish early, try implementing one refactoring step:
1. Extract database operations to a repository interface
2. Make EmailSender dependency injectable
3. Break the large `processOrder` method into smaller methods
