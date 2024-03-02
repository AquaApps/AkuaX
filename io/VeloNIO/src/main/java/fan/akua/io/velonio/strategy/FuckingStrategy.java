package fan.akua.io.velonio.strategy;

public class FuckingStrategy implements AllocStrategy {
    @Override
    public int AllocBufferByFileSize(long fileSize) {
        final int k = 1024;
        final int m = k * 1024;

        if (fileSize < m) {
            return 64 * k;
        } else if (fileSize < 9 * m) {
            return 128 * k;
        } else if (fileSize < 17 * m) {
            return 256 * k;
        } else if (fileSize < 33 * m) {
            return 512 * k;
        } else if (fileSize < 129 * m) {
            return 4 * m;
        } else {
            return 32 * m;
        }
    }
}
