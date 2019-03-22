package mysql.reader;

import lombok.Getter;

@Getter
public class ByteReader {

    private final byte[] bytes;

    private final int offset;

    private int position;

    public ByteReader(byte[] bytes, int offset) {
        this.bytes = bytes;
        this.offset = offset;
    }

    public int read() {
        return bytes[offset + position++] & 0xff;
    }

    public ByteReader skip(int skipLength) {
        this.position += skipLength;
        return this;
    }

    public ByteReader setPosition(int position) {
        this.position = position;
        return this;
    }

    public long readLong() {
        int int8 = read() << 56;
        int int7 = read() << 48;
        int int6 = read() << 40;
        int int5 = read() << 32;
        int int4 = read() << 24;
        int int3 = read() << 16;
        int int2 = read() << 8;
        int int1 = read();
        return int8 | int7 | int6 | int5 | int4 | int3 | int2 | int1;
    }

    public int readInt() {
        int int4 = read() << 24;
        int int3 = read() << 16;
        int int2 = read() << 8;
        int int1 = read();
        return int4 | int3 | int2 | int1;
    }

    public short readShort() {
        int int2 = read() << 8;
        int int1 = read();
        return (short) (int2 | int1);
    }

    public String readString(int byteLength) {
        StringBuilder stringBuilder = new StringBuilder("");

        for (int i = 0; i < byteLength; i++) {
            int v = read();
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
