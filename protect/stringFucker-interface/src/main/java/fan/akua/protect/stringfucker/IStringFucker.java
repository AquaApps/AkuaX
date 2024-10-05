package fan.akua.protect.stringfucker;

/**
 * Interface of how to encrypt and decrypt a string.
 *
 * @author Akua
 * @since 2024/10/01 18:16
 */
public interface IStringFucker {
    byte[] keygen(String data);

    /**
     * Encrypt the string by the special key.
     *
     * @param data The original string.
     * @param key  Special key.
     * @return The encrypted data.
     */
    byte[] encrypt(String data, byte[] key);

    /**
     * Decrypt the data by the special key.
     *
     * @param data The encrypted data.
     * @param key  Special key.
     * @return The original string.
     */
    String decrypt(byte[] data, byte[] key);
}
