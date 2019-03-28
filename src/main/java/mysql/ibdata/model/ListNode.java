package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * list_node
 * 12字节
 * -----------------------------------------------------------
 * prevNodePageNum         4字节         前一个XDES entry的指针
 * preNodeOffset           2字节         前一个XDES entry的指针
 * nextNodePageNum         4字节         后一个XDES entry的指针
 * nextNodeOffset          2字节         后一个XDES entry的指针
 * -----------------------------------------------------------
 */
@Data
public class ListNode implements Reader<ListNode> {

    private int prevNodePageNum;

    private short preNodeOffset;

    private int nextNodePageNum;

    private short nextNodeOffset;

    @Override
    public ListNode read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.prevNodePageNum = byteReader.readInt();
        this.preNodeOffset = byteReader.readShort();
        this.nextNodePageNum = byteReader.readInt();
        this.nextNodeOffset = byteReader.readShort();
        return this;
    }
}
