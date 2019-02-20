package com.rookiefly.commons.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @create: 2019-02-20
 **/
public class AlgorithmUtil {

    public static void main(String[] args) {
        System.out.println(LCS_length("acbcbcef", "abcbced"));
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(longestCommonPrefix(new String[]{"leets", "leetcode", "leet", "leets"}));
        int[] arr = {5, 4, 3, 6, 7, 4, 9};
        quickSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    /**
     * 最长公共子字符串
     * https://blog.csdn.net/u010397369/article/details/38979077
     *
     * @param s1
     * @param s2
     * @return
     */
    public static String LCS_length(String s1, String s2) {
        char[] s1Array = s1.toCharArray();
        char[] s2Array = s2.toCharArray();
        int length = 0;
        int pos = 0;
        int[][] temp = new int[s1Array.length][s2Array.length];
        for (int i = 0; i < s1Array.length; i++) {
            for (int j = 0; j < s2Array.length; j++) {
                if (s1Array[i] == s2Array[j]) {
                    if (i > 0 && j > 0) {
                        temp[i][j] = temp[i - 1][j - 1] + 1;
                    } else {
                        temp[i][j] = 0;
                    }
                    if (temp[i][j] > length) {
                        length = temp[i][j];
                        pos = j;
                    }
                } else {
                    temp[i][j] = 0;
                }
            }
        }

        return s2.substring(pos - length + 1, pos + 1);
    }

    /**
     * 无重复字符的最长子串
     *
     * @return
     */
    public static String lengthOfLongestSubstring(String s) {
        if (s.length() == 0 || s.length() == 1) {
            return s;
        }
        Map<Character, Integer> map = new HashMap<>();
        int pre = -1;
        int length = 0;
        int pos = -1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            Integer index = map.get(ch);
            if (index != null) {
                pre = Math.max(pre, index);
            }
            map.put(ch, i);
            if (length < i - pre) {
                length = i - pre;
                pos = i;
            }
        }
        return s.substring(pos + 1 - length, pos + 1);
    }

    /**
     * 数据最长公共前缀
     * https://blog.csdn.net/biezhihua/article/details/79859576
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].length() == i || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    public static void quickSort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    private static void qsort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);        //将数组分为两部分
            qsort(arr, low, pivot - 1);                   //递归排序左子数组
            qsort(arr, pivot + 1, high);                  //递归排序右子数组
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];     //枢轴记录
        while (low < high) {
            while (low < high && arr[high] >= pivot) --high;
            arr[low] = arr[high];             //交换比枢轴大的记录到左端
            while (low < high && arr[low] <= pivot) ++low;
            arr[high] = arr[low];           //交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }

    public static boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }
        int[] count1 = new int[26]; // s1每个字符出现的次数
        int[] count2 = new int[26]; // s2每个字符出现的次数
        // 1. 进行统计
        for (int i = 0; i < s1.length(); i++) {
            count1[s1.charAt(i) - 'a']++;
            count2[s2.charAt(i) - 'a']++;
        }
        // 2. 滑动窗口，滑块长度始终为 s1.length()
        for (int i = s1.length(); i < s2.length(); i++) {
            if (isSame(count1, count2)) {
                return true;
            }
            count2[s2.charAt(i - s1.length()) - 'a']--; // 去掉滑块当前的首个字符
            count2[s2.charAt(i) - 'a']++; // 添加最新的字符到滑块中
        }
        return isSame(count1, count2);
    }

    // 有且仅当 count1 中所有值都等于 count2 中对应值时满足条件
    public static boolean isSame(int[] count1, int[] count2) {
        for (int i = 0; i < count1.length; i++) {
            if (count1[i] != count2[i]) {
                return false;
            }
        }
        return true;
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<String>();
        helper(s, 0, "", res);
        return res;
    }

    public void helper(String s, int n, String out, List<String> res) {
        if (n == 4) {
            if (s.isEmpty()) res.add(out);
            return;
        }
        for (int k = 1; k < 4; ++k) {
            if (s.length() < k) break;
            int val = Integer.parseInt(s.substring(0, k));
            if (val > 255 || k != String.valueOf(val).length()) continue;
            helper(s.substring(k), n + 1, out + s.substring(0, k) + (n == 3 ? "" : "."), res);
        }
    }
}