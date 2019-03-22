package mysql.model;

import lombok.Data;
import mysql.enums.PageTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展描述页
 * 对于 FSP_HDR 的扩展补充
 * ------------------------------------------
 * 无                    112字节
 * xDesEntryList        10240(256*40) 字节
 * empty_space          5968字节
 * -------------------------------------------
 */
@Data
public class XDes extends FileBody<XDes> {

    private List<XDesEntry> xDesEntryList;

    @Override
    public PageTypeEnum getPageType() {
        return PageTypeEnum.XDES;
    }

    @Override
    public XDes read(byte[] bytes, int offset) {
        List<XDesEntry> xDesEntryList = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            xDesEntryList.add(new XDesEntry().read(bytes, 112 + i * 40));
        }
        this.xDesEntryList = xDesEntryList;
        return this;
    }
}
