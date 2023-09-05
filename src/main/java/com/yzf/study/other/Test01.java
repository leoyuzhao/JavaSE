package com.yzf.study.other;

import java.util.HashMap;
import java.util.Map;

public class Test01 {

    private static String PingNian = "P";
    private static String RunNian = "R";

    // 月份天数映射
    private static Map<String, Integer> MONTH_DAY_MAP = null;

    static {
        MONTH_DAY_MAP = new HashMap<>();
        MONTH_DAY_MAP.put("1", 31);
        MONTH_DAY_MAP.put("2" + PingNian, 28);
        MONTH_DAY_MAP.put("2" + RunNian, 29);
        MONTH_DAY_MAP.put("3", 31);
        MONTH_DAY_MAP.put("4", 30);
        MONTH_DAY_MAP.put("5", 31);
        MONTH_DAY_MAP.put("6", 30);
        MONTH_DAY_MAP.put("7", 31);
        MONTH_DAY_MAP.put("8", 31);
        MONTH_DAY_MAP.put("9", 30);
        MONTH_DAY_MAP.put("10", 31);
        MONTH_DAY_MAP.put("11", 30);
        MONTH_DAY_MAP.put("12", 31);
    }


    public static void main(String[] args) {

        // 指定的 年 月 日
        int year = 2024;
        int month = 4;
        int day = 7;
        // 未来的天数
        int fDays = 12378;
        // 输出
        System.out.println(getDateFromNow(year,month,day,fDays));

    }

    /**
     * @param year  指定年
     * @param month 指定月
     * @param day   指定日
     * @param fDays 未来的天数
     * @return 未来的日期
     */
    static Map<String, Integer> getDateFromNow(int year, int month, int day, int fDays) {


        // 确定年
        int fYear = 0;

        // 计算指定年剩余天数
        int yearSurplusDays = getYearSurplusDays(year, month, day);

        // 扣减后剩余的天数
        int fSurplusDays = fDays - yearSurplusDays;

        if (fSurplusDays > 0) {
            // 跨年
            for (int i = year + 1; ; i++) {
                int yearDays = getYearDays(i);
                if (fSurplusDays <= yearDays) {
                    fYear = i;
                    break;
                }
                fSurplusDays -= yearDays;
            }
        } else {
            // 不跨年
            fSurplusDays = fDays;
            fYear = year;
        }


        // 确定月
        int fMonth = 0;
        if (fYear == year) {
            // 指定日期所在月剩余天数
            int monthSurplusDays = getMonthDays(month, year) - day;
            // 指定的月份
            if (fSurplusDays - monthSurplusDays <= 0) {
                fMonth = month;
            } else {
                fSurplusDays -= monthSurplusDays;
                for (int j = month + 1; j <= 12; j++) {
                    int monthDays = getMonthDays(j, year);
                    if (fSurplusDays < monthDays) {
                        fMonth = j;
                        break;
                    }
                    fSurplusDays -= monthDays;
                }
            }
        } else {
            for (int j = 1; j <= 12; j++) {
                int monthDays = getMonthDays(j, fYear);
                if (fSurplusDays < monthDays) {
                    fMonth = j;
                    break;
                }
                fSurplusDays -= monthDays;
            }
        }


        // 确定日
        int fDay = 0;
        if (year == fYear && month == fMonth) {
            fDay = day + fSurplusDays;
        } else {
            fDay = fSurplusDays;
        }

        Map<String, Integer> map = new HashMap<>();
        map.put("year", fYear);
        map.put("month", fMonth);
        map.put("day", fDay);
        return map;
    }

    /**
     * 获取指定日期的年份剩余多少天
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 剩余天数
     */
    private static int getYearSurplusDays(int year, int month, int day) {
        int days = 0;
        int monthDays = getMonthDays(month, year);
        // 月剩余天数
        int monthSurplusDays = monthDays - day;
        days += monthSurplusDays;
        // 其他月份天数
        for (int i = month + 1; i <= 12; i++) {
            days += getMonthDays(i, year);
        }
        return days;
    }

    /**
     * 计算每个月多少天
     *
     * @param month 月份
     * @param year  年份
     * @return
     */
    private static int getMonthDays(int month, int year) {
        int days = 0;
        if (month == 2) {
            boolean runNian = isRunNian(year);
            if (runNian) {
                days = MONTH_DAY_MAP.get(2 + "" + RunNian);
            } else {
                days = MONTH_DAY_MAP.get(2 + "" + PingNian);
            }
        } else {
            days = MONTH_DAY_MAP.get(month + "");
        }
        return days;
    }

    /**
     * 获取年份天数
     *
     * @param year
     * @return
     */
    private static int getYearDays(int year) {
        boolean runNian = isRunNian(year);
        if (runNian) {
            return 366;
        } else {
            return 365;
        }
    }

    /**
     * 判断是否闰年
     *
     * @param year
     * @return
     */
    private static boolean isRunNian(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }


}
