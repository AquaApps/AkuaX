package fan.akua.stringFucker.algo;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import fan.akua.protect.stringfucker.IStringFucker;

public class ChaCha20 implements IStringFucker, Serializable {
    @Override
    public byte[] keygen(String data) {
        byte[] random = new byte[40];
        new SecureRandom().nextBytes(random);
        return random;
    }

    @Override
    public byte[] encrypt(String data, byte[] key) {
        byte[] first32 = Arrays.copyOfRange(key, 0, 32);
        byte[] last8 = Arrays.copyOfRange(key, 32, 40);
        return chacha20(data.getBytes(StandardCharsets.UTF_8), first32, last8);
    }

    @Override
    public String decrypt(byte[] data, byte[] key) {
        byte[] first32 = Arrays.copyOfRange(key, 0, 32);
        byte[] last8 = Arrays.copyOfRange(key, 32, 40);
        return new String(chacha20(data, first32, last8), StandardCharsets.UTF_8);
    }

    private static byte[] chacha20(byte[] value, byte[] key, byte[] nonce) {
        ChaChaEngine chaCha = new ChaChaEngine();
        ParametersWithIV params = new ParametersWithIV(new KeyParameter(key), nonce);
        chaCha.init(true, params);

        byte[] output = new byte[value.length];
        chaCha.processBytes(value, 0, value.length, output, 0);
        return output;
    }

}