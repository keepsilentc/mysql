package mysql.reader;

public interface Reader<T extends Reader> {
    T read(byte[] bytes, int offset);
}
