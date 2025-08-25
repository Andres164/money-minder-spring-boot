# TODO list of things to do next

## Notifications

- Controller
  - Patch

### User

- Add endpoint to get user's notifications
- getAllUsers is not returning a ResponseEntity

## Global

- **Add rate limiting**
- Change folder structure to Domain layered


### Improve code consistency
- use the same names for request body variable names e.g notificationRequest, updatedUser, etc.
- Be consistent with type inference, dont use var then use explicit typing after if it's not necessary

### Add 429 (Too many requests) to all endpoints  OpenAPI Docs