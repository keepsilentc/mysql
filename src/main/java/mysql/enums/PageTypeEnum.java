package mysql.enums;

import lombok.Getter;

@Getter
public enum PageTypeEnum {
    ALLOCATED("最新分配，还没使用", Short.valueOf("0000", 16)),
    UNDO_LOG("Undo日志页", Short.valueOf("0002", 16)),
    INODE("段信息节点", Short.valueOf("0003", 16)),
    IBUF_FREE_LIST("Insert Buffer空闲列表", Short.valueOf("0004", 16)),
    IBUF_BITMAP("Insert Buffer位图", Short.valueOf("0005", 16)),
    SYS("系统页", Short.valueOf("0006", 16)),
    TRX_SYS("事务系统数据", Short.valueOf("0007", 16)),
    FSP_HDR("表空间头部信息", Short.valueOf("0008", 16)),
    XDES("扩展描述页", Short.valueOf("0009", 16)),
    BLOB("BLOB页", Short.valueOf("000A", 16)),
    INDEX("索引页&数据页", Short.valueOf("45BF", 16));

    private String desc;
    private short code;

    PageTypeEnum(String desc, short code) {
        this.desc = desc;
        this.code = code;
    }

    public static PageTypeEnum findByCode(short code) {
        for (PageTypeEnum pageTypeEnum : PageTypeEnum.values()) {
            if (pageTypeEnum.code == code) {
                return pageTypeEnum;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.desc;
    }
}
