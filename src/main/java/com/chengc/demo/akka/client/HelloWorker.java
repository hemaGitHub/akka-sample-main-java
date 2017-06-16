package com.chengc.demo.akka.client;

import akka.actor.UntypedActor;

/**
 * 真正干活的Worker：
 */
class HelloWorker extends UntypedActor {    //Worker是一个Actor，需要实现onReceive方法
    @Override
    public void onReceive(Object o) {
        System.out.println("Worker 收到消息----" + o );
        if (o instanceof String) { 
            String result = doWork(o);        //调用真实的处理方法
            getContext().replyUnsafe(result);//将结果返回给Master
        } 
    } 
    //Worker处理其实很简单，仅仅将参数字符串改造一下而已。只不过使其sleep了20秒，让它变得“耗时较长” 
    String doWork(String str) {
        try {
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result----" + str + " 。" 
    } 
}