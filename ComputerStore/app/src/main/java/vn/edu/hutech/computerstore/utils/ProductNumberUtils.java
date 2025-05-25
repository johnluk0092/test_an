package vn.edu.hutech.computerstore.utils;

import java.util.Locale;

public final class ProductNumberUtils {
    private ProductNumberUtils() {}

    public static String toCurrencyVND(long number) {
        return String.format(Locale.US, "%,d VND", number);
    }


    public static String toNumberWithThousandSplit(long number) {
        return String.format(Locale.US, "%,d", number);
    }

}
