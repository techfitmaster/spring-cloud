docker_hub="nvxhub.nvxclouds.net:9443/skadi/grpc-server:20220118-1"
mvn  clean install -DskipTests

docker build -t  $docker_hub ./
docker push $docker_hub
