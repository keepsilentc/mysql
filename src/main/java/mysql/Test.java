package mysql;

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

        ibdataFileReader.setPosition(3, 38 + 56);
        ibdataFileReader.skip(3);

        while (true) {
            System.out.println(ibdataFileReader.getOffset());
            short nextRecord = ByteUtils.bytes2Short(ibdataFileReader.read(2));
            if (nextRecord == 0) {
                break;
            }
            ibdataFileReader.skip(nextRecord - 2);
        }

        ibdataFileReader.setPosition(3,110);

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
