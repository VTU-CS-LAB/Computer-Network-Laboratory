
/*
7. Write a program for error detecting code using CRC-CCITT (16- bits).
*/

import java.util.Scanner;

class CRC {

    static String xor(String a, String b) {

        StringBuilder stringBuilder = new StringBuilder();
        int len = Math.min(a.length(), b.length());
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) == b.charAt(i)) {
                stringBuilder.append('0');
            } else {
                stringBuilder.append('1');
            }
        }
        return stringBuilder.toString();
    }

    static String divide(String dividend, String divisor) {
        int divisorLength = divisor.length();
        int dividendLength = dividend.length();
        while (dividendLength >= divisorLength) {
            String temp;
            if (dividend.charAt(0) == '1')
                temp = xor(divisor, dividend.substring(0, divisorLength));
            else
                temp = dividend.substring(0, divisorLength);
            dividend = temp.substring(1) + dividend.substring(divisorLength);
            dividendLength -= 1;
        }
        return dividend;
    }

    static String generateCodeWord(String message, String generator) {
        int msgLength = message.length();
        int gtrLength = generator.length();
        // Right pad the message String to make total length as (msgLength+gtrLength-1)
        // Put the formatted String in new variable
        String dividend = String.format("%-" + (msgLength + gtrLength - 1) + "s", message).replace(' ', '0');

        String remainder = divide(dividend, generator);
        return message + remainder;
    }

    static boolean checkCodeWord(String codeword, String generator) {
        String temp = divide(codeword, generator);
        int len = temp.length();
        for (int i = 0; i < len; i++) {
            if (temp.charAt(i) == '1') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Generator String");
        String generator = scanner.next();

        while (true) {
            System.out.println("\nMenu");
            System.out.println("1. Generate Code Word");
            System.out.println("2. Check Code Word");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                System.out.println("Enter Message");
                String message = scanner.next();
                String result = generateCodeWord(message, generator);
                System.out.println("CodeWord: " + result);
                break;
            case 2:
                System.out.println("Enter Code Word");
                String codeWord = scanner.next();
                if (checkCodeWord(codeWord, generator)) {
                    System.out.println("Code Word is Valid");
                } else {
                    System.out.println("Code Word is Invalid");
                }
                break;
            case 3:
                System.exit(0);
            }
        }
    }
}

////////////
// Output
//
// Enter Generator String
// 10001000000100001

// Menu
// 1. Generate Code Word
// 2. Check Code Word
// 3. Exit
// 1
// Enter Message
// 1001
// CodeWord: 10011001000100101001

// Menu
// 1. Generate Code Word
// 2. Check Code Word
// 3. Exit
// 2
// Enter Code Word
// 10001000000100001
// Code Word is Valid

// Menu
// 1. Generate Code Word
// 2. Check Code Word
// 3. Exit
// 2
// Enter Code Word
// 10001000000100000
// Code Word is Invalid

// Menu
// 1. Generate Code Word
// 2. Check Code Word
// 3. Exit
// 3