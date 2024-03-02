package fan.akua.io.velonio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

final class VeloWriter {
    static void writeBytes(final byte[] bytes, final File file, boolean isAppend) throws IOException {
        if (!isAppend) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(raf.length());
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbf = fc.map(FileChannel.MapMode.READ_WRITE, fc.position(), bytes.length);
        fc.close();
        mbf.put(bytes);
    }

}
