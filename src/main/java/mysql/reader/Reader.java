package mysql.reader;

public interface Reader<T extends Reader> {
    default T read(byte[] bytes) {
        return read(bytes, 0);
    }

    T read(byte[] bytes, int offset);
}
