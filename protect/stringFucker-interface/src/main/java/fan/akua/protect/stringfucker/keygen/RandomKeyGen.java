package fan.akua.protect.stringfucker.keygen;

import java.security.SecureRandom;

public class RandomKeyGen implements IKeyGen {
    private static final int DEFAULT_LENGTH = 8;

    private final SecureRandom mSecureRandom;
    private final int mLength;

    public RandomKeyGen() {
        this(DEFAULT_LENGTH);
    }

    public RandomKeyGen(int length) {
        mLength = length;
        mSecureRandom = new SecureRandom();
    }

    @Override
    public byte[] generate(String text) {
        byte[] key = new byte[mLength];
        mSecureRandom.nextBytes(key);
        return key;
    }

}
