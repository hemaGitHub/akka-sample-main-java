package sample.hello;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.ActorRef;

/**
 * 这个HelloWorld继承了UntypedActor，表明我们实现的是一个Actor。
 */
public class HelloWorld extends UntypedActor {

    /**
     * 其中的preStart是在启动这个Actor时调用的方法。在这里，我们创建了另一个Actor的实例。
     * 我们稍后会看到另一个Actor Greeter的实现。
     * 然后，我们调用tell方法给它发了一个消息，Greeter.Msg.GREET，
     * 后面的getSelf()给出了一个Actor的引用（ActorRef），用以表示发消息的Actor。这只是启动一个Actor，后面的部分才是更重要的。
     */
  @Override
  public void preStart() {
    // create the greeter actor
    final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
    // tell it to perform the greeting
    greeter.tell(Greeter.Msg.GREET, getSelf());
  }

    /**
     * onReceive方法是处理我们接收到消息的情况。
     * 这里我们看到，如果接收到的消息是一个Greeter.Msg.DONE，我们就会停下（stop）所有的处理，
     * 同样，getSelf()指明停下的目标，否则的话，就说我们没处理（unhandled）。
     * @param msg
     */
  @Override
  public void onReceive(Object msg) {
    if (msg == Greeter.Msg.DONE) {
        System.out.println("HelloWorld received a message :"+msg);
      // when the greeter is done, stop this actor and with it the application
      getContext().stop(getSelf());
    } else
      unhandled(msg);
  }
}
