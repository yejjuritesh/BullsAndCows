package com.game;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int codesize = 0;
        System.out.println("Input the length of the secret code:");
        String seclenght = s.nextLine();
        try{
            codesize = Integer.parseInt(seclenght);
        }catch(Exception e){
            System.out.println("Error: \""+seclenght+"\" isn't a valid number.");
            System.exit(1);
        }
        System.out.println("Input the number of possible symbols in the code:");
        int charrange = s.nextInt();
        if(codesize==0){
            System.out.println("Error: length of secret code cannot be 0");
        }else if(codesize>charrange){
            System.out.println("Error: it's not possible to generate a code with a length of"+codesize+"with"+charrange+"unique symbols");
        }else if(codesize>36 || charrange>36){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)");
        }else {
            String secretcode = uniqueCharacterSecretCodeGenerator(codesize,charrange);
            System.out.print("The secret is prepared: ");
            int stars=codesize;
            while(stars>0){
                System.out.print("*");
                stars--;
            }
            System.out.print(" (0-9");
            if(charrange>10){
                System.out.print(", a");
                if(charrange>11)System.out.print("-"+(char)(charrange-10+96));
            }
            System.out.println(")");
            System.out.println("Okay, let's start a game!");
            int bulls = 0;
            int turns = 1;
            while (bulls < codesize) {
                int cows = 0;
                bulls = 0;
                System.out.println("Turn " + turns++ + ". Answer:");
                String inputcode = s.next();
                String gradeString = grader(secretcode, inputcode);
                bulls = Character.getNumericValue(gradeString.charAt(0));
                cows = Character.getNumericValue(gradeString.charAt(2));
                System.out.print("Grade: ");
                if (bulls == 0) {
                    if (cows == 0) {
                        System.out.println("None.");
                    } else if (cows == 1) {
                        System.out.println(cows + " cow.");
                    } else {
                        System.out.println(cows + " cows.");
                    }
                } else if (bulls == 1) {
                    if (cows == 0) {
                        System.out.println(bulls + " bull.");
                    } else if (cows == 1) {
                        System.out.println(bulls + " bull." + " and " + cows + " cow.");
                    } else {
                        System.out.println(bulls + " bull." + " and " + cows + " cows.");
                    }
                }else {
                    if (cows == 0) {
                        System.out.println(bulls + " bulls.");
                    } else if (cows == 1) {
                        System.out.println(bulls + " bulls." + " and " + cows + " cow.");
                    } else if (cows > 4) {
                        System.out.println(bulls + " bulls." + " and " + 4 + " cows.");
                    }else {
                        System.out.println(bulls + " bulls." + " and " + cows + " cows.");
                    }
                }
            }
            System.out.println(codesize + " bulls.");
            System.out.println("Congratulations! You guessed the secret code");
        }
    }

    private static String uniqueCharacterSecretCodeGenerator(int codesize, int charrange) {
        StringBuilder secretcode = new StringBuilder();
        HashSet<Integer> generatednumbersholder = new HashSet<>();
        while(codesize>0){
            int generatednumber;
            if(secretcode.length()==0){
                generatednumber = ((int) (Math.random() * (charrange-1)))+1;
            }
            else {
                generatednumber = (int) (Math.random() * charrange);
            }
            while(generatednumbersholder.contains(generatednumber)){
                generatednumber = (int) (Math.random() * charrange);
            }
            generatednumbersholder.add(generatednumber);
            char generatedcharacter;
            if(generatednumber>10){
                generatedcharacter = (char)(generatednumber-10+96);
            }else{
                generatedcharacter=Character.forDigit(generatednumber,10);
            }
            secretcode.append(generatedcharacter);
            codesize--;
        }
        return secretcode.toString();
    }

    private static String grader (String secretcode, String inputcode){
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < inputcode.length(); i++) {
            char inputchar = inputcode.charAt(i);
            for (int j = 0; j < secretcode.length(); j++) {
                char secretchar = secretcode.charAt(j);
                if (inputchar == secretchar) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        String s = bulls + "," + cows;
        return s;
    }
}

