package fan.akua.stringFucker.algo;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import fan.akua.protect.stringfucker.IStringFucker;

public class ChaCha20 implements IStringFucker, Serializable {
    private final byte[] nonce = new byte[12];

    public ChaCha20() {
        new SecureRandom().nextBytes(nonce);
    }

    @Override
    public byte[] encrypt(String data, byte[] key) {
        return chacha20(data.getBytes(StandardCharsets.UTF_8), key, nonce);
    }

    @Override
    public String decrypt(byte[] data, byte[] key) {
        return new String(chacha20(data, key, nonce), StandardCharsets.UTF_8);
    }

    @Override
    public boolean canFuck(String data) {
        return true;
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