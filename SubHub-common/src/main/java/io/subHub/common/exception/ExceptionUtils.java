package io.subHub.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception Tool class
 *
 * @author By
 */
public class ExceptionUtils {

    /**
     * Get abnormal information
     * @param ex
     * @return    Return abnormal information
     */
    public static String getErrorStackTrace(Exception ex){
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw, true);
            ex.printStackTrace(pw);
        }finally {
            try {
                if(pw != null) {
                    pw.close();
                }
            } catch (Exception e) {

            }
            try {
                if(sw != null) {
                    sw.close();
                }
            } catch (IOException e) {

            }
        }

        return sw.toString();
    }
}
