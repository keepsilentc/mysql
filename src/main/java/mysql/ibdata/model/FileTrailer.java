package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * 8字节
 * ---------------------------------------
 * checkSum         4字节        页的校验和
 * low32LSN         4字节        页面被最后修改时对应的日志序列位置（LSN）
 * ---------------------------------------
 */
@Data
public class FileTrailer implements Reader<FileTrailer> {

    private int checkSum;

    private int low32LSN;

    @Override
    public FileTrailer read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.checkSum = byteReader.readInt();
        this.low32LSN = byteReader.readInt();
        return this;
    }
}
