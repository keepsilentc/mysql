package mysql.ibdata.enums;

import lombok.Getter;

@Getter
public enum ExtentStateEnum {
    FREE("空闲的区", 0),
    FREE_FRAG("有剩余空间的碎片区", 1),
    FULL_FRAG("没有剩余空间的碎片区", 2),
    FSEG("附属于某个段的区", 3);

    private String desc;
    private int code;

    ExtentStateEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public static ExtentStateEnum findByCode(int code) {
        for (ExtentStateEnum extentStateEnum : ExtentStateEnum.values()) {
            if (extentStateEnum.code == code) {
                return extentStateEnum;
            }
        }
        return null;
    }

}
