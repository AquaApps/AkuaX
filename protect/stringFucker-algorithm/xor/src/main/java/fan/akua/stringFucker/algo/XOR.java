package fan.akua.stringFucker.algo;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import fan.akua.protect.stringfucker.IStringFucker;

public class XOR implements IStringFucker, Serializable {
    @Override
    public byte[] keygen(String data) {
        byte[] random = new byte[32];
        new SecureRandom().nextBytes(random);
        return random;
    }

    @Override
    public byte[] encrypt(String data, byte[] key) {
        return xor(data.getBytes(StandardCharsets.UTF_8), key);
    }

    @Override
    public String decrypt(byte[] data, byte[] key) {
        return new String(xor(data, key), StandardCharsets.UTF_8);
    }

    private static byte[] xor(byte[] data, byte[] key) {
        int len = data.length;
        int lenKey = key.length;
        int i = 0;
        int j = 0;
        while (i < len) {
            if (j >= lenKey) {
                j = 0;
            }
            data[i] = (byte) (data[i] ^ key[j]);
            i++;
            j++;
        }
        return data;
    }

}