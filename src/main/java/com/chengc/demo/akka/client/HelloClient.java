package com.chengc.demo.akka.client;

import akka.actor.Actor;
import akka.actor.ActorRef;

/**
 * 先看看Client，模拟同时多个客户端给Master发请求
 */
class HelloClient implements Runnable {
    int seq ;
    String serviceName;

    HelloClient(int seq, String serviceName) { 
        this.seq = seq ;
        this.serviceName = serviceName;
    }

    public void run() {
        ActorRef actor = Actor..actorFor(serviceName, "10.68.15.113", 9999);
        String str = "Hello--" + seq;
        System.out.println("请求-----${str}");
        Object res = actor.sendRequestReply(str);
        System.out.println("返回-----${res}");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new HelloClient(i, "hello-service"))
            thread.start();        //同时启动5个客户端请求Master
        }
    }
}