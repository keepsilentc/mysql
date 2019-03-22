package mysql.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * 192字节
 * <p>
 * --------------------------------------------------
 * segmentId                    8字节             段编号
 * notFullNUsed                 4字节             not_full链表已经使用的页面，下次从NOT_FULL链表分配空闲页面时可以直接根据这个字段的值定位到。而不用从链表中的第一个页面开始遍历着寻找空闲页面
 * freeListBaseNode             16字节            FREE链表
 * notFullListBaseNode          16字节            NOT_FULL链表
 * fullListBaseNode             16字节            FULL链表
 * magicNumber                  4字节             这个值是用来标记这个INODE Entry是否已经被初始化了（初始化的意思就是把各个字段的值都填进去了）。如果这个数字是值的97937874，表明该INODE Entry已经初始化，否则没有被初始化。
 * fragmentArrayEntryList       128(32*4)字节     每个Fragment Array Entry结构都对应着一个零散的页面，这个结构一共4个字节，表示一个零散页面的页号。
 * --------------------------------------------------
 */
@Data
public class INodeEntry implements Reader<INodeEntry> {

    private long segmentId;

    private int notFullNUsed;

    private ListBaseNode freeListBaseNode;

    private ListBaseNode notFullListBaseNode;

    private ListBaseNode fullListBaseNode;

    private int magicNumber;

    private List<Integer> fragmentArrayEntryList;

    @Override
    public INodeEntry read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.segmentId = byteReader.readLong();
        this.notFullNUsed = byteReader.readInt();
        freeListBaseNode = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        notFullListBaseNode = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        fullListBaseNode = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        this.magicNumber = byteReader.readInt();
        List<Integer> fragmentArrayEntryList = new ArrayList<>(32);
        for (int i = 0; i < 32; i++) {
            fragmentArrayEntryList.add(byteReader.readInt());
        }
        this.fragmentArrayEntryList = fragmentArrayEntryList;
        return this;
    }
}
