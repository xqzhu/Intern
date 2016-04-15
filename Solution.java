import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Solution {
	/*
	 * Xueqing Zhu
	 * 04/14/2016
	 * Internship for Telenav
	 * 
	 * This program is to find possible arithmetical expression equal to a target.
	 * If no such expression exists, output will be 'none'
	 * 
	 * To run this code:
	 * 1. javac Solution.java
	 * 2. java Solution [The list of numbers as input]
	 * 3. Output will be printed using System.out.println().
	 * 
	 * Basic idea:
	 * 1. Use class Node to store the result as will as the expression.
	 * 2. During every recursion shrink the size of the Node array, make at least one operations.
	 * 3. If we end up with only one element, if it is equal to target, Then we have what we want.
	 * 4. Else continue to find such expression using recursion and Backtracking.
	 * 
	 * Representations:
	 * 1. Using 0-3 to represent plus, minus, multiplication and division.
	 * 2. For a single number, use 4 to represent the highest priority.
	 */
	public static boolean find = false;
	public static String result = null;
	public static class Node{
		int num;
		String list;
		int sign;
		Node(int n, int s){
			num = n;
			list = new String("" + n);
			sign = s;
		}
	}
	public static void main(String[] args){
		int[] nums;
		int target;
		int length = args.length - 1;
		if (length < 1 || length > 4){
			System.out.print("none");
		}
		nums = new int[length];
		target = Integer.parseInt(args[0]);
		for (int i = 0; i < length; i++){
			nums[i] = Integer.parseInt(args[i + 1]);
		}
		if (nums.length < 2 || nums.length > 5){
			System.out.println("none");
		}
		Node[] nodeList = new Node[length];
		for (int i = 0; i < length; i++){
			nodeList[i] = new Node(nums[i], 4);
		}
		findTarget(nodeList, target, length);
		if (find == true){
			System.out.println(result + " = " + target);
		}
		else {
			System.out.println("none");
		}
    }
	public static Node construct(Node a, Node b, int sign){
		if (sign == 0){
			Node n = new Node(a.num + b.num, sign);
			n.list = new String(a.list + " + " + b.list);
			return n;
		}
		else if (sign == 1){
			Node n = new Node(a.num - b.num, sign);
			if (b.sign <= 1){
				n.list = new String(a.list + " - (" + b.list + ")");
			}
			else {
				n.list = new String(a.list + " - " + b.list);
			}
			return n;
		}
		else if (sign == 2){
			Node n = new Node(a.num * b.num, sign);
			if (a.sign <= 1 && b.sign <= 1){
				n.list = new String("(" + a.list +") * (" + b.list + ")");
			}
			else if (a.sign <= 1){
				n.list = new String("(" + a.list +") * " + b.list);
			}
			else if (b.sign <= 1){
				n.list = new String(a.list + " * (" + b.list + ")");
			}
			else {
				n.list = new String(a.list + " * " + b.list);
			}
			return n;
		}
		else if (sign == 3){
			Node n = new Node(a.num / b.num, sign);
			if (a.sign <= 1 && b.sign <= 1){
				n.list = new String("(" + a.list +") / (" + b.list + ")");
			}
			else if (a.sign <= 1){
				n.list = new String("(" + a.list +") / " + b.list);
			}
			else if (b.sign <= 2){
				n.list = new String(a.list + " / (" + b.list + ")");
			}
			else {
				n.list = new String(a.list + " / " + b.list);
			}
			return n;
		}
		return null;
	}
	public static boolean findTarget(Node[] nodelist, int target, int n){
		if (n == 1){
			if (target == nodelist[0].num){
				find = true;
				result = new String(nodelist[0].list);
				return true;
			}
			else {
				return false;
			}
		}
		for (int i = 0; i < n - 1; i++){
			for (int j = i + 1; j < n; j++){
				Node temp1 = nodelist[i];
				Node temp2 = nodelist[j];
				nodelist[j] = nodelist[n - 1];
			
				nodelist[i] = construct(temp1, temp2, 0);
				if (findTarget(nodelist, target, n - 1)){
					return true;
				}
			
				nodelist[i] = construct(temp1, temp2, 1);
				if (findTarget(nodelist, target, n - 1)){
					return true;
				}
			
				nodelist[i] = construct(temp2, temp1, 1);
				if (findTarget(nodelist, target, n - 1)){
					return true;
				}
			
				nodelist[i] = construct(temp1, temp2, 2);
				if (findTarget(nodelist, target, n - 1)){
					return true;
				}
			
				if (temp2.num != 0 && temp1.num % temp2.num == 0 && Math.abs(temp1.num) >= Math.abs(temp2.num)){
					nodelist[i] = construct(temp1, temp2, 3);
					if (findTarget(nodelist, target, n - 1)){
						return true;
					}
				}
			
				if (temp1.num != 0 && temp2.num % temp1.num == 0 && Math.abs(temp2.num) >= Math.abs(temp1.num)){
					nodelist[i] = construct(temp2, temp1, 3);
					if (findTarget(nodelist, target, n - 1)){
						return true;
					}
				}
			
				nodelist[i] = temp1;
				nodelist[j] = temp2;
			}
		}
		return false;
	}
}