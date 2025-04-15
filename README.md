# Sarah's notes

Here's what I did for the DAO and Service. I think this would be the same for the other classes, too. Of course, maybe it's overkill? I had some trouble figuring out where to put logs and errors. I know I said I wouldn't do them, but I can't help myself... If we didn't want to do them, then the distinction between the responsibilities is all we really need here. 

## WorkoutClassDAO (Data Access Object)

### Responsibilities
- Execute CRUD operations directly against the database.

### Error Handling
- Catch `SQLExceptions` that occur during database operations to prevent them from propagating to higher layers without context.
- (Maybe we want to rethrow them as more meaningful custom exceptions?)

### Logging
- Log technical details about database errors here (related to the database connectivity and query execution).
- These logs capture sufficient detail for troubleshooting database interactions (at least, that's the goal)

## WorkoutClassService (Service Layer)

### Responsibilities
- Handle business logic
- Validates input data before it’s passed down to the DAO.

### Error Handling
- Catch exceptions thrown by the DAO
- Handles them (possibly logging user-friendly messages or taking corrective actions)
- Rethrows errors necessary.
- Throws new exceptions based on failed validations.

### Logging
- Log operational errors or important business rule violations.
- Focus on the context of the error.
