# TODO list of things to do next

## Expenses
- ~~Model~~
- ~~Repository~~
- ~~Dto~~
- ~~Mapper~~
- ~~Service~~
- ~~Controller~~

## Notifications

- Controller
  - Patch
- FIX
  - Update should not accept userId changing
  - You can assign a non-existent userId to a notification

### User

- Add endpoint to get user's notifications
- getAllUsers is not returning a ResponseEntity

## Global

- **Add rate limiting**
- Configure JPA to print queries to console
- Change folder structure to Domain layered

### Validate that Services of entities that have reletions to other entities validate that the referenced entity exists
- Notifications (Notification - User)
- Expenses (Expense - User)

### Improve OpenAPI Docs
- Add descriptions for endpoint parameters
  - Notifications
  - Users
  - Categories
- Add descriptions for endpoint responses
  - Notifications
  - Users
  - Categories