package mysql.ibdata.model;

import lombok.Data;
import mysql.ibdata.enums.PageTypeEnum;

import java.util.ArrayList;
import java.util.List;


/**
 * 表空间头部信息
 * --------------------------------
 * fileSpaceHeader     112字节
 * xDesEntryList       10240(256*40) 字节
 * empty_space         5986字节
 * --------------------------------
 */
@Data
public class FspHdr extends FileBody<FspHdr> {

    private FileSpaceHeader fileSpaceHeader;

    private List<XDesEntry> xDesEntryList;

    @Override
    public FspHdr read(byte[] bytes, int offset) {
        this.fileSpaceHeader = new FileSpaceHeader().read(bytes, offset);
        List<XDesEntry> xDesEntryList = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            xDesEntryList.add(new XDesEntry(i).read(bytes, offset + 112 + i * 40));
        }
        this.xDesEntryList = xDesEntryList;
        return this;
    }

    @Override
    public PageTypeEnum getPageType() {
        return PageTypeEnum.FSP_HDR;
    }
}
