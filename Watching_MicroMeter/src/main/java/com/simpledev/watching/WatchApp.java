package com.simpledev.watching;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class WatchApp {

    public static void main(String ... args) {

        InfluxMeterRegistry registry = InfluxMeterRegistry.builder(new InfluxConfig() {
            @Override
            public String db() {
                return "db1";
            }
            @Override
            public String userName() {
                return "your user name";
            }
            @Override
            public String password() {
                return "your password";
            }
            @Override
            public String uri() {
                return "http://127.0.0.1:8086";
            }
            // 这个方法必须重写，不然得不到任何数据
            @Override
            public Duration step() {
                return Duration.ofSeconds(4);
            }
            @Override
            public String get(String key) {
                return null;
            }
        }).build();

        // 1. 通用标记，数据库中的记录都会有下面的标签
        registry.config().commonTags("app_name", "test_app", "version", "v0.0.1", "ip", "192.168.0.2");

        // 2. 添加进去
        Metrics.addRegistry(registry);

        // 3. 添加JVM相关信息(内置模块)
        new ClassLoaderMetrics().bindTo(registry);
        new JvmMemoryMetrics().bindTo(registry);
        new JvmGcMetrics().bindTo(registry);
        new ProcessorMetrics().bindTo(registry);
        new JvmThreadMetrics().bindTo(registry);

        // 4. 业务模块，主要是分两类使用，一种的counter(累加器), 另一种是gauge(单个值的变化)

        // 5. 增加一个订单计数器， 中间用.表示，到了influxdb就成了game_order
        Counter orderCounter = Metrics.counter("game.order");

        // 6. 增加一个申请计数器，可以加自己的参数
        Counter reqCounter = Metrics.counter("game.req", "req_type", "1");

        // 7. 增加一个gauge
        AtomicInteger onlinePlayerNum = registry.gauge("online_player_num", new AtomicInteger(0));

        // 8. 增加一个动态范围的容器
        Map<String, Integer> onlinePlayerMap = registry.gaugeMapSize("online_player_map", Tags.empty(), new HashMap<>());

        System.out.println("Start run ...");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            System.out.println("---add data");
            try {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                orderCounter.increment(random.nextInt(100));
                reqCounter.increment(random.nextInt(100));
                int num = random.nextInt(100);
                //
                System.out.println("---num:" + num);
                onlinePlayerNum.set(num);
                int n = random.nextInt(100);
                // n比map大就增加，否则移除数据
                if (n > onlinePlayerMap.size()) {
                    n = n - onlinePlayerMap.size();
                    while (n-- > 0) {
                        onlinePlayerMap.put(System.currentTimeMillis()+"", n);
                    }
                } else if (n < onlinePlayerMap.size()) {
                    n = onlinePlayerMap.size() - n;
                    Iterator iterator = onlinePlayerMap.entrySet().iterator();
                    while (iterator.hasNext() && n-- > 0) {
                        iterator.next();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 2L, 2L, TimeUnit.SECONDS);

        LockSupport.parkUntil(System.currentTimeMillis() + 60 * 5 * 1000); // 运行5分钟
        System.out.println("Run over ...");
        executor.shutdown();

        registry.close();
    }
}
