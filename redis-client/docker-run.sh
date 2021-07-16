
## test
docker stop redis-test-r
docker rm redis-test-r
docker rmi --force `docker images | grep redis-test | awk '{print $3}'`
docker run \
    -d --name redis-test-r \
    -p 19092:9093 \
    -p 18082:8081 \
    --env CR_RDIP="10.3.1.66" \
    --env CR_RDPORT="7009" \
    --env CR_THREAD="5" \
    --env CR_TESTNUM="10000000" \
    --env CR_TESTSIZE="128" \
    10.3.4.111:5000/redis-test:5.0.0-2107161445

docker logs -f  redis-test-r


## test
docker stop redis-test
docker rm redis-test
docker rmi --force `docker images | grep redis-test | awk '{print $3}'`
docker run \
    -d --name redis-test \
    -p 19092:9093 \
    -p 18082:8081 \
    --env CR_RDIP="10.3.4.111" \
    --env CR_RDPORT="16399" \
    --env CR_THREAD="15" \
    --env CR_TESTNUM="1000000000" \
    --env CR_TESTSIZE="1024" \
    --env CR_TTL="0" \
    10.3.4.111:5000/redis-test:5.0.0-2107161726

docker logs -f  redis-test