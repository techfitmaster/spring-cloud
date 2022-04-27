# ISTIO

## 目标
* 多语言服务注册到istio；
* 跨语言服务器进行通信。


## 实现方式

* springboot-server
* python-client


## 操作步骤

### spring-boot server

### python-client
1. create proto
```proto
syntax = "proto3";

service HelloWorld {
  rpc SayHello (HelloRequest) returns (HelloResponse) {}
}

message HelloRequest {
  string name = 1;
}

message HelloResponse {
  string message = 1;
}
```

