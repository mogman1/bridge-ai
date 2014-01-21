package com.shaunericcarlson.bridgeai;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Dealer d = new Dealer();
        for (Hand h : d.deal()) {
            System.out.println(h + "\n");
        }
    }
}
