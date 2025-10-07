# TODO list of things to do next

## Budget
- ~~Model~~
- ~~Repository~~
- DTOs
  - ~~BudgetResponse~~
  - ~~UpdateBudgetRequest~~
  - ~~CreateBudgetRequest~~
- ~~Mapper~~
- Service
  - CRUD operations 
- Controller
  - CRUD operations 
- Add option to filter which categories are to be concidered on the budget

## User

- getAllUsers is not returning a ResponseEntity

## Rate Limiting
- Consider adding a global rate limiting apart from the current
  by ip rate limiting

## Global
- Change folder structure to Domain layered


### Improve code consistency
- use the same names for request body variable names e.g. notificationRequest, updatedUser, etc.
- Be consistent with type inference, don't use var then use explicit typing after if it's not necessary

### Add 429 (Too many requests) to all endpoints  OpenAPI Docs