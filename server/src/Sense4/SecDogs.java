package Sense4;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by guan on 2017/7/2.
 */
public class SecDogs
{
	//加密锁序列号
    private static String dogSeq;
    //加密锁普通用户PIN码
    private static String userPin ;
    //加密文件
    private static byte encryFileID[] = new byte[5];
    //验证文件
    private static byte authFileID[] = new byte[5];
    //加载日志信息
	private static Logger logger;
    
    SENSE4_CONTEXT[] s4_context = new SENSE4_CONTEXT[0];
    public static void init(){
    	//加载日志
    	logger = Logger.getLogger("SecDogs");
        InputStream inputStream = SecDogs.class.getResourceAsStream("/secDog.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            dogSeq = properties.getProperty("dogSeq");
            userPin = properties.getProperty("userPin");

            String []encryFileName = properties.getProperty("encryFileID").split(",");
            for (int i=0; i<encryFileName.length; i++){
                encryFileID[i]= Byte.parseByte(encryFileName[i]);
            }

            String []authFileName = properties.getProperty("authFileID").split(",");
            for (int j=0; j<authFileName.length; j++){
                authFileID[j]= Byte.parseByte(authFileName[j]);
            }
        }
        catch (Exception e) {
            logger.warning(e+"不能读取secDog.properties配置文件." );
        }
    }
    private static String genRandomNum(int num) {
        final int maxNum = 10;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        while (count < num) {
        // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为10-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
    public String encryptDog(String rand){
        String encrypt = null;
        JavaSense4 sense4 = new JavaSense4();

        int [] size = new int[1];
        int ret = 0;
        byte lpInBuffer[] = rand.getBytes();

//        logger.info("输入数据："+rand);
        byte [] lpOutBuffer = new byte[20];
        int [] lpBytesReturned ={0};
        //SENSE4_CONTEXT[] s4_context = new SENSE4_CONTEXT[0];

        ret = sense4.S4Enum(null, size);

        s4_context = new SENSE4_CONTEXT[size[0] / 96];
        for (int i = 0;i< size[0] / 96; i++)
        {
            s4_context[i] = new SENSE4_CONTEXT();
        }
        ret = sense4.S4Enum(s4_context, size);
       /* if (0 != ret)
            System.out.print("enumerate sense4 error");
        else
            System.out.println("enumerate sense4 success");*/
        //指定加密锁
        for (int i=0; i<s4_context.length; i++){
            if ((new String(s4_context[i].bID)).trim().equals(dogSeq)){
            	//System.out.println("设备ID："+new String(s4_context[i].bID));
                ret = sense4.S4Open(s4_context[i]);
               /* if (0 != ret)
                    System.out.print("open sense4 error");
                else
                    System.out.println("open sense4 success");*/

                //ret = sense4.S4VerifyPin(s4_context[i], ((String)"123456781234567812345678").getBytes(), 24, JavaSense4.S4_DEV_PIN);
               /* if (0 != ret){
                    System.out.print("verify dev pin error");
                    sense4.S4Close(s4_context[i]);
                }
                else
                    System.out.println("verify dev pin success");*/

                ret = sense4.S4VerifyPin(s4_context[i], userPin.getBytes(),  userPin.length(), JavaSense4.S4_USER_PIN);
                /*if (0 != ret){
                    System.out.print("verify user pin error");
                    sense4.S4Close(s4_context[i]);
                }
                else
                    System.out.println("verify user pin success");*/
                //执行程序内代码

                ret = sense4.S4ExecuteEx(s4_context[i], encryFileID, JavaSense4.S4_VM_EXE ,lpInBuffer,lpInBuffer.length, lpOutBuffer, lpOutBuffer.length, lpBytesReturned);
                if (0 != ret){
//                    logger.warning("execute hex file failed!----------------"+ret);
                    sense4.S4Close(s4_context[i]);
                }
                else{
//                   logger.info("execute hex file successed!");
                    //将密文返回
                    //System.out.print("输出加密数据：");
                    for (int j=0; j<lpOutBuffer.length; j++){
                        if (encrypt==null){
                            encrypt= String.valueOf(lpOutBuffer[0])+",";
                        }else{
                            encrypt+=String.valueOf(lpOutBuffer[j])+",";
                        }
                    }
                   // System.out.println(encrypt);
                    //encrypt = new String(lpOutBuffer);
                    sense4.S4Close(s4_context[i]);
                    break;
                }
            }
        }
        return encrypt;
    }
    public boolean clientAuth(String clientEncrypt){
       
        JavaSense4 sense4 = new JavaSense4();

        int [] size = new int[1];
        int ret = 0;
        //byte lpInBuffer[] = clientEncrypt.getBytes();
        String encrypt[] = clientEncrypt.split(",");
        byte lpInBuffer[] = new byte[45];
        for (int i=0; i<encrypt.length; i++){
            if (i<20){
                lpInBuffer[i] = Byte.parseByte(encrypt[i]);
            }else {
                byte temp[] = encrypt[i].getBytes();
                for (int j=0; j<temp.length; j++){
                    lpInBuffer[i+j] = temp[j];
                }
            }
        }
        //System.out.println("输入数据："+clientEncrypt);
        byte [] lpOutBuffer = new byte[20];
        int [] lpBytesReturned ={0};
        //SENSE4_CONTEXT[] s4_context = new SENSE4_CONTEXT[0];

        ret = sense4.S4Enum(null, size);

        s4_context = new SENSE4_CONTEXT[size[0] / 92];
        for (int i = 0;i< size[0] / 92; i++)
        {
            s4_context[i] = new SENSE4_CONTEXT();
        }
        ret = sense4.S4Enum(s4_context, size);
       /* if (0 != ret)
            System.out.print("enumerate sense4 error");
        else
            System.out.println("enumerate sense4 success");*/
        //指定加密锁
        for (int i=0; i<s4_context.length; i++){
            if ((new String(s4_context[i].bID)).trim().equals(dogSeq)){
            	//System.out.println("设备ID："+new String(s4_context[i].bID));
                ret = sense4.S4Open(s4_context[i]);
               /* if (0 != ret)
                    System.out.print("open sense4 error");
                else
                    System.out.println("open sense4 success");*/

                //ret = sense4.S4VerifyPin(s4_context[i], ((String)"123456781234567812345678").getBytes(), 24, JavaSense4.S4_DEV_PIN);
                /*if (0 != ret){
                    System.out.print("verify dev pin error");
                    sense4.S4Close(s4_context[i]);
                }
                else
                    System.out.println("verify dev pin success");*/

                ret = sense4.S4VerifyPin(s4_context[i], userPin.getBytes(),   userPin.length(), JavaSense4.S4_USER_PIN);
               /* if (0 != ret){
                    System.out.print("verify user pin error");
                    sense4.S4Close(s4_context[i]);
                }
                else
                    System.out.println("verify user pin success");*/
                //执行程序内代码

                ret = sense4.S4ExecuteEx(s4_context[i], authFileID,  JavaSense4.S4_VM_EXE,lpInBuffer,lpInBuffer.length, lpOutBuffer, lpOutBuffer.length, lpBytesReturned);
                if (0 != ret){
                    logger.warning("execute hex file failed!------------------------"+ret);
                    sense4.S4Close(s4_context[i]);
                }
                else{
                    logger.info("execute hex file successed!");
                    logger.info("返回验证结果："+lpOutBuffer[0]);
                    sense4.S4Close(s4_context[i]);
                    if (lpOutBuffer[0]==0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void  main(String[] arg) throws UnsupportedEncodingException {
        Random random = new Random();
        Runnable clientRun = new Runnable() {
            @Override
            public void run() {
            	
            	String rand = genRandomNum(20);
            	SecDogs secDog = new SecDogs();
            	// System.out.println("密文："+secDog.encryptDog(rand));
            	try{
            		
            		String encrypt = secDog.encryptDog(rand);
            		secDog.clientAuth(encrypt+"S0001"+rand);
            	}catch(Exception e){
            		System.out.println(e);
            	}
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(clientRun, 0, 1, TimeUnit.SECONDS);
    }

}


