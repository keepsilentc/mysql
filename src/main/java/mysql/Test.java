package mysql;

import mysql.constants.Constants;
import mysql.ibdata.model.IndexHeader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        IbdataFileReader ibdataFileReader = new IbdataFileReader(new BaseFileReader(() -> {
            try {
                return new FileInputStream("/usr/local/test.ibd");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));

        ibdataFileReader.setPosition(3, 38);

        IndexHeader indexHeader = new IndexHeader().read(ibdataFileReader.read(56));
        short nDirSlot = indexHeader.getNDirSlot();

        ibdataFileReader.setPosition(3, Constants.pageByte - 8 - nDirSlot * 2);

        for (int i = 0; i < nDirSlot; i++) {
            ibdataFileReader.setPosition(3, Constants.pageByte - 8 - nDirSlot * 2 + i * 2);
            byte[] bytes = ibdataFileReader.read(2);
            short nextRecord = ByteUtils.bytes2Short(bytes);

            ibdataFileReader.setPosition(3, nextRecord);
            byte[] bytes1 = new byte[8];
            bytes1[0] = 0;
            System.arraycopy(ibdataFileReader.skip(1).read(7), 0, bytes1, 1, 7);
            System.out.println("-------------------");
            System.out.println("data : " + ByteUtils.bytes2Long(bytes1));
            System.out.println("page number : " + ByteUtils.bytes2Int(ibdataFileReader.read(4)));
        }

//        ibdataFileReader.skip(3);
//
//        while (true) {
//            System.out.println(ibdataFileReader.getOffset());
//            short nextRecord = ByteUtils.bytes2Short(ibdataFileReader.read(2));
//            if (nextRecord == 0) {
//                break;
//            }
//            ibdataFileReader.skip(nextRecord - 2);
//        }

//        int pageNumber = 4;
//        while (pageNumber != -1) {
//            ibdataFileReader.setPosition(pageNumber, 0);
//            FileHeader fileHeader = new FileHeader().read(ibdataFileReader.read(38), 0);
//            if (PageTypeEnum.INDEX != PageTypeEnum.findByCode(fileHeader.getPageType())) {
//                throw new RuntimeException("非索引页");
//            }
//            IndexHeader indexHeader = new IndexHeader().read(ibdataFileReader.read(56), 0);
//            System.out.println(indexHeader);
//            pageNumber = fileHeader.getNextPage();
//        }


    }
}
