package mysql.model;

import lombok.Data;

@Data
public class RecordHeader {

    //预留位 没有使用
    // 1bit

    //预留位 没有使用
    // 1bit

    //标记该记录是否被删除
    // 1bit
    private boolean deleteMask;
    //B+树的每层非叶子节点中的最小记录都会添加该标记
    // 1bit
    private boolean minRecMask;
    //表示当前记录拥有的记录数
    // 4bit
    private short nOwned;
    //表示当前记录在记录堆的位置信息
    // 13bit
    private short heapNo;
    // 表示当前记录的类型，0表示普通记录，1表示B+树非叶节点记录，2表示最小记录，3表示最大记录
    // 3bit
    private short recordType;
    //表示下一条记录的相对位置
    private short nextRecord;
}
