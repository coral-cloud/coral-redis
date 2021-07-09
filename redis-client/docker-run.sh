
## test
docker stop redis-test
docker rm redis-test
docker rmi --force `docker images | grep redis-test | awk '{print $3}'`
docker run \
    -d --name redis-test \
    -p 19093:9093 \
    -p 18081:8081 \
    --env CR_RDIP="10.3.4.111" \
    --env CR_RDPORT="9221" \
    --env CR_THREAD="2" \
    --env CR_TESTNUM="100000" \
    --env CR_TESTSIZE="128" \
    10.3.4.111:5000/redis-test:1.0.0-2107051540

docker logs -f  redis-test


## test
docker stop redis-test
docker rm redis-test
docker rmi --force `docker images | grep redis-test | awk '{print $3}'`
docker run \
    -d --name redis-test \
    -p 19093:9093 \
    -p 18081:8081 \
    --env CR_RDIP="10.3.4.111" \
    --env CR_RDPORT="9221" \
    --env CR_THREAD="10" \
    --env CR_TESTNUM="100000000" \
    --env CR_TESTSIZE="256" \
    10.3.4.111:5000/redis-test:1.0.0-2107051902

docker logs -f  redis-test
