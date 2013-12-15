package helper;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Encryption {
	private static StandardPBEStringEncryptor strongEncryptor = null;
	
	private static void configureEncryptor() {
		strongEncryptor = new StandardPBEStringEncryptor();
        strongEncryptor.setPassword("mysuperpassword");
        strongEncryptor.setAlgorithm("PBEWITHMD5ANDDES");
	}
	
	public static StandardPBEStringEncryptor getEncryptor() {
		if(strongEncryptor == null) {
			configureEncryptor();
		}
		return strongEncryptor;
	}
	
}
