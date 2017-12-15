package com.company;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.*;

public class Main {

    public static void main(String[] args) throws ScriptException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a boolean expression int the format of 'A && B || C'");
        String expressionScan = input.nextLine().toLowerCase();
        FileWriter fw = new FileWriter("theTruthWillSetYouFree.out");
        PrintWriter output = new PrintWriter(fw);
        String[] booleanVariableNames = expressionScan.split("\\s+");
        int varCounter = 0;
        for (String booleanVariableName1 : booleanVariableNames) {
            if (!booleanVariableName1.contains("&&")
                    && !booleanVariableName1.contains("||")
                    && !booleanVariableName1.contains("!="))
                varCounter++;
        }

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        output.println("Expression: " + expressionScan);
        for (String booleanVariableName : booleanVariableNames) {
            if (!booleanVariableName.contains("&&")
                    && !booleanVariableName.contains("||")
                    && !booleanVariableName.contains("!="))
                output.print("\t" + booleanVariableName);
        }
        output.print("\t\t" + expressionScan + "\n");

        for (int i = 0; i < Math.pow(2, varCounter); i++) {
            StringBuilder bin = new StringBuilder(Integer.toBinaryString(i));
            while (bin.length() < varCounter)
                bin.insert(0, "0");
            char[] chars = bin.toString().toCharArray();
            boolean[] boolArray = new boolean[varCounter];
            for (int j = 0; j < chars.length; j++) {
                boolArray[j] = chars[j] == '0';
            }
            output.println(Arrays.toString(boolArray) +
                    "\t\t" + engine.eval(booleanExpressionBuilder(boolArray, booleanVariableNames)));
        }
        fw.close();
        output.close();
    }

    private static String booleanExpressionBuilder(boolean[] values, String[] expression) {
        StringBuilder returnString = new StringBuilder();
        int valuePlace = 0;
        for (String anExpression : expression) {
            if (!anExpression.contains("&&")
                    && !anExpression.contains("||")
                    && !anExpression.contains("!=")) {
                returnString.append(values[valuePlace]);
                valuePlace++;
            } else
                returnString.append(" ").append(anExpression).append(" ");
        }
        return returnString.toString();
    }
}
