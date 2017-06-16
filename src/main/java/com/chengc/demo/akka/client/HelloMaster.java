package com.chengc.demo.akka.client;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 负责并发调度的Master：
 */
class HelloMaster extends UntypedActor {
    @Override
    public void onReceive(Object o) {
        System.out.println("Master接收到Work消息：" + o) ;
        def clientChannel = getContext().;    //客户端链接Channel
        //启动worker actor 
        ActorRef worker = Actors.actorOf(new UntypedActorFactory() {
            public UntypedActor create() { 
                return new HelloWorker(); 
            } 
        }).start();

        //这里实现真正的并发 
        Future f1 = Futures.future(new Callable() {
            public Object call() {
                Object result = worker.sendRequestReply(o);            //将消息发给worker actor，让Worker处理业务，同时得到返回结果
                worker.stop();
                System.out.println("Worker Return----" + result);
                clientChannel.sendOneWay(result);                //将结果返回给客户端
                return result;
            }
        });

        System.out.println("Future call over");
    }

    public static void main(String[] args) {    //启动Master进程，绑定IP、端口和服务 
        Actors.remote().start("10.68.15.113", 9999).register( 
                "hello-service", 
                Actors.actorOf(HelloMaster.class)); 
    } 
}