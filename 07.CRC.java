
/*
7. Write a program for error detecting code using CRC-CCITT (16- bits).
*/

import java.util.Scanner;

class CRC {

    static String xor(String a, String b) {
        int len = Math.max(a.length(), b.length());
        int x = Integer.parseInt(a, 2);
        int y = Integer.parseInt(b, 2);
        String result = Integer.toBinaryString((x ^ y));
        // Left pad the result String with zeros to make total length as 'len'.
        result = String.format("%" + len + "s", result);
        return result.replace(' ', '0');
    }

    static String divide(String dividend, String divisor) {
        int divisorLength = divisor.length();
        int dividendLength = dividend.length();
        while (dividendLength >= divisorLength) {
            String temp;
            if (dividend.charAt(0) == '1')
                temp = xor(divisor, dividend.substring(0, divisorLength));
            else
                temp = xor("0", dividend.substring(0, divisorLength));
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
        return (Integer.parseInt(temp) == 0);
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