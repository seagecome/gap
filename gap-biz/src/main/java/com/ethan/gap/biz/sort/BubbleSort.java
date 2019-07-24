package com.ethan.gap.biz.sort;

import java.util.Arrays;

public class BubbleSort {
	public static void main(String[] args) {
		System.out.println(7>>>1);
		Integer[] num = new Integer[]{30,28,15,49,35,69,11,25};
		int len = num.length;
		
		for(int i = 0; i < len; i++) {
			for(int j = len-1; j > 0; j--) {
				int temp = num[j];
				if(num[j] < num[j-1]) {
					num[j] = num[j-1];
					num[j-1] = temp;
				}
						
			}
		}
		System.out.println("===BubbleSort===result:"+Arrays.toString(num));
		Integer[] num2 = new Integer[]{30,28,15,49,35,69,11,25};
		Arrays.sort(num2);
		System.out.println("===ArraysSort===result:"+Arrays.toString(num2));
		System.out.println("===checkResult===" + Arrays.toString(num).equals(Arrays.toString(num2)));
	}
}