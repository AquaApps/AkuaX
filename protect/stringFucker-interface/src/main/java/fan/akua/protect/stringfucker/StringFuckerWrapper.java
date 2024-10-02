package fan.akua.protect.stringfucker;

import java.lang.reflect.InvocationTargetException;

public final class StringFuckerWrapper implements IStringFucker {
    private final IStringFucker mStringFucker;

    public StringFuckerWrapper(Object implClass) {
//        try {
        mStringFucker = (IStringFucker) implClass;
//        } catch (InstantiationException e) {
//            throw new IllegalArgumentException("IStringFucker implementation class new instance failed: "
//                    + e.getMessage());
//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            throw new IllegalArgumentException("IStringFucker implementation class create instance failed: "
//                    + e.getMessage());
//        }
    }

    @Override
    public byte[] encrypt(String data, byte[] key) {
        return mStringFucker == null ? data.getBytes() : mStringFucker.encrypt(data, key);
    }

    @Override
    public String decrypt(byte[] data, byte[] key) {
        return mStringFucker == null ? new String(data) : mStringFucker.decrypt(data, key);
    }

    @Override
    public boolean canFuck(String str) {
        return mStringFucker != null && mStringFucker.canFuck(str);
    }
}
