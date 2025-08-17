# TODO list of things to do next

## Categories
- ~~Create CategoryRequest for creating a new category~~
- ~~Add field validations on create and update~~

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

### Improve OpenAPI Docs
- Add descriptions for endpoint parameters
  - Notifications
  - Users
  - Categories
- Add descriptions for endpoint responses
  - Notifications
  - Users
  - Categories