package com.hadoop;

/*Driver Class fro MapReduce */
public class App 
{
    public static void main( String[] args )
    {
        
        TaxiTripMapper map = new TaxiTripMapper();
        map.print();

    }
}
