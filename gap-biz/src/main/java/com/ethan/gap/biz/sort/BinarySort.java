package com.ethan.gap.biz.sort;

import java.util.Arrays;

public class BinarySort {
	public static void main(String[] args) {
		Integer[] num = new Integer[]{30,28,15,9,17,49,35,69,11,25};
		int start = countRunAndMakeAscending(num,num.length);
		System.out.println(start);
		binarySort(num, num.length, start);
		
		System.out.println(Arrays.toString(num));
	}
	
	private static void binarySort(Integer[] a, int hi, int start) {
		for(;start < hi; start++) {
			Integer saobing = a[start];
			
			int left = 0;
			int right = start;
			
			while(left < right) {
				int mid = (left + right) >>> 1;
				if(saobing.compareTo(a[mid]) < 0) {
					right = mid;
				}else {
					left = mid+1;
				}
			}
			
			int n = start - left;
			switch (n) {
            case 2:  a[left + 2] = a[left + 1];
            case 1:  a[left + 1] = a[left];
                      break;
            default: System.arraycopy(a, left, a, left + 1, n);
         }
         a[left] = saobing;
		}
	}
	
	private static int countRunAndMakeAscending(Integer[] a, int hi) {
		int runHi = 1;
		if(runHi == hi) {
			return 1;
		}
		if(a[runHi].compareTo(a[0]) < 0) {
			while(runHi < hi && a[runHi].compareTo(a[runHi-1]) < 0) {
				runHi++;
			}
			reverseRange(a, 0, runHi);
		}else {
			while(runHi < hi && a[runHi].compareTo(a[runHi-1]) >= 0) {
				runHi++;
			}
		}
		return runHi;
	}
	
	private static void reverseRange(Integer[] num, int lo, int hi) {
		hi--;
		while(lo < hi) {
			int temp = num[lo];
			num[lo++] = num[hi];
			num[hi--]=temp;
		}
	}
}
