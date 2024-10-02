package fan.akua.protect.stringfucker.keygen;

public interface IKeyGen {
    /**
     * Generate a security key.
     *
     * @param text The content text will be encrypted.
     * @return A security key for the encryption.
     */
    byte[] generate(String text);
}
