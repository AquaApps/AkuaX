package fan.akua.io.velonio;

import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.MalformedInputException;

final class VeloReader {
    static byte[] readBytes(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel channel = raf.getChannel();
        long size = channel.size();
        int bufferSize = VeloNIO.strategy.AllocBufferByFileSize(size);
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        byte[] bytes = new byte[(int) size];

        int chunk = (int) Math.ceil((double) size / (double) bufferSize);
        int remaining = (int) (size % bufferSize);

        for (int i = 0; i < chunk - 1; i++) {
            mappedByteBuffer.get(bytes, bufferSize * i, bufferSize);
        }
        if (remaining > 0) {
            mappedByteBuffer.get(bytes, bufferSize * (chunk - 1), remaining);
        }

        channel.close();
        raf.close();
        return bytes;
    }

}
