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
First we need to package the application as a WAR file:

`grails war`

Using Docker Compose we can launch a cluster of Tomcat nodes fronted by
a round-robin proxy. It may take a few minutes the first time you run
this command as it downloads the VM images for Tomcat and the Proxy.
Each Tomcat node will deploy the WAR file we built in the previous command.

`docker-compose scale node=3 proxy=1`

Unfortunately the links capability in docker-compose doesn't work very well when defined
as unscaled -> scaled containers (they work fine when defined from scaled -> unscaled).
Scaling a container up or down doesn't add/remove links from other services so we have to
force Docker to rebuild and restart all the containers so that links are correctly established.
Seems like a pretty major oversight, especially for a common case like load-balancing, but the
workaround is pretty trivial:

`docker-compose up --force-recreate -d`

To get the status of the load balancer and the backing cluster you can access HAProxy's
[stats page](http://localhost:1936) with username/password `stats/stats`.

Once you're done you can now shut down the docker containers running in the background.

`docker-compose stop`

## Cleanup
To stop all the containers started by docker-compose execute this from the project directory:

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
