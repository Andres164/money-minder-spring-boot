# Instrictions for deploying the app to cloud run

## change to main branch

## Build project to target

run the following commands to clean the project and then build the .jar

```bash
mvn clean

mvn package
```

## Deploy to the cloud

- First build the docker image
`docker build -t money-minder-spring-boot .`

- Now make a docker container with the image to make sure the changes were saved successfuly

- then tag it like this
`docker tag SOURCE-IMAGE LOCATION-docker.pkg.dev/PROJECT-ID/REPOSITORY/IMAGE:TAG`

- And push
`docker push LOCATION-docker.pkg.dev/PROJECT-ID/REPOSITORY/IMAGE:TAG`

### updating cloud run

To deploy a new revision of an existing service:

- In the Google Cloud console, go to the Cloud Run page
- Locate the service you want to update in the services list, and click to open the details of that service.
- Click Edit and deploy new revision to display the revision deployment form.
- Select the updated docker image URL you want to deploy.
    - Configure the container as needed.
    - Remember to set autoscaling from 0 to 1
