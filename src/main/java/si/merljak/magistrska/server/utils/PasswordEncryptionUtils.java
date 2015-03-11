package si.merljak.magistrska.server.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utility class for basic password encryption. Based on code by Jerry Orr from article 
 * <a href="http://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html">Secure Password Storage – Don’ts, dos and a Java example</a>.
 * 
 * @author Jerry Orr, adopted by Jakob Merljak
 */
public class PasswordEncryptionUtils {
	// PBKDF2 with SHA-1 as the hashing algorithm. 
	// Note that the NIST specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	
	// SHA-1 generates 160 bit hashes, so that's what makes sense here
	private static final int DERIVED_KEY_LENGTH = 160;

	// Pick an iteration count that works for you. The NIST recommends at least 1,000 iterations:
	// http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
	// iOS 4.x reportedly uses 10,000:
	// http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
	private static final int ITERATIONS = 2500;

	/** 
	 * Checks if attempted password matches stored encrypted one.
	 *  
	 * @param attemptedPassword attempted password
	 * @param encryptedPassword stored encrypted password
	 * @param salt same salt that was used to encrypt the original password
	 * @return <em>true</em> if passwords match, <em>false</em> otherwise
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Encrypt the clear-text password using the same salt that was used to encrypt the original password
		byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

		// Authentication succeeds if encrypted password that the user entered
		// is equal to the stored hash
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	}

	/** 
	 * Calculates encrypted password from plain-text password and salt.
	 * 
	 * @param password plain-text password
	 * @param salt salt
	 * @return encrypted password
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, DERIVED_KEY_LENGTH);
		SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
		return f.generateSecret(spec).getEncoded();
	}

	/** Generates salt. */
	public static byte[] generateSalt() throws NoSuchAlgorithmException {
		// VERY important to use SecureRandom instead of just Random
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

		// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
		byte[] salt = new byte[8];
		random.nextBytes(salt);

		return salt;
	}
}
