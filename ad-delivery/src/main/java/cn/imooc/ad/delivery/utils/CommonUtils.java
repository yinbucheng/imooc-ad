package cn.imooc.ad.delivery.utils;

import cn.imooc.ad.common.exception.AdException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

/**
 * Created by Qinyi.
 */
public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {

        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString)
            throws AdException {

        try {
            return DateUtils.parseDate(
                    dateString, parsePatterns
            );
        } catch (Exception ex) {
            throw new AdException(ex.getMessage());
        }
    }
}
