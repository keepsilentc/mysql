package mysql;

import mysql.enums.PageTypeEnum;
import mysql.model.FspHdr;
import mysql.model.INode;
import mysql.model.Index;
import mysql.model.Page;

import java.io.FileInputStream;

public class Test {

    private static final int pageByte = 16 * 1024;

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/usr/local/ibdata1");
        long totalPage = 0;
        byte[] bytes = new byte[pageByte];
        int position = 0;
        while (true) {
            int read = fileInputStream.read(bytes, position, pageByte - position);
            if (read == -1) {
                break;
            }
            if (read == pageByte - position) {
                Page page = new Page().read(bytes, 0);
                if (page.getFileBody() != null) {
                    switch (page.getFileBody().getPageType()) {
                        case FSP_HDR:
                            FspHdr fspHdr = (FspHdr) page.getFileBody();
                            break;
                        case INODE:
                            INode iNode = (INode) page.getFileBody();
                            break;
                        case INDEX:
                            Index index = (Index) page.getFileBody();
//                            if (page.getFileHeader().getSpaceId() != 0) {
//                                System.out.println(index.getInfimum());
//                                System.out.println(index.getSupremum());
//                            }
                            break;
                    }
                }

                if (page.getFileHeader().getSpaceId() != 0) {
                    System.out.println("spaceId: " + page.getFileHeader().getSpaceId() + ", pageNum: " + page.getFileHeader().getPageNum());
                    System.out.println(PageTypeEnum.findByCode(page.getFileHeader().getPageType()));
                }

                totalPage += 1;
                position = 0;
            } else {
                position += read;
            }
        }
        System.out.println(totalPage);
    }


}
