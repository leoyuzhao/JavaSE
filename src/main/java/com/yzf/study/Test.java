package com.yzf.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        // 按年月存放假期
        Map<String, List<String>> holidayMap = new HashMap<>();
        // 某年某月的假期
        List<String> hd_2023_1 = new ArrayList<>();
        hd_2023_1.add("2023-1-1");
        hd_2023_1.add("2023-1-3");
        hd_2023_1.add("2023-1-5");
        holidayMap.put("2023-1", hd_2023_1);

        // 比如计算 2023-1-1   2023-3-1 的假期
        // 直接从 holidayMap.get("2023-1") 取出，这几个月的假日 List<String> a
        // 轮询判断 a 中的日期是否在 2023-1-1   2023-3-1 这个区间内，并计数 count
        //




    }




}
