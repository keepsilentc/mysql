package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.ByteReader;
import mysql.reader.Reader;

/**
 * file space header
 * 112字节
 * ----------------------------------------------------------------------
 * spaceId	                        4字节	    表空间的ID
 * notUsed                          4字节	    这4个字节未被使用，可以忽略
 * size	                            4字节	    当前表空间占有的页面数
 * freeLimit	                    4字节	    尚未被初始化的最小页号，大于或等于这个页号的区对应的XDES Entry结构都没有被加入FREE链表
 * spaceFlags	                    4字节	    表空间的一些占用存储空间比较小的属性
 * fragNUsed	                    4字节	    FREE_FRAG链表中已使用的页面数量
 * baseNodeForFreeList	            16字节	    FREE链表的基节点
 * baseNodeForFreeFragList	        16字节	    FREE_FREG链表的基节点
 * baseNodeForFullFragList	        16字节	    FULL_FREG链表的基节点
 * nextUnUsedSegmentId	            8字节	    当前表空间中下一个未使用的 Segment ID
 * baseNodeForSegInodesFullList	    16字节	    SEG_INODES_FULL链表的基节点
 * baseNodeForSegInodesFreeList	    16字节	    SEG_INODES_FREE链表的基节点
 * -----------------------------------------------------------------------
 */
@Data
public class FileSpaceHeader implements Reader<FileSpaceHeader> {

    private int spaceId;

    private int size;

    private int freeLimit;

    private int spaceFlags;

    private int fragNUsed;

    private ListBaseNode baseNodeForFreeList;

    private ListBaseNode baseNodeForFreeFragList;

    private ListBaseNode baseNodeForFullFragList;

    private long nextUnUsedSegmentId;

    private ListBaseNode baseNodeForSegInodesFullList;

    private ListBaseNode baseNodeForSegInodesFreeList;

    @Override
    public FileSpaceHeader read(byte[] bytes, int offset) {
        ByteReader byteReader = new ByteReader(bytes, offset);
        this.spaceId = byteReader.readInt();
        //4字节未使用
        byteReader.skip(4);
        this.size = byteReader.readInt();
        this.freeLimit = byteReader.readInt();
        this.spaceFlags = byteReader.readInt();
        this.fragNUsed = byteReader.readInt();
        this.baseNodeForFreeList = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        this.baseNodeForFreeFragList = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        this.baseNodeForFullFragList = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        this.nextUnUsedSegmentId = byteReader.readLong();
        this.baseNodeForSegInodesFullList = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        this.baseNodeForSegInodesFreeList = new ListBaseNode().read(bytes, offset + byteReader.getPosition());
        byteReader.skip(16);
        return this;
    }
}
