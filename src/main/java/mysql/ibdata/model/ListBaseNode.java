package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * list_length                 4字节     该链表共多少节点
 * fist_node_page_number       4字节     链表头节点所在页号
 * fist_node_offset            2字节     链表头节点所在页的页内偏移量
 * last_node_page_number       4字节     链表尾节点所在页号
 * last_node_offset            2字节     链表尾节点所在页的页内偏移量
 */
@Data
public class ListBaseNode implements Reader<ListBaseNode> {

    private int listLength;

    private int fistNodePageNum;

    private short firstNodeOffset;

    private int lastNodePageNum;

    private short lastNodeOffset;

    @Override
    public ListBaseNode read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.listLength = byteReader.readInt();
        this.fistNodePageNum = byteReader.readInt();
        this.firstNodeOffset = byteReader.readShort();
        this.lastNodePageNum = byteReader.readInt();
        this.lastNodeOffset = byteReader.readShort();
        return this;
    }
}