package fan.akua.io.velonio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import fan.akua.io.velonio.strategy.AllocStrategy;
import fan.akua.io.velonio.strategy.FuckingStrategy;

public final class VeloNIO {
    static AllocStrategy strategy = new FuckingStrategy();

    public static void setAllocStrategy(AllocStrategy allocStrategy) {
        strategy = allocStrategy;
    }

    public static void copyInputToOutput(final InputStream is, final OutputStream os) throws IOException {
        try (ReadableByteChannel inChannel = Channels.newChannel(is); WritableByteChannel outChannel = Channels.newChannel(os)) {
            final ByteBuffer buffer = ByteBuffer.allocate(65536);
            while (true) {
                int bytesRead = inChannel.read(buffer);
                if (bytesRead == -1) break;
                buffer.flip();
                while (buffer.hasRemaining()) outChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    public static void writeBytes(final byte[] bytes, final File file, boolean isAppend) throws IOException {
        VeloWriter.writeBytes(bytes, file, isAppend);
    }

    public static void writeBytes(final byte[] bytes, final File file) throws IOException {
        writeBytes(bytes, file, false);
    }

    public static void writeString(final String string, final File file) throws IOException {
        writeBytes(string.getBytes(), file, false);
    }

    public static void writeString(final String string, final File file, boolean isAppend) throws IOException {
        writeBytes(string.getBytes(), file, isAppend);
    }

    public static String readString(File file) throws IOException {
        byte[] bytes = readBytes(file);
        return new String(bytes);
    }

    public static byte[] readBytes(File file) throws IOException {
        return VeloReader.readBytes(file);
    }
}
