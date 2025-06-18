package hotelreservationsystem.util;

import java.util.UUID;

public class UUIDUtil {
    public static String generateShortUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
} 