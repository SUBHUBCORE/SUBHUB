package io.subHub.common.xss;

import io.subHub.common.exception.ErrorCode;
import io.subHub.common.exception.RenException;
import org.apache.commons.lang3.StringUtils;

/**
 * SQL filterate
 * @author By
 */
public class SqlFilter {

    /**
     * SQL Injection filtration
     * @param str  The string to be verified
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //Remove the '|' |; | \ characters
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //Convert to lowercase
        str = str.toLowerCase();

        // Illegal characters
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //Determine if it contains illegal characters
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RenException(ErrorCode.INVALID_SYMBOL);
            }
        }

        return str;
    }
}
