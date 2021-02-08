docker rm `docker ps -a -q`
docker rmi --force `docker images | grep mp | awk '{print $3}'`
gradle clean
gradle buildDocker -x test