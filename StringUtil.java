package myPackage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class StringUtil { 	//Result of Sha256 to string

	public static String useSHA256(String input){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(
  			input.getBytes(StandardCharsets.UTF_8));
				StringBuffer hexString = new StringBuffer();
		    for (int i = 0; i < encodedhash.length; i++) {
		    	String hex = Integer.toHexString(0xff & encodedhash[i]);
		    	if(hex.length() == 1) hexString.append('0');
		      	hexString.append(hex);
		    }
		   return hexString.toString();
		 }
		catch(Exception e) {
		throw new RuntimeException(e);
		}
	}
}
