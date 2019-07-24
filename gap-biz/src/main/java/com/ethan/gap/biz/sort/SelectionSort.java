package com.ethan.gap.biz.sort;

import java.util.Arrays;

public class SelectionSort {
	public static void main(String[] args) {
		Integer[] num = new Integer[]{30,28,15,9,17,49,35,69,11,25};
		for(int i = 0; i < num.length; i++) {
			Integer small = num[i];
			int smallIndex = 0;
			for(int j = i+1; j < num.length; j++) {
				if(small.compareTo(num[j])>0) {
					small = num[j];
					smallIndex = j;
				}
			}
			if(smallIndex != 0) {
				Integer min = num[smallIndex];
				num[smallIndex] = num[i];
				num[i]=min;
			}
			
		}
		System.out.println(Arrays.toString(num));
		Integer[] num2 = Arrays.copyOf(num, num.length);
		Arrays.sort(num2);
		System.out.println(Arrays.toString(num2));
	}
}
