import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

class RSA {

    static BigInteger p, q, n, phi_n, e, d;
    static SecureRandom secureRandom;
    static int bitLength = 50;


    static String encrypt(String msg) {
        return new BigInteger(msg).modPow(e, n).toString();
    }

    static String decrypt(String cipher) {
        return new BigInteger(cipher).modPow(d, n).toString();
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
        String msg = scanner.next();

        String encryptedMessage = encrypt(msg);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String decryptedMessage = decrypt(encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);

    }
}

////////////
//    Output
//    P assigned as: 635469119130037
//    Q assigned as: 954033057154061
//    N assigned as: 606258546450627387595401630257
//    PHI_N assigned as: 606258546450625798093225346160
//
//    Enter Message
//    45465
//    Encrypted Message: 581551797190538649820014908031
//    Decrypted Message: 45465