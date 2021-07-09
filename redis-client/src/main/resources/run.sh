#!/bin/bash

ulimit -c unlimited
ulimit -n 32768

basePath=$(cd "$(dirname "$0")";pwd)
SERVICE_HOME=$basePath

cd $SERVICE_HOME

SERVICE_CONF="$SERVICE_HOME/"
SERVICE_LIBS="$SERVICE_HOME/libs"

SERVICE_MAIN="org.coral.redis.client.simple.CoralRedisExec"
declare -a JAVA_ARGS
JAVA_ARGS[0]="-Xmx256m"
JAVA_ARGS[1]="-Xms256m"

exec $JAVA_HOME/bin/java -Duser.dir=$SERVICE_HOME ${JAVA_ARGS[@]} -classpath $SERVICE_HOME:$SERVICE_LIBS/*:$SERVICE_CONF/* $SERVICE_MAIN