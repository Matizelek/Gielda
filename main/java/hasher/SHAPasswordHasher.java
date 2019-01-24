package hasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAPasswordHasher implements PasswordHasher {
    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest dig = MessageDigest.getInstance("SHA-256");
            byte[] bytes = dig.digest(password.getBytes(StandardCharsets.UTF_8));
            String hash = bytesToHex(bytes);
            return hash;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
