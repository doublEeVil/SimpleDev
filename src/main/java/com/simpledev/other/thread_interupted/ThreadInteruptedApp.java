package com.simpledev.other.thread_interupted;

import com.simpledev.other.thread_interupted.msg.Msg1;
import com.simpledev.other.thread_interupted.msg.Msg2;

/**
 * 如果出现线程死循环的问题，没有完美的解决方法，但有两种可以实验的思路
 * 1. 中断机制
 * 2. 类替换机制
 *
 * 无论哪一种，第一个都是要找到发生死循环的线程，然后再对线程做对应的处理
 * 中断机制要求while的条件判断加上是否中断，显然对于业务逻辑而言，不太现实。
 *
 * 类替换机制，或者线程替换机制，则要求对变量做一些保存
 */
public class ThreadInteruptedApp {

    public static void main(String ... args) {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(2000L);

                Msg1 msg1 = new Msg1();
                msg1.setHeader(i++);
                msg1.setName("" + System.currentTimeMillis());
                Dispatcher.getINS().dispatch(msg1);

                Msg2 msg3 = new Msg2();
                msg3.setHeader(i++);
                msg3.setOp("check ... ");
                Dispatcher.getINS().dispatch(msg3);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
