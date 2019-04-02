package mysql.ibdata.model;

import lombok.Data;
import mysql.reader.Reader;

/**
 * -------------------------------------------------------------
 * variable field length           1-2 bytes per var. field
 * nullable field bitmap           1bit per nullable field
 * info flags                      4bits    两个预留位   delete_mask min_rec_mask
 * number of records owned         4bits
 * order                           13bits
 * record type                     3bits
 * next record offset              2bytes
 * -------------------------------------------------------------
 */
@Data
public class UserRecord implements Reader<UserRecord> {



    @Override
    public UserRecord read(byte[] bytes, int offset) {
        return this;
    }
}
