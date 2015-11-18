# DistributedGrails
Work in Progress - this is not ready for use yet. Seriously, don't try.

## Installation

### Install Docker and Docker Compose
Follow the [installation instructions](https://docs.docker.com/compose/install/).

### Configuration
After installing Docker make sure you add your user to the 'docker' group.

`sudo usermod -aG docker {username}`

Logout and log back in for this change to take effect.


### Verification
Verify that Docker is installed and operational:

`docker run hello-world`

Verify that Compose is installed and operational:

`docker-compose version`

## Run
Build the custom Docker image we'll use for our Grails nodes. This command may take
some time depending on your bandwidth - behind the scenes it's downloading a Java8
VM image and installing Grails onto it. You only need to run this once - the resulting
custom image will be cached on your machine.

`docker build -t "mplummer/grails" -f Dockerfile.grails .`

Next we need to package the application as a WAR file:

`grails war`

Using Docker Compose we can launch a cluster of Grails nodes fronted by
a round-robin proxy. It may take a few minutes the first time you run
this command as it downloads the VM image for the Proxy.

`docker-compose scale node=3 proxy=1 up --force-recreate -d`

Provided the images downloaded and built cleanly we can now shut down these docker
images running in the background.

`docker-compose stop`

## Cleanup
`docker-compose stop`

To remove all containers and images from the filesystem you can run the following (assuming you're on a UNIX system).
This will remove ALL items, not just those from this project, so use carefully if you use Docker
for other things.
```
#!/bin/bash
# Delete all containers
docker rm $(docker ps -a -q)
# Delete all images
docker rmi $(docker images -q)
```
