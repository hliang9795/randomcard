package com.cedar.number.fun;

public class Mod10Utils {
    /**
     * Calculate check digit for a long number x of y digits. Assumes check digit
     * will be append as last digits of the new number
     */
    public static long appendMod10(long x) {
        boolean isDouble = true;
        long n=x;
        long sum=0;
        long d;
        long c1,c2;

        while( n>0 ) {
            d = n % 10;
            n = n/10;
            c1 = (d * (isDouble ? 2 : 1));
            c2 = (c1 >= 10) ? c1-10 + c1/10 : c1;
            sum += c2;
            //System.out.println(x + ":" + " d:" + d + " c1:" + c1 + " c2:" + c2 + " isDouble:" + isDouble + " sum:" + sum);
            isDouble = !isDouble;
        }
        //for(c1=0; (c1+sum)%10!=0; c1++);
        c1 = sum % 10;
        c1 = (c1==0) ? 0 : 10 - c1;
        //System.out.println(" check digits:" + c1 + "\n");
        return x*10 + c1;
    }
}

