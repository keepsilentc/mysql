package mysql.ibdata;

import mysql.ibdata.model.*;

import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static mysql.constants.Constants.pageByte;

public class Test {


    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/usr/local/test.ibd");
        long totalPage = 0;
        byte[] bytes = new byte[pageByte];
        int position = 0;

        Map<Integer, Page> pageMap = new LinkedHashMap<>();

        while (true) {
            int read = fileInputStream.read(bytes, position, pageByte - position);
            if (read <= 0) {
                break;
            }
            if (read == pageByte - position) {
                Page page = new Page().read(bytes, 0);
                if (page.getFileBody() != null) {
                    switch (page.getFileBody().getPageType()) {
                        case FSP_HDR:
                            FspHdr fspHdr = (FspHdr) page.getFileBody();
                            System.out.println(fspHdr.getFileSpaceHeader());
                            List<XDesEntry> xDesEntryList = fspHdr.getXDesEntryList();
                            xDesEntryList.forEach(t -> System.out.println("index : " + t.getIndex() + ", state : " + t.getState() + ", segment_id : " + t.getSegmentId()));
                            break;
                        case XDES:
                            XDes xDes = (XDes) page.getFileBody();
                            break;
                        case INODE:
                            INode iNode = (INode) page.getFileBody();
//                            System.out.println("inode page number: " + page.getFileHeader().getPageNum());
//
//                            for (INodeEntry iNodeEntry : iNode.getINodeEntryList()) {
//                                System.out.println("------------------------");
//                                System.out.println("magic number: " + iNodeEntry.getMagicNumber());
//                                System.out.println("segment id: " + iNodeEntry.getSegmentId());
//                                System.out.println("fragment array: " + iNodeEntry.getFragmentArrayEntryList().stream().map(String::valueOf).collect(Collectors.joining("->")));
//                                System.out.println("not full used : " + iNodeEntry.getNotFullNUsed());
//                                System.out.println("free list base node : " + iNodeEntry.getFreeListBaseNode());
//                                System.out.println("not full list base node : " + iNodeEntry.getNotFullListBaseNode());
//                                System.out.println("full list base node : " + iNodeEntry.getFullListBaseNode());
//                            }
                            break;
                        case INDEX:
                            FileHeader fileHeader = page.getFileHeader();
                            pageMap.put(page.getFileHeader().getPageNum(), page);
                            Index index = (Index) page.getFileBody();


                            break;
                    }
                }

                System.out.println(page.getFileHeader().getPageNum());


//                    System.out.println(PageTypeEnum.findByCode(page.getFileHeader().getPageType()));

                totalPage += 1;
                position = 0;
                bytes = new byte[pageByte];
            } else {
                position += read;
            }
        }

        System.out.println(totalPage);
    }


}
