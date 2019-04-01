package mysql;

public class ByteUtils {
    public static long bytes2Long(byte[] bytes) {
        int i = 0;
        int int8 = bytes[i++] << 56;
        int int7 = bytes[i++] << 48;
        int int6 = bytes[i++] << 40;
        int int5 = bytes[i++] << 32;
        int int4 = bytes[i++] << 24;
        int int3 = bytes[i++] << 16;
        int int2 = bytes[i++] << 8;
        int int1 = bytes[i++];
        return int8 | int7 | int6 | int5 | int4 | int3 | int2 | int1;
    }

    public static int bytes2Int(byte[] bytes) {
        int i = 0;
        int int4 = bytes[i++] << 24;
        int int3 = bytes[i++] << 16;
        int int2 = bytes[i++] << 8;
        int int1 = bytes[i++];
        return int4 | int3 | int2 | int1;
    }

    public static short bytes2Short(byte[] bytes) {
        int i = 0;
        int int2 = bytes[i++] << 8;
        int int1 = bytes[i++];
        return (short) (int2 | int1);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
