package mysql.redolog;

import java.io.FileInputStream;

import static mysql.constants.Constants.logBlockByte;

public class TestRedoLog {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/usr/local/ib_logfile0");
        long totalBlock = 0;
        byte[] bytes = new byte[logBlockByte];
        int position = 0;
        while (true) {
            int read = fileInputStream.read(bytes, position, logBlockByte - position);
            if (read == -1) {
                break;
            }
            if (read == logBlockByte - position) {
                RedoLogBlock logBlock = new RedoLogBlock(totalBlock).read(bytes, 0);
                System.out.println(logBlock.getLogBlockHeader().getHdrNo());
                System.out.println(logBlock.getLogBlockHeader().getHdrDataLength());
//                System.out.println(logBlock.getIndex());
                totalBlock += 1;
                position = 0;
            } else {
                position += read;
            }
        }
        System.out.println(totalBlock);
    }
}
