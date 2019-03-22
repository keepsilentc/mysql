package mysql.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

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
