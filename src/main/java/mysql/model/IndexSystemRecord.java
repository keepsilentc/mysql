package mysql.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * info flags                   4bit
 * number of records owned      4bit
 * order                        13bit
 * record_type                  3bit
 * next record offset           2字节
 * infimum\0 或 supremum        8字节
 */
@Data
public class IndexSystemRecord implements Reader<IndexSystemRecord> {

    //预留位 没有使用
    //预留位 没有使用

    //标记该记录是否被删除
    private boolean deleteMask;
    //B+树的每层非叶子节点中的最小记录都会添加该标记
    private boolean minRecMask;


    //表示当前记录拥有的记录数
    // 4bit
    private short nOwned;

    //表示当前记录在记录堆的位置信息
    // 13bit
    private short heapNo;

    // 表示当前记录的类型，0表示普通记录，1表示B+树非叶节点记录，2表示最小记录，3表示最大记录
    // 3bit
    private short recordType;

    //表示下一条记录的相对位置
    private short nextRecord;

    private String data;

    @Override
    public IndexSystemRecord read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        byteReader.skip(2);
        int read = byteReader.read();
        this.recordType = (short) (read & 0x03);
        this.nextRecord = byteReader.readShort();
        this.data = byteReader.readString(8);
        return this;
    }
}
