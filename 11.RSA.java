
/*
11. Write a program for simple RSA algorithm to encrypt and decrypt the data.
*/

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

class RSA {

    static BigInteger p, q, n, phi_n, e, d;
    static SecureRandom secureRandom;
    static int bitLength = 64;

    static String encrypt(String msg) {
        return new BigInteger(msg.getBytes()).modPow(e, n).toString();
    }

    static String decrypt(String cipher) {
        BigInteger bi = new BigInteger(cipher).modPow(d, n);
        return new String(bi.toByteArray());
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        secureRandom = new SecureRandom();

        p = BigInteger.probablePrime(bitLength, secureRandom);
        q = BigInteger.probablePrime(bitLength, secureRandom);
        n = p.multiply(q);
        phi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitLength / 2, secureRandom);
        while (e.gcd(phi_n).compareTo(BigInteger.ONE) != 0 && e.compareTo(phi_n) < 0) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi_n);

        System.out.println("P assigned as: " + p);
        System.out.println("Q assigned as: " + q);
        System.out.println("N assigned as: " + n);
        System.out.println("PHI_N assigned as: " + phi_n);

        System.out.println("\nEnter Message");
        String msg = scanner.nextLine();

        String encryptedMessage = encrypt(msg);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String decryptedMessage = decrypt(encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);

    }
}

////////////
// Output
// P assigned as: 18331937448746061757
// Q assigned as: 14660384846134004581
// N assigned as: 268753257973873229081240359403146908817
// PHI_N assigned as: 268753257973873229048248037108266842480

// Enter Message
// Hello World
// Encrypted Message: 185814656462362097147843651345248330194
// Decrypted Message: Hello World