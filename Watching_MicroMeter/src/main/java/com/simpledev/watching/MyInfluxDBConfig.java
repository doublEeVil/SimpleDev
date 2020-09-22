package com.simpledev.watching;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MyInfluxDBConfig {

    private InfluxMeterRegistry registry;

    public MyInfluxDBConfig() {
        init();
    }

    public void init() {
        // registry = new InfluxMeterRegistry(config, Clock.SYSTEM);
        registry = InfluxMeterRegistry.builder(config).build();
        // registry.config().commonTags("app", "test", "version", "v0.0.1");



        Metrics.addRegistry(registry);
    }

    Map<String, Counter> counterMap = new HashMap<>();

    public Counter counter(String name) {
        counterMap.putIfAbsent(name, registry.counter(name, "t1", "v1", "t2", "v2")); //实际上可以有多个参数
        return counterMap.get(name);
    }

    private InfluxConfig config = new InfluxConfig() {
        @Override
        public String get(String s) {
            return null;
        }

        @Override
        public String db() {
            return "db1";
        }

        @Override
        public String userName() {
            return "null";
        }

        @Override
        public String password() {
            return "null";
        }

        @Override
        public String uri() {
            return "http://127.0.0.1:8086";
        }

        /**
         * 如果不重写这个，得不到数据
         * @return
         */
        @Override
        public Duration step() {
            return Duration.ofSeconds(10);
        }
    };
}
