package mysql;

import lombok.Getter;
import mysql.constants.Constants;

import java.io.IOException;

@Getter
public class IbdataFileReader {

    private int pageNumber;
    private int offset;

    private final BaseFileReader baseFileReader;

    public IbdataFileReader(BaseFileReader baseFileReader) {
        this.baseFileReader = baseFileReader;
    }

    public IbdataFileReader setPosition(int pageNumber, int offset) throws IOException {
        this.pageNumber = pageNumber;
        this.offset = offset;
        return this;
    }

    public byte[] read(int length) throws IOException {
        byte[] bytes = baseFileReader.read(pageNumber * Constants.pageByte + offset, length);
        this.offset += length;
        return bytes;
    }

    public IbdataFileReader skip(int length) {
        this.offset += length;
        return this;
    }


}
