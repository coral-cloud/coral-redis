#基础镜像
FROM openjdk:8u292-jre
VOLUME ["/tmp"]

#docker打包时配置文件
ADD . /home/redis-test/

#指定时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["/home/redis-test/run.sh"]

