package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Extent Descriptor Entry 用来记录区信息
 * 40 字节
 * ------------------------------------------------------
 * segment_id           8字节         该区所在的段
 * list_node            12字节        见ListNode
 * state                4字节         区的状态
 * page_state_bitmap    16字节        共128bit，顺序分给64个页，这两个比特位的第一个位表示对应的页是否是空闲的，第二个比特位还没有用
 * ------------------------------------------------------
 */
@Data
public class XDesEntry implements Reader<XDesEntry> {

    private long segmentId;

    private ListNode listNode;

    //区的状态 FREE、FREE_FRAG、FULL_FRAG和FSEG
    private int state;
    //16字节 128bit，划分给64页，每页2bit
    private List<Integer> pageStateBitMapList;

    @Override
    public XDesEntry read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.segmentId = byteReader.readLong();
        this.listNode = new ListNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(12);
        this.state = byteReader.readInt();
        List<Integer> pageStateBitMapList = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            pageStateBitMapList.add(byteReader.read());
            byteReader.skip(1);
        }
        this.pageStateBitMapList = pageStateBitMapList;
        return this;
    }
}
