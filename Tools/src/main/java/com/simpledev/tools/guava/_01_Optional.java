package com.simpledev.tools.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ComparisonChain;

import static com.google.common.base.Preconditions.*;

public class _01_Optional {

    public static void main(String ... args) {
        // 创建
        Optional<Integer> optional = Optional.of(Integer.valueOf(2));
        System.out.println(optional.get());

        // 创建一个null值, 如果直接使用of方法会触发异常
        optional = Optional.fromNullable(null);
        System.out.println(optional.or(() -> 4));

        // 前置条件
        checkArgument(2 == 3); // 异常不显示msg信息
        checkArgument(2 == 3, " err msg 1"); // 显示msg信息
        checkArgument(2 == 3, " err is %s", " not equal"); // 占位符风格，仅支持%s

        // 检查null
        checkNotNull(null);
        // 检查状态
        checkState(2==3);
        // 指定长度的列表、字符串和数组中检查index是否有效
        checkElementIndex(1, 3);
        // 指定位置是否有效，同上效果
        checkPositionIndex(1, 4);
        // 指定位置范围是否有效
        checkPositionIndexes(1, 2, 5);

        // 通用Object方法，主要就4个，equal, hashCode, toString, compare/compareTo
        // equal方法， 不用再去处理null的情况
        System.out.println(Objects.equal(2, "2"));

        // hashCode 方法，同JDK内置的相同
        // Objects.hashCode(field1, field2, ..., fieldn)
        System.out.println(Objects.hashCode("a", "b", "c"));

        // toString 方法，返回ClassName{filed1=val1,field2=val2}
        // compare方法
        class MyObj implements Comparable<MyObj> {
            String name;
            int age;
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("name", name)
                        .add("age", age)
                        .toString();
            }

            @Override
            public int compareTo(MyObj o) {
                return ComparisonChain.start()
                        .compare(this.age, o.age)
                        .compare(this.name, o.name)
                        .result();
            }
        }
    }
}
