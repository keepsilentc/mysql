package mysql.ibdata.enums;

public enum RecordTypeEnum {
    //0表示普通记录，1表示B+树非叶节点记录，2表示最小记录，3表示最大记录
    normal("普通记录", 0),
    not_leaf("B+树非叶节点记录", 1),
    min_leaf("最小记录", 2),
    max_leaf("最大记录", 3);

    private String desc;
    private int code;

    RecordTypeEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public static RecordTypeEnum findByCode(int code) {
        for (RecordTypeEnum recordTypeEnum : RecordTypeEnum.values()) {
            if (recordTypeEnum.code == code) {
                return recordTypeEnum;
            }
        }
        return null;
    }

}
