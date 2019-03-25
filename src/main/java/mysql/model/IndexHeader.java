package mysql.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * 页面头部
 * 56字节
 * -------------------------------------------------------------------------------------
 * nDirSlot	        2字节	    在页目录中的槽数量
 * heapTop	        2字节	    还未使用的空间最小地址，也就是说从该地址之后就是Free Space
 * nHeap	        2字节	    本页中的记录的数量（包括最小和最大记录以及标记为删除的记录） 前15位表示行记录格式
 * free	            2字节	    第一个已经标记为删除的记录地址（各个已删除的记录通过next_record也会组成一个单链表，这个单链表中的记录可以被重新利用）
 * garbage	        2字节	    已删除记录占用的字节数
 * lastInsert	    2字节	    最后插入记录的位置
 * direction	    2字节	    记录插入的方向
 * nDirection	    2字节	    一个方向连续插入的记录数量
 * nRecords	        2字节	    该页中记录的数量（不包括最小和最大记录以及被标记为删除的记录）
 * maxTrxId	        8字节	    修改当前页的最大事务ID，该值仅在二级索引中定义
 * level	        2字节	    当前页在B+树中所处的层级
 * indexId	        8字节	    索引ID，表示当前页属于哪个索引
 *
 * btrSegLeaf	    10字节	    B+树叶子段的头部信息，仅在B+树的Root页定义
 * btrSegTop	    10字节	    B+树非叶子段的头部信息，仅在B+树的Root页定义
 * ----------------------------------------------------------------------------------------
 */
@Data
public class IndexHeader implements Reader<IndexHeader> {

    private short nDirSlot;

    private short heapTop;

    private short nHeap;

    private short free;

    private short garbage;

    private short lastInsert;

    private short direction;

    private short nDirection;

    private short nRecords;

    private long maxTrxId;

    private short level;

    private long indexId;

    private SegmentHeader btrSegLeaf;

    private SegmentHeader btrSegTop;

    @Override
    public IndexHeader read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.nDirSlot = byteReader.readShort();
        this.heapTop = byteReader.readShort();
        this.nHeap = byteReader.readShort();
        this.free = byteReader.readShort();
        this.garbage = byteReader.readShort();
        this.lastInsert = byteReader.readShort();
        this.direction = byteReader.readShort();
        this.nDirection = byteReader.readShort();
        this.nRecords = byteReader.readShort();
        this.maxTrxId = byteReader.readLong();
        this.level = byteReader.readShort();
        this.indexId = byteReader.readLong();
        this.btrSegLeaf = new SegmentHeader().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(10);
        this.btrSegTop = new SegmentHeader().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(10);
        return this;
    }

    /**
     * inode_entry
     * 10字节
     * ---------------------------------------------
     * spaceId      4字节         所属表空间id
     * pageNum      4字节         所在的页面页号
     * offSet       2字节         所在页面中的偏移量
     * ---------------------------------------------
     */
    @Data
    public class SegmentHeader implements Reader<SegmentHeader> {
        private int spaceId;
        private int pageNum;
        private short offSet;

        @Override
        public SegmentHeader read(byte[] bytes, int offset) {
            ByteReader byteReader = new ByteReader(bytes, offset);
            this.spaceId = byteReader.readInt();
            this.pageNum = byteReader.readInt();
            this.offSet = byteReader.readShort();
            return this;
        }
    }

}
