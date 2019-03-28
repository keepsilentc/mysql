package mysql.redolog;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

@Data
public class RedoLogBlock implements Reader<RedoLogBlock> {

    private final long index;

    public RedoLogBlock(long index) {
        this.index = index;
    }

    private RedoLogBlockHeader logBlockHeader;

    private long logBlockBodyOffset;

    private RedoLogBlockTrailer logBlockTrailer;

    @Override
    public RedoLogBlock read(byte[] bytes, int offset) {
        this.logBlockHeader = new RedoLogBlockHeader().read(bytes, offset);
        this.logBlockBodyOffset = index * 512 + 12;
        this.logBlockTrailer = new RedoLogBlockTrailer().read(bytes, offset + 512 - 4);
        return this;
    }


    @Data
    public static class RedoLogBlockHeader implements Reader<RedoLogBlockHeader> {
        private int hdrNo;
        private short hdrDataLength;
        private short firstRecGroup;
        private int checkpointNo;

        @Override
        public RedoLogBlockHeader read(byte[] bytes, int offset) {
            ByteReader byteReader = new ByteReader(bytes, offset);
            this.hdrNo = byteReader.readInt();
            this.hdrDataLength = byteReader.readShort();
            this.firstRecGroup = byteReader.readShort();
            this.checkpointNo = byteReader.readInt();
            return this;
        }
    }

    @Data
    public static class RedoLogBlockTrailer implements Reader<RedoLogBlockTrailer> {
        private int checkSum;

        @Override
        public RedoLogBlockTrailer read(byte[] bytes, int offset) {
            ByteReader byteReader = new ByteReader(bytes, offset);
            this.checkSum = byteReader.readInt();
            return this;
        }
    }
}
