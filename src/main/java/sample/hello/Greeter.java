package sample.hello;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {

  public static enum Msg {
    GREET, DONE;
  }

    /**
     * 在onReceive方法里，如果它接收到的消息是Msg.GREET，它就打印出“Hello World!”，
     * 然后，给发送者回复一条Msg.DONE。
     * 没处理的话，就说没处理。
     * @param msg
     */
  @Override
  public void onReceive(Object msg) {
    if (msg == Msg.GREET) {
      System.out.println("Greeter received a message :"+msg);
      getSender().tell(Msg.DONE, getSelf());
    } else
      unhandled(msg);
  }

}
