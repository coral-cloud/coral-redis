## rcpredis

docker stop rcpredis
docker rm rcpredis
docker rmi --force `docker images | grep redis-server | awk '{print $3}'`
docker run \
    -d --name rcpredis \
    -p 16399:6399 \
    -p 18083:8081 \
    10.3.4.111:5000/redis-server:5.0.0-2107161530

docker logs -f  rcpredis

