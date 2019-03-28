package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * 页头
 * 38字节
 * ----------------------------
 * checkSum			4字节	页的校验和（checksum值）
 * pageNum			4字节	页号
 * previousPage		4字节	上一个页的页号
 * nextPage			4字节	下一个页的页号
 * lastModifyLSN    8字节	页面被最后修改时对应的日志序列位置（英文名是：Log Sequence Number）
 * pageType			2字节	该页的类型
 * flushLSN			8字节	仅在系统表空间的一个页中定义，代表文件至少被刷新到了对应的LSN值
 * spaceId			4字节	页属于哪个表空间
 * ----------------------------
 */
@Data
public class FileHeader implements Reader<FileHeader> {

    private int checkSum;

    private int pageNum;

    private int previousPage;

    private int nextPage;

    private long lastModifyLSN;

    private short pageType;

    private long flushLSN;

    private int spaceId;

    @Override
    public FileHeader read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.checkSum = byteReader.readInt();
        this.pageNum = byteReader.readInt();
        this.previousPage = byteReader.readInt();
        this.nextPage = byteReader.readInt();
        this.lastModifyLSN = byteReader.readLong();
        this.pageType = byteReader.readShort();
        this.flushLSN = byteReader.readLong();
        this.spaceId = byteReader.readInt();
        return this;
    }
}
