package mysql.model;

import lombok.Data;
import mysql.enums.PageTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 段信息节点
 * <p>
 * --------------------------------------------------
 * listNode             12字节
 * iNodeEntryList       16320(192*85)字节
 * empty_space          6字节
 * --------------------------------------------------
 */
@Data
public class INode extends FileBody<INode> {

    private ListNode listNode;

    private List<INodeEntry> iNodeEntryList;

    @Override
    public PageTypeEnum getPageType() {
        return PageTypeEnum.INODE;
    }

    @Override
    public INode read(byte[] bytes, int offset) {
        this.listNode = new ListNode().read(bytes, offset);
        List<INodeEntry> iNodeEntryList = new ArrayList<>(85);
        for (int i = 0; i < 85; i++) {
            iNodeEntryList.add(new INodeEntry().read(bytes, offset + 12 + 85 * i));
        }
        this.iNodeEntryList = iNodeEntryList;
        return this;
    }
}
