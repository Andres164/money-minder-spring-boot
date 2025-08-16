# TODO list of things to do next

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

- Pass down all the mapping logic from the controllers 
to a service layer.
  - NotificationController
  - CategoryController
- Change folder structure to Domain layered
- Configure JPA to print queries to console

### Improve OpenAPI Docs
- Add descriptions for endpoint parameters
  - Notifications
  - Users
  - Categories
- Add descriptions for endpoint responses
  - Notifications
  - Users
  - Categories