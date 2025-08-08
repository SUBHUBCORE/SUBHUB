package io.subHub.common.utils;

import java.util.Random;

public class InviteCodeUtil {
    private static final String charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String numList = "0123456789";

    public static String getInviteCode(){
        String rev = "";
        int maxNumCount = 100;
        int length = 6;
        Random f = new Random();
        for (int i = 0; i < length; i++) {
            if (f.nextBoolean() && maxNumCount > 0) {
                maxNumCount--;
                rev += numList.charAt(Math.abs(f.nextInt()) % numList.length());
            } else {
                rev += charList.charAt(Math.abs(f.nextInt()) % charList.length());
            }
        }
        return rev;
    }

    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i=0;i<300;i++){
            String code = getInviteCode();
            System.out.println("INSERT INTO `invite_code_record` (`invite_code`, `use_status`, `use_user_id`, `claim_status`, `recipient_name`, `activation_dt`) VALUES ('"+code+"', 0, NULL, 1, 'lucas', NULL);");
        }

    }
}
