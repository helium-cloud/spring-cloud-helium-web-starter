
#后台管理系统docker
docker stop mp-admin

#docker rm `docker ps -a -q`

docker run \
    -d --name mp-admin \
    -p 8091:8091 \
    --env AUTH_ENABLE=false \
    --env udmUrl="http://172.16.106.65:29503" \
    --env DB_HOST="172.16.106.65:3000" \
    --env ccf.base.interface=kafka \
    -v /tmp:/tmp \
    10.10.208.193:5000/mp-admin:3.0.0-2012142108

docker logs -f --tail=100 mp-admin