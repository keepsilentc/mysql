package mysql.ibdata;

import mysql.ibdata.model.FspHdr;
import mysql.ibdata.model.INode;
import mysql.ibdata.model.Index;
import mysql.ibdata.model.Page;
import mysql.reader.ByteReader;

import java.io.FileInputStream;

import static mysql.constants.Constants.pageByte;

public class Test {


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
                            if (page.getFileHeader().getSpaceId() != 0) {
//                                System.out.println((index.getIndexHeader().getNHeap() & 0x8000) >> 24);
//                                System.out.println(index.getInfimum().getNextRecord());
//                                System.out.println(index.getSupremum().getNextRecord());
                            }
                            break;
                    }
                }

//                if (page.getFileHeader().getSpaceId() != 0) {
//                    System.out.println("spaceId: " + page.getFileHeader().getSpaceId() + ", pageNum: " + page.getFileHeader().getPageNum());
//                    System.out.println(PageTypeEnum.findByCode(page.getFileHeader().getPageType()));
//                }

                totalPage += 1;
                position = 0;
            } else {
                position += read;
            }
        }
        System.out.println(totalPage);
    }


}
