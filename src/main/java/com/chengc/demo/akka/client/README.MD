这里有三个角色：Client、Master、Worker
Client傻乎乎地发同步请求给Master，一直等到结果返回客户端才离开。
Master接收客户端发来的请求，然后将请求交给Worker处理，处理完成之后将结果返回给Client。
Worker负责具体的业务处理，它耗费的事件比较长。

所以这里的关键在于Master，如果Master线性地“接收请求——调用Worker处理得到返回结果——将结果返回”，这样的系统必将歇菜。
使用Akka可以方便地将它变成并行地。