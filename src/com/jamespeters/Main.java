package com.jamespeters;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
	
	static Scanner input;
	static double a,b,c, lowerX, upperX, averageX, fa, fb, alphaY;
	static int terms, rootType, equationType;
	static double[] coefficients, powers;
	
	final static double ACCURACY = 0.001;

	public static void main(String[] args) {
		
		input = new Scanner(System.in);
		setup();
		
		fa = polynomialEquation(lowerX);
		fb = polynomialEquation(upperX);
		
		averageX = (lowerX+upperX)/2;
		
		alphaY = polynomialEquation( averageX);
		
		//Validity checks
		if((fa<0)&&(fb<0)){
			error("There are no solutions between these points OR there is more than 1 solution");
			setup();
		} else if((fa>0)&&(fb>0)){
			error("There are no solutions between these points OR there is more than 1 solution");
			setup();
		} else if((fa<=ACCURACY)&&(fa>=-ACCURACY)){
			error("You already have your solution!", "Solution is "+lowerX);
			System.exit(0);
		} else if((fb<=ACCURACY)&&(fb>=-ACCURACY)){
			error("You already have your solution!", "Solution is "+upperX);
			System.exit(0);
		}
		
		String leftAlignFormat = "| %-10s | %-12s | %-11s | %-11s | %-12s | %-12s | %n";

		//Table Begin
		System.out.format("+------------+--------------+-------------+-------------+--------------+--------------+%n");
		System.out.printf("|  a         | f(a)         |  b          |  f(b)       |  (a+b)/2     |  f(a+b)/2    | %n");
		System.out.format("+------------+--------------+-------------+-------------+--------------+--------------+%n");
		
		while(true){
			System.out.format(leftAlignFormat, FormatDouble(lowerX,9),FormatDouble(fa,9), FormatDouble(upperX,9), FormatDouble(fb,9), FormatDouble(averageX,9), FormatDouble(alphaY,9));
			if((alphaY<=ACCURACY)&&(alphaY>=-ACCURACY)){
				break;
			}
			if(alphaY>ACCURACY&&alphaY<fb){
				upperX = averageX;
			} else if(alphaY<-ACCURACY&&alphaY>fa){
				lowerX = averageX;
			}
			
			fa = polynomialEquation(lowerX);
			fb = polynomialEquation(upperX);
			
			averageX = (lowerX+upperX)/2;
			
			alphaY = polynomialEquation(averageX);
		}
   
		//End Line
		System.out.format("+------------+--------------+-------------+-------------+--------------+--------------+%n");
		
		notification("Root is x = "+averageX);

	}
	
	public static double polynomialEquation(double x){
		if(equationType==1){
			double total = 0;
			for(int i = 0; i < coefficients.length; i++){
				total += coefficients[i]*(Math.pow(x, powers[i]));
				//System.out.println("part "+i+" = "+coefficients[i]+" "+(Math.pow(x, powers[i])));
			}
			//System.out.println("For x = "+x+" y = "+total);
			return total;
		} else if(equationType==2){
			//CUSTOM EQUATION GOES HERE
			return (6*x)-Math.pow(3, x);
		}
		return 0;
	}
	
	public static void setup(){
		System.out.println("**--------------------------------------------------**");
		System.out.println("** Setup");
		System.out.println("**--------------------------------------------------**");
		
		notification("What type of equation do you want?", "1. Polynomial", "2. Custom");
		equationType = input.nextInt();
		
		if(equationType==1){
			System.out.println("How many terms does the equation have? In the format of successive decreasing powers.");
		
			terms = input.nextInt();
			coefficients = new double[terms];
			powers = new double[terms];
			
			for(int i = 0; i<terms; i++){
				System.out.println("In the format ax^i, for term "+(i+1)+", what is a?");
				coefficients[i] = input.nextDouble();
				if(powers[0]==0){
					System.out.println("And what is i?");
					powers[i] = input.nextDouble();
				} else {
					powers[i] = powers[i-1]-1;
				}
			}
			
			for(int i = 0; i<terms; i++){
				System.out.print(coefficients[i]+"x^"+powers[i]+" + ");
			}
			System.out.println();
		}
		
		notification("Which root do you want?", "1. Positive", "2. Negative", "3. Closest to x=0","4. Custom");
		rootType = input.nextInt();
		
		boundChooser();
	}
	
	public static void error(String s){
		System.out.println("!!--------------------------------------------------!!");
		System.out.println("!! "+s);
		System.out.println("!!--------------------------------------------------!!\r\n");
	}
	
	public static void error(String s, String s2){
		System.out.println("!!--------------------------------------------------!!");
		System.out.println("!! "+s);
		System.out.println("!! "+s2);
		System.out.println("!!--------------------------------------------------!!\r\n");
	}
	
	public static void notification(String s){
		System.out.println();
		System.out.println("**--------------------------------------------------**");
		System.out.println("** "+s);
		System.out.println("**--------------------------------------------------**\r\n");
	}
	
	public static void notification(String s, String s2){
		System.out.println();
		System.out.println("**--------------------------------------------------**");
		System.out.println("** "+s);
		System.out.println("** "+s2);
		System.out.println("**--------------------------------------------------**\r\n");
	}
	
	public static void notification(String s, String s2, String s3){
		System.out.println();
		System.out.println("**--------------------------------------------------**");
		System.out.println("** "+s);
		System.out.println("** "+s2);
		System.out.println("** "+s3);
		System.out.println("**--------------------------------------------------**\r\n");
	}
	
	public static void notification(String s, String s2, String s3, String s4){
		System.out.println();
		System.out.println("**--------------------------------------------------**");
		System.out.println("** "+s);
		System.out.println("** "+s2);
		System.out.println("** "+s3);
		System.out.println("** "+s4);
		System.out.println("**--------------------------------------------------**\r\n");
	}
	
	public static void notification(String s, String s2, String s3, String s4, String s5){
		System.out.println();
		System.out.println("**--------------------------------------------------**");
		System.out.println("** "+s);
		System.out.println("** "+s2);
		System.out.println("** "+s3);
		System.out.println("** "+s4);
		System.out.println("** "+s5);
		System.out.println("**--------------------------------------------------**\r\n");
	}
	
	public static String FormatDouble(double d, int maxValue){
		double rounded = Math.round(d*Math.pow(10, maxValue-2))/Math.pow(10, maxValue-2); 
		DecimalFormat df = new DecimalFormat("#0.00");
        df.setMaximumFractionDigits(maxValue-2);
		String s = df.format(rounded);
		if (s.length() > maxValue) {
		    s = s.substring(0, maxValue);
		}
		return s;
	}
	
	public static void boundChooser(){
		int i = 0;
		int i2 = 0;
		while(true){
			if(rootType==1||rootType==3){
				if((polynomialEquation(i)<=ACCURACY)&&(polynomialEquation(i+1)>=-ACCURACY)){
					lowerX = i;
					upperX = i+1;
					notification("Lower bound is: x = "+lowerX, "Upper bound is: x = "+upperX );
					break;
				}
				if((polynomialEquation(i)>=-ACCURACY)&&(polynomialEquation(i+1)<=ACCURACY)){
					lowerX = i+1;
					upperX = i;
					notification("Lower bound is: x = "+lowerX, "Upper bound is: x = "+upperX );
					break;
				}
			}
			if(rootType==2||rootType==3){
				if((polynomialEquation(i2)<=ACCURACY)&&(polynomialEquation(i2+1)>=-ACCURACY)){
					lowerX = i2;
					upperX = i2+1;
					notification("Lower bound is: x = "+lowerX, "Upper bound is: x = "+upperX );
					break;
				}
				if((polynomialEquation(i2)>=-ACCURACY)&&(polynomialEquation(i2+1)<=ACCURACY)){
					lowerX = i2+1;
					upperX = i2;
					notification("Lower bound is: x = "+lowerX, "Upper bound is: x = "+upperX );
					break;
				}
			} else {
				notification("What is the lower X value?");
				lowerX = input.nextDouble();
				notification("What is the upper X value?");
				upperX = input.nextDouble();
				
				if(polynomialEquation(lowerX)>polynomialEquation(upperX)){
					double temp = lowerX;
					lowerX = upperX;
					upperX = temp;
				}
				break;
			}
			i2--;
			i++;
		}
	}

}
