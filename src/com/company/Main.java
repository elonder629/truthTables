package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.*;

public class Main {

    public static void main(String[] args) throws ScriptException, IOException{
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a boolean expression int the format of 'A && B || C'");
        String expressionScan = input.nextLine().toLowerCase();
        FileWriter fw = new FileWriter("theTruthWillSetYouFree.out");
        PrintWriter output = new PrintWriter(fw);

        String[] booleanVariableNames = expressionScan.split("\\s+");
        int varCounter = 0;
        for (int x = 0; x < booleanVariableNames.length; x++){
            if (!booleanVariableNames[x].contains("&&")
                    && !booleanVariableNames[x].contains("||")
                    && !booleanVariableNames[x].contains("!=")) {
                varCounter++;
            }
        }

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        output.println("Expression: " + expressionScan);
        for (int g = 0; g < booleanVariableNames.length; g++){
            if (!booleanVariableNames[g].contains("&&")
                    && !booleanVariableNames[g].contains("||")
                    && !booleanVariableNames[g].contains("!=")) {
                output.print("\t" + booleanVariableNames[g]);
            }
        }
        output.print("\t\t" + expressionScan + "\n");

        int n = varCounter;
        for (int i = 0; i < Math.pow(2, n); i++) {
            String bin = Integer.toBinaryString(i);
            while (bin.length() < n)
                bin = "0" + bin;
            char[] chars = bin.toCharArray();
            boolean[] boolArray = new boolean[n];
            for (int j = 0; j < chars.length; j++) {
                boolArray[j] = chars[j] == '0';
            }
            output.println(Arrays.toString(boolArray) + "\t\t" + engine.eval(booleanExpressionBuilder(boolArray, booleanVariableNames)));

        }
        fw.close();
        output.close();

    }

    public static String booleanExpressionBuilder(boolean[] values, String[] expression){
        String returnString = "";
        int valuePlace = 0;
        for (int x = 0; x < expression.length; x++){
            if (!expression[x].contains("&&")
                    && !expression[x].contains("||")
                    && !expression[x].contains("!=")) {
                returnString += values[valuePlace];
                valuePlace++;
            }
            else {
                returnString += " " +expression[x] + " ";
            }

        }
        System.out.println(returnString);
        return returnString;
    }
}
