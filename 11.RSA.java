
/*
11. Write a program for simple RSA algorithm to encrypt and decrypt the data.
*/

import java.util.Scanner;

class RSA {
    static int p, q;
    static long n, phi_n;

    static long gcd(long m, long n) {
        long c = m % n;
        if (c == 0) {
            return n;
        }
        return gcd(n, c);
    }

    static long calculate_e() {
        for (long i = 2; i < Long.MAX_VALUE; i++) {
            if (gcd(i, phi_n) == 1) {
                return i;
            }
        }
        return 0;
    }

    static long calculate_d(long e) {
        for (long i = 1; i < Long.MAX_VALUE; i++) {
            if ((e * i) % phi_n == 1) {
                return i;
            }
        }
        return 0;
    }

    static long encrypt(long m, long e) {
        return (long) (Math.pow(m, e) % n);
    }

    static long decrypt(long c, long d) {
        return (long) (Math.pow(c, d) % n);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter two prime numbers");
        p = scanner.nextInt();
        q = scanner.nextInt();

        n = p * q;
        phi_n = (p - 1) * (q - 1);

        long e = calculate_e();

        if (e == 0) {
            System.out.println("Value out of range");
            System.exit(0);
        }

        long d = calculate_d(e);

        long m, c;
        while (true) {
            System.out.println("\n1. Encrypt\n2. Decrypt\n3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
            case 1:
                System.out.println("Enter Message");
                m = scanner.nextLong();
                c = encrypt(m, e);
                System.out.println("Encrypted Message: " + c);
                break;
            case 2:
                System.out.println("Enter Cipher");
                c = scanner.nextLong();
                m = decrypt(c, d);
                System.out.println("Decrypted Message: " + m);
                break;
            case 3:
                System.exit(0);
            }
        }
    }
}

/////////
// Output
// Enter two prime numbers
// 3 11
// 1. Encrypt
// 2. Decrypt
// 3. Exit
// 1
// Enter Message
// 9
// Encrypted Message: 3
// 1. Encrypt
// 2. Decrypt
// 3. Exit
// 2
// Enter Cipher
// 3
// Decrypted Message: 9
// 1. Encrypt
// 2. Decrypt
// 3. Exit
// 3
