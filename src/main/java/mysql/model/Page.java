package mysql.model;

import lombok.Data;
import mysql.enums.PageTypeEnum;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

@Data
public class Page implements Reader<Page> {

    private FileHeader fileHeader;

    private FileBody fileBody;

    private FileTrailer fileTrailer;

    @Override
    public Page read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.fileHeader = new FileHeader().read(bytes, offset);

        switch (PageTypeEnum.findByCode(fileHeader.getPageType())) {
            case FSP_HDR:
                fileBody = new FspHdr();
                break;
            case INODE:
                fileBody = new INode();
                break;

        }
        if (fileBody != null) {
            fileBody.read(bytes, offset + byteReader.getPosition());
        }

        byteReader.skip(16 * 1024 - 38);
        this.fileTrailer = new FileTrailer().read(bytes, offset + byteReader.getPosition());
        return this;
    }
}
