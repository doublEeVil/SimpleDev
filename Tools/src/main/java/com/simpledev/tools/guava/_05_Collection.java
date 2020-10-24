package com.simpledev.tools.guava;

import com.google.common.collect.*;
import com.google.common.graph.*;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class _05_Collection {

    public static void main(String ... args) {
        // jdk常见的不可变集合
        try {
            List<String> list = Arrays.asList("2", "fff", "abc");
            list.add("lll"); // 不能添加，因为是不可变集合
        } catch (Exception e) {
            e.printStackTrace();
        }

        // guava的写法
        ImmutableSet<String> colors = ImmutableSet.of(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8"
        );

        // Multiset
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("aaa");
        multiset.add("aaa");
        multiset.add("bbb");
        System.out.println(multiset.count("aaa"));
        System.out.println(multiset.size());

        // Multimap
        System.out.println("---multimap");
        ListMultimap<String, Integer> listMultimap = MultimapBuilder.treeKeys().arrayListValues().build();
        listMultimap.put("a", 1);
        listMultimap.put("a", 2);
        listMultimap.put("a", 4);
        listMultimap.put("b", 5);
        listMultimap.put("c", 6);
        listMultimap.remove("a", 1);
        listMultimap.remove("d", 1);
        listMultimap.values().forEach(System.out::println); // 结果应该是2457

        // BiMap
        System.out.println("---BiMap");
        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("aaa", 123);
        userId.put("bbb", 124);
        System.out.println(userId.inverse().get(123)); //得到aaa
        userId.remove("aaa");
        userId.inverse().remove(124);
        System.out.println(userId.size()); // 此时应该为空

        // Table
        System.out.println("---Table");
        //  班级-性名-学号
        Table<Integer, String, Integer> table = HashBasedTable.create();
        table.put(1, "张三", 123);
        table.put(1, "李四", 124);
        table.put(2, "王五", 125);
        System.out.println(table.get(1, "李四")); // 结果应该是124
        System.out.println(table.get(1, "李四1")); // 结果应该是null

        // ClassToInstanceMap
        MutableClassToInstanceMap m = MutableClassToInstanceMap.create();
        m.put(Integer.class, 3);
        m.put(Double.class, 3.0);

        // RangeSet
        System.out.println("---RangeSet");
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1,10)); // [1,10]
        rangeSet.add(Range.closedOpen(11,15)); // [1,10],[11,15]
        rangeSet.add(Range.closedOpen(15,20)); // [1,10],[11,20]

        System.out.println(rangeSet.contains(5)); //true
        System.out.println(rangeSet.contains(50)); //false

        // RangeMap
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}

        // Collections
        // 静态构建, jdk方法
        List<String> nameList = new ArrayList<>();
        // guava方法
        nameList = Lists.newLinkedList();
        // Ints方法
        List<Integer> counts = Ints.asList(1, 2, 3,4);
        // 列表反转
        counts = Lists.reverse(counts);

        // 集合操作，主要就是并集，交集，差集，补集
        Set<Integer> set1 = ImmutableSet.of(1,2,3);
        Set<Integer> set2 = ImmutableSet.of(1,2,6);
        Set<Integer> set3 = Sets.union(set1, set2); // 并集[1,2,3,6]
        Set<Integer> set4 = Sets.intersection(set1, set2); // 交集[1,2]
        Set<Integer> set5 = Sets.difference(set1, set2); // 差集[6]
        Set<Integer> set6 = Sets.symmetricDifference(set1, set2); // 对称差集[3,6]， 等同于并集减去交集
    }
}
