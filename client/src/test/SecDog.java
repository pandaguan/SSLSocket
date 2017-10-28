package test;/*
package test;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

*/
/**
 * Created by guan on 2017/6/28.
 */

class Sql2Log {
    
}
/*

public class SecDog {
    private final static String clientSeq = "clientSeq0001";
    private final static String serverSeq = "serverSeq0001";
    private final static String key = "588326419635109745";
    public boolean isIegal(String ciphertext, String rand, String serverSeq){
        System.out.println("解密信息："+serverSeq+key+rand);
        String temp = String.valueOf(DigestUtils.sha1Hex(serverSeq+key+rand));
        System.out.println(temp);
        if (temp.equals(ciphertext))
            return true;
        return false;
    }
    public String encrypt(String rand){
        System.out.println("加密信息："+serverSeq+key+rand);
        return String.valueOf(DigestUtils.sha1Hex(serverSeq+key+rand));
    }

    // 将字符串转换成二进制字符串，以空格相隔
    private String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        return result;
    }
    public String encryptDog(String rand){

        JavaSense4 sense4 = new JavaSense4();

        int [] size = new int[1];
        int ret = 0;
        byte lpInBuffer[] = null;
        if (lpInBuffer==null){
            lpInBuffer = rand.getBytes();
        }
        byte FileID[] = {0x00, 0x00, 0x00, 0x03};

        byte [] lpOutBuffer = new byte[40];
        int [] lpBytesReturned = {0};
        SENSE4_CONTEXT[] s4_context = new SENSE4_CONTEXT[0];

        ret = sense4.S4Enum(null, size);

        s4_context = new SENSE4_CONTEXT[size[0] / 92];
        for (int i = 0;i< size[0] / 92; i++)
        {
            s4_context[i] = new SENSE4_CONTEXT();
        }

        ret = sense4.S4Enum(s4_context, size);
        if (0 != ret)
            System.out.print("enumerate sense4 error");
        else
            System.out.println("enumerate sense4 success");

        S4OPENINFO s4_OpenInfo = new S4OPENINFO();
        s4_OpenInfo.dwS4OpenInfoSize = 8;
        s4_OpenInfo.dwShareMode = JavaSense4.S4_EXCLUSIZE_MODE;

        ret = sense4.S4OpenEx(s4_context[0], s4_OpenInfo);
        if (0 != ret)
            System.out.print("0pen sense4 error"+ret+"\n");
        else
            System.out.println("open sense4 success");

        byte [] frequency = {0x04};
        ret = sense4.S4Control(s4_context[0], JavaSense4.S4_LED_WINK, frequency, 1, null, 0, lpBytesReturned);
        if (0 != ret)
            System.out.print("sense4 LED wink error");
        else
            System.out.println("sense4 LED wink success");

        ret = sense4.S4VerifyPin(s4_context[0], ((String)"11111111").getBytes(), 8, JavaSense4.S4_USER_PIN);
        if (0 != ret)
            System.out.print("verify user pin error");
        else
            System.out.println("verify user pin success");

        ret = sense4.S4ExecuteEx(s4_context[0], FileID, JavaSense4.S4_VM_EXE, lpInBuffer, 18, lpOutBuffer, 60, lpBytesReturned);
        if (0 != ret)
            System.out.print("execute 0003 error");
        else
            System.out.println("execute 0003 success");

        ret = sense4.S4Control (s4_context[0], JavaSense4.S4_LED_DOWN, null, 0, null, 0, lpBytesReturned);
        if (0 != ret)
            System.out.print("LED down error");
        else
            System.out.println("LED down success");

        ret = sense4.S4Close(s4_context[0]);
        if (0 != ret)
            System.out.print("close sense4 error");
        else
            System.out.print("close sense4 success");

        return lpOutBuffer.toString();
    }
    public static void  main(String[] arg){
        Random random = new Random();
        System.out.println(random.nextLong());
        String rand = String.valueOf(random.nextLong());
        //System.out.println(rand.getBytes());
        SecDog secDog = new SecDog();
        */
/*String ciphertext = secDog.encrypt(rand);
        System.out.println(ciphertext);

        boolean flag = secDog.isIegal(ciphertext, rand, serverSeq);
        if (flag){
            System.out.println("服务端验证成功！");
        }else{
            System.out.println("服务端验证失败！");
        }
        *//*

        System.out.println("密文："+secDog.encryptDog(rand));
        //String ciphertext = DigestUtils.shaHex();
    }
}
*/
