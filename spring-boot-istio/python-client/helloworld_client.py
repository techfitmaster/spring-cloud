'''
Author: zhengxing.hu
Date: 2021-11-02 16:21:18
LastEditTime: 2022-01-18 15:20:23
Description:
'''


import logging
import re

import grpc
import helloworld_pb2
import helloworld_pb2_grpc
from flask import Flask, jsonify, request

app = Flask(__name__)


def run(name):
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    address = '127.0.0.1:20001'
    channel = grpc.insecure_channel(
        address, options=(('grpc.enable_http_proxy', 0),))
    stub = helloworld_pb2_grpc.HelloWorldStub(channel)

    request = helloworld_pb2.HelloRequest(name=name)
    logging.info("Sending to grpc server.......")
    response = stub.SayHello(request)
    logging.info(response)
    return response


@app.route("/hello", methods=["GET"])
def hello_world():
    name = request.args.get('name')
    # name = request.args.get('name')
    response = run(name)
    return jsonify(response)


if __name__ == '__main__':
    logging.basicConfig()
    app.run(debug=True)
