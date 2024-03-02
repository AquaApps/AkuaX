package fan.akua.io.velonio.strategy;

public interface AllocStrategy {
    int AllocBufferByFileSize(long fileSize);
}
