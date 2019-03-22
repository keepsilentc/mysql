package mysql.model;

import lombok.Data;
import mysql.enums.PageTypeEnum;
import mysql.reader.Reader;

/**
 * 页体 16338字节
 * 根据PageType分为不同
 */
@Data
public abstract class FileBody<T extends FileBody> implements Reader<T> {

    public abstract PageTypeEnum getPageType();

}
