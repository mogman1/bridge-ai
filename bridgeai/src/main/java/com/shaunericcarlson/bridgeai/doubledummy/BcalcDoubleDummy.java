package com.shaunericcarlson.bridgeai.doubledummy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

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
    public DoubleDummyResult evaluate(Hand[] hands, Direction dealer) throws DoubleDummyException {
        String bcalcOutput = this.callBcalcdds(this.getPbnRepresentation(hands, dealer));
        String[] lines = bcalcOutput.split("\\n");
        if (lines.length != 21) {
            String vals = "";
            for (String l : lines) vals += l + "\n";
            throw new DoubleDummyException("Expected 21 lines, found [" + lines.length + "]:\n" + vals);
        }
        
        DoubleDummyResult ddr = new DoubleDummyResult(hands[0], hands[2]);
        for (int i = 1; i < lines.length; i++) {
            String[] pieces = lines[i].split(" ");
            String direction = pieces[2];
            String suit = pieces[1];
            String tricks = pieces[0];
            ddr.setTrick(Direction.TranslateShortString(direction), Suit.TranslateShortString(suit), Integer.parseInt(tricks));
        }
            
        return ddr;
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
