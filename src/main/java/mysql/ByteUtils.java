package mysql;

public class ByteUtils {
    public static long bytes2Long(byte[] bytes) {
        int i = 0;
        int int8 = (bytes[i++] & 0xff) << 56;
        int int7 = (bytes[i++] & 0xff) << 48;
        int int6 = (bytes[i++] & 0xff) << 40;
        int int5 = (bytes[i++] & 0xff) << 32;
        int int4 = (bytes[i++] & 0xff) << 24;
        int int3 = (bytes[i++] & 0xff) << 16;
        int int2 = (bytes[i++] & 0xff) << 8;
        int int1 = (bytes[i++] & 0xff);
        return int8 | int7 | int6 | int5 | int4 | int3 | int2 | int1;
    }

    public int read(byte[] bytes,int position) {
        return bytes[position] & 0xff;
    }

    public static int bytes2Int(byte[] bytes) {
        int i = 0;
        int int4 = (bytes[i++] & 0xff) << 24;
        int int3 = (bytes[i++] & 0xff) << 16;
        int int2 = (bytes[i++] & 0xff) << 8;
        int int1 = (bytes[i++] & 0xff);
        return int4 | int3 | int2 | int1;
    }

    public static short bytes2Short(byte[] bytes) {
        int i = 0;
        int int2 = (bytes[i++] & 0xff) << 8;
        int int1 = (bytes[i++] & 0xff);
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
