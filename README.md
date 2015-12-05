# DistributedGrails
Example of clustering Grails using Docker Compose and sharing data within the cluster using Hazelcast.

Fair warning: This is still a work in progress. The base functionality works but the UI is still being developed and there is no in-code documentation.

## Installation
This application makes use of Docker and Docker Compose to execute the Grails app on a dynamically-scalable set of 'virtual machines'. If you really don't want to install this software you don't have to - the Grails app can be run independently and all the features will work the same except that it will all be isolated to a single instance and all URL's referenced in this README won't have the 'DistributedGrails' context path.

### Install Docker and Docker Compose
Follow the [installation instructions](https://docs.docker.com/compose/install/).

After installing Docker make sure you add your user to the 'docker' group.

`sudo usermod -aG docker {username}`

Logout and log back in for this change to take effect.

### Verification
**Important Note:** The application expects to run on port 8080 - be sure this port is free prior to launching.

Verify that Docker is installed and operational:

`docker run hello-world`

Verify that Compose is installed and operational:

`docker-compose version`

## Launch
First we need to package the application as a WAR file:

`grails war`

Using Docker Compose we can launch a cluster of Tomcat nodes fronted by a round-robin proxy. It may take several minutes the first time you run this command as it downloads the VM images for Tomcat and the Proxy. Each Tomcat node will deploy the WAR file we built in the previous command.

`docker-compose up -d`

We can scale the cluster to any number of nodes we want - this will spin up additional Tomcat containers each of which will deploy the Grails app automatically.

`docker-compose scale node=4`

Unfortunately the links capability in docker-compose doesn't work very well when defined as unscaled -> scaled containers (they work fine when defined from scaled -> unscaled). Scaling a container up or down doesn't add/remove links from other services so we have to force Docker to rebuild and restart all the containers so that links are correctly established. Annoying for sure, but the workaround is pretty trivial:

`docker-compose stop`
`docker-compose up --force-recreate -d`

To get the status of the load balancer and the backing cluster you can access HAProxy's [stats page](http://localhost:1936) with username/password `stats/stats`. This is a good way to make sure that the proxy is connecting to the entire cluster.

## Use it!
Hit the [landing page](http://localhost:8080/DistributedGrails/) to access the cluster info page. This contacts one of the cluster members and gets the status of all the caches and Hazelcast distributed objects registered in the cluster. Reloading this page will reflect a different hostname being the source of the data - this is due to HAProxy round-robining you around the Docker cluster.

Grails services are exposed to supply [cache data](http://localhost:8080/DistributedGrails/cache) and [city data](http://localhost:8080/DistributedGrails/city).
There is also an example of a MapReduce-style [distributed job](http://localhost:8080/DistributedGrails/city/population) to show off another of Hazelcast's great features, especially useful when you're working with truly massive datasets. This endpoint runs a 'simple' summation of the population of all Cities by iterating the entire dataset on a single cluster node and also executes a distributed job to sum populations stored on each cluster node then combine the results. Due to the small size of the dataset and the fact that the Docker containers are all sharing the same processor the distributed job will likely be slower in this example due to the overhead incurred by splitting up and recombining the job.

Once you're done you can now shut down the docker containers running in the background.

`docker-compose stop`

## Cleanup
To stop all the containers started by docker-compose execute this from the project directory:

`docker-compose stop`

To remove all containers and images from the filesystem you can run the following (assuming you're on a UNIX system). This will remove ALL items, not just those from this project, so use carefully if you use Docker for other things.
```
#!/bin/bash
# Delete all containers and their data volumes (if any)
docker rm -v $(docker ps -a -q)
# Delete all images (only do this if you're finished, going back from this requires re-downloading the images)
docker rmi $(docker images -q)
```

## Tools
Docker
Docker Compose
Hazelcast
Angular2
HAProxy
Tomcat
Grails
