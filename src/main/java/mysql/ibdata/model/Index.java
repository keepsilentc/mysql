package mysql.ibdata.model;

import lombok.Data;
import mysql.ibdata.enums.PageTypeEnum;

/**
 * --------------------------------------------------
 * index_header        56字节
 * system_records      26字节
 * user_records        可变
 * free_space          可变
 * page_directory      可变
 * --------------------------------------------------
 */
@Data
public class Index extends FileBody<Index> {

    private IndexHeader indexHeader;

    private IndexSystemRecord infimum;

    private IndexSystemRecord supremum;

    private UserRecord userRecord;

    @Override
    public PageTypeEnum getPageType() {
        return PageTypeEnum.INDEX;
    }

    @Override
    public Index read(byte[] bytes, int offset) {
        this.indexHeader = new IndexHeader().read(bytes, offset);
        this.infimum = new IndexSystemRecord().read(bytes, offset + 56);
        this.supremum = new IndexSystemRecord().read(bytes, offset + 69);
        this.userRecord = new UserRecord().read(bytes, offset + 82);
        return this;
    }
}
