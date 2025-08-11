# TODO list of things to do next

## Notifications

- ~~Entity~~
- ~~Repository~~
- Controller
  - ~~Get~~
  - ~~Get All~~
  - ~~Create~~
  - Put
  - ~~Delete~~
  - Patch
- Dtos
  - ~~NotificationRequest~~
  - UpdateRequest
- Mapper
  - ~~Request to Entity~~
  - update notification using update request
- Test CRUD
- FIX
  - Update should not accept userId changing
  - You can assign a non-existent userId to a notification

### User

- Add endpoint to get user's notifications
- getAllUsers is not returning a ResponseEntity

## Global

- Pass down all the mapping logic from the controllers 
to a service layer.
- Add documentation with Swagger
- Change folder structure to Domain layered