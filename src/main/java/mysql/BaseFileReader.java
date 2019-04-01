package mysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Supplier;

public class BaseFileReader {

    private final Supplier<FileInputStream> fileInputStreamSupplier;

    private FileInputStream fileInputStream;

    private int position;

    public BaseFileReader(Supplier<FileInputStream> fileInputStreamSupplier) {
        this.fileInputStreamSupplier = fileInputStreamSupplier;
    }

    public byte[] read(int position, int length) throws IOException {

        if (fileInputStream == null || this.position > position) {
            this.fileInputStream = this.fileInputStreamSupplier.get();
            this.position = 0;
        }
        long needSkip = position - this.position;
        long skip = fileInputStream.skip(position - this.position);
        if (skip != needSkip) {
            throw new IllegalArgumentException("无法skip到相应位置");
        }
        byte[] bytes = new byte[length];
        int read = fileInputStream.read(bytes);
        if (read == -1) {
            fileInputStream.available();
            throw new IllegalArgumentException("无法读取相应length数据 " + length);
        }
        this.position = position + length;
        return bytes;
    }


}
