package com.shaunericcarlson.bridgeai.doubledummy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

import com.shaunericcarlson.bridgeai.Direction;
import com.shaunericcarlson.bridgeai.Hand;
import com.shaunericcarlson.bridgeai.Suit;

public class BcalcDoubleDummy extends DoubleDummy {
    /**
     * Makes call to external program to compute double dummy evaluation of a deal in PBN representatino
     * @param pbnRepresentation
     * @return
     */
    private String callBcalcdds(String pbnRepresentation) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            ProcessBuilder bcalc = new ProcessBuilder("./bcalcdds", pbnRepresentation);
            bcalc.directory(new File("src/main/resources/bcalc"));
            p = bcalc.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return output.toString();
    }
    
    @Override
    protected boolean evaluate(Hand[] hands, Direction dealer, Map<Direction, Map<Suit, Integer>> data) {
        boolean isValid;
        String bcalcOutput = this.callBcalcdds(this.getPbnRepresentation(hands, dealer));
        String[] lines = bcalcOutput.split("\\n");
        if (lines.length != 21) {
            isValid = false;
        } else {
            for (int i = 1; i < lines.length; i++) {
                String[] pieces = lines[i].split(" ");
                String direction = pieces[2];
                String suit = pieces[1];
                String tricks = pieces[0];
                data.get(Direction.TranslateShortString(direction)).put(Suit.TranslateShortString(suit), Integer.parseInt(tricks));
            }
            
            isValid = true;
        }

        return isValid;
    }
    
    /**
     * Converts an array of Hands into PBN notation
     * @param hands
     * @param dealer
     * @return
     */
    private String getPbnRepresentation(Hand[] hands, Direction dealer) {
        String pbn = dealer.getShortString() + ":";
        for (int i = 0; i < hands.length; i++) pbn += hands[i] + " ";
        
        return pbn.trim();
    }
}
