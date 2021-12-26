package com.alpha.common.util;

import java.util.Scanner;
import java.util.Stack;

/**
 * Copyright: 
 * Author: jiming.jing 
 * Date: 2017年11月1日 
 * Description:
 */
public class ChnStringToNumberUtils {

	public static int ChnToNum(String str) {
		String aval = "零一二三四五六七八九";
		String bval = "十百千万亿";
		int[] bnum = { 10, 100, 1000, 10000, 100000000 };
		// Scanner sc = new Scanner(System.in);
		// long num = 0;
		int num = 0;
		char[] arr = str.toCharArray();
		int len = arr.length;
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < len; i++) {
			char s = arr[i];
			// 跳过零
			if (s == '零')
				continue;
			// 用下标找到对应数字
			int index = bval.indexOf(s);
			// 如果不在bval中，即当前字符为数字，直接入栈
			if (index == -1) {
				stack.push(aval.indexOf(s));
			} else { // 当前字符为单位。
				int tempsum = 0;
				int val = bnum[index];
				// 如果栈为空则直接入栈
				if (stack.isEmpty()) {
					stack.push(val);
					continue;
				}
				// 如果栈中有比val小的元素则出栈，累加，乘N，再入栈
				while (!stack.isEmpty() && stack.peek() < val) {
					tempsum += stack.pop();
				}
				// 判断是否经过乘法处理
				if (tempsum == 0) {
					stack.push(val);
				} else {
					stack.push(tempsum * val);
				}
			}
		}
		// 计算最终的和
		while (!stack.isEmpty()) {
			num += stack.pop();
		}
		return num;
	}

	public static void main(String[] args) {
		System.out.println(ChnToNum("一百一十一"));
	}
}
