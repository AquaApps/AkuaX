package fan.akua.io.velonio.strategy;

public class FixedSizeStrategy implements AllocStrategy {
    private final int size;

    public FixedSizeStrategy(int size) {
        this.size = size;
    }

    @Override
    public int AllocBufferByFileSize(long fileSize) {
        return size;
    }
}
