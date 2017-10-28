package client2;


import Sense4.SecDogs;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by guan on 2017/6/6.
 */
public class ClientCode2{
    //服务端地址
    private static final String addrIp = "10.10.13.13";
    //端口号
    private static final int portNo = 3333;
    //客户端私钥库的密钥
    private static final String CLIENT_KEY_STORE_PASSWORD       = "123456";
    //CA根证书库的密钥
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";

    private SSLSocket sslClientSocket;
    //客户端序列号
    private static final String Client_seq = "C0001";
    //记录双向认证出错次数
    private static int errorNum = 1;
    //上传文件路径
   /* private static final String filePath= "E:\\文件传输\\发送\\";
    //客户端序列号
    private static final String Lab_seq = "clientSequenceNumber";
    //UKey中存放的客户端序列号
    private static String UKLab_seq = null;
    //UKey中存放的客户端关键字
    private static String ukey = null;
    //客户端盐值，用于MD5加密
    private static String Lab_salt = null;
    //客户端随机数，用于传输数据加密
    private static String Lab_rand = null;*/

    /*
     * 服务端传输过来的数据
     */
   /* //服务端传送过来的序列号
    private String Ser_seq = null;
    //服务端传送过来的盐值
    private String Ser_salt = null;
    //服务端传送过来的随机数
    private String Ser_rand = null;*/
   /* private Socket client;
    public ClientCode() throws IOException {
        super(addrIp, portNo);
        this.client = this;
    }*/
    /**
     * UKey认证
     */
    /*private static String ukeyAuth(){
        //从UKey中读取客户端的序列号
        UKLab_seq = "clientSequenceNumber";
        //确定客户端的合法性
        if(!Lab_seq.equals(UKLab_seq)){
            return "error";
        }

        return "success";
    }*/
    /**
     * 产生客户端的盐值和随机数
     */
    /*private static void generSaltRand(){

        Lab_salt = String.valueOf(new Random());

        Lab_rand = String.valueOf(new Random());
    }*/
    /**
     * 认证服务端的合法性
     */
    /*private static String serverAuth(String md5_info, String seq, String rand, String salt){
        //从UKey中获取关键字key
        ukey = "ukey";
        //使用MD5加密传输过来的数据
        String result = String.valueOf(new SimpleHash("MD5", seq+ukey+rand, salt, 3));
        if(!result.equals(md5_info)){
            return "error";
        }
        return "success";
    }*/
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
    /**
     * 双向认证
     */
    private boolean twoAuth() throws Exception {
        //UKey合法性认证
        /*if(ukeyAuth().equals("error")){
            throw new Exception("UKey 认证失败！");
        }else {
            System.out.println("UKey 认证成功！");
        }*/
        // 设置IO句柄
        BufferedReader in = new BufferedReader(new InputStreamReader(sslClientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(sslClientSocket.getOutputStream())), true);

        //接收服务端发送过来的验证信息
        String server_infos = in.readLine();
        //初始化加密锁
        SecDogs secDogs = new SecDogs();
        secDogs.init();
        //服务端身份认证
        boolean temp = secDogs.serverAuth(server_infos);
        if(temp==false){
            throw new Exception("服务端身份验证失败！");
        }else{
            System.out.println("服务端身份验证成功！");
            //向服务端发送客户端的密文、序列号和随机数
            //Random random = new Random();
            String rand = String.valueOf(genRandomNum(20));
            out.println(secDogs.encryptDog(rand)+Client_seq+rand);
            //客户端身份验证结果反馈，success表示成功
            if (in.readLine().equals("success")){
                return true;
            }else{
                return false;
            }
        }
    }
    /*
     *获取最新的SQL文件
     * */
    /*private File getFile(String filePath){
        //获取所有日志文件的路径
        File logDir = new File(filePath);
        if (logDir.exists()){
            File[] files = logDir.listFiles();
            if (files.length>0){
                List<String> fileList = new ArrayList<>();
                for (int i=0; i<files.length; i++){
                    fileList.add(files[i].getName());
                }
                //获取所有文件中最后变更的文件
                Collections.sort(fileList);
                //File sqlFile = new File(filePath+fileList.get(fileList.size()-1));
                File sqlFile = new File("E:\\文件传输\\发送\\ubuntu-16.04.2-desktop-amd64.iso");
                return sqlFile;
            }
        }
        return null;
    }*/
    /**
     * 向服务端传输文件
     */
    private boolean uploadFile(String upFile) throws Exception {
        File file = new File(upFile);
        if(file.exists()){
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream dos = new DataOutputStream(sslClientSocket.getOutputStream());
            // 文件名和长度
            try {
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
                // 开始传输文件
                long startMili=System.currentTimeMillis();
                System.out.println("======== 开始传输文件 ========");
                byte[] bytes = new byte[4096];
                int length = 0;
                long progress = 0;
                long flag =0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    if((100*progress/file.length())!=0 && (100*progress/file.length())!= flag){
                        flag = 100*progress/file.length();
                        System.out.println("| " + (100*progress/file.length()) + "% |");
                    }
                }
                System.out.println("======== 文件传输成功 ========");
                long endMili=System.currentTimeMillis();
                System.out.println("耗时："+(endMili-startMili)%1000+"秒");
            } catch (IOException e) {
                throw new Exception("文件传输失败！");
            }finally {
                fis.close();
                dos.close();
                sslClientSocket.close();
            }
        }
        return true;
    }
    /**
     * 采用线程，异步处理文件传输
     */
    private void load(ClientCode2 clientCode, String upFile){
        new Thread(new Task(clientCode,upFile)).start();
    }
    class Task implements Runnable{
        private ClientCode2 clientCode;
        private String upFile;
        public Task(ClientCode2 clientCode,String upFile) {
            this.clientCode = clientCode;
            this.upFile = upFile;
        }
        @Override
        public void run() {
            try {
                clientCode.uploadFile(upFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void init() {
        try {
            //获取SSlContext对象
            SSLContext ctx = SSLContext.getInstance("SSL");
            //JSSE密钥管理器KeyManagerFactory对象
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            //信任管理器TrustManagerFactory对象
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            //密钥和证书的存储设施
            KeyStore ks = KeyStore.getInstance("PKCS12");
            KeyStore tks = KeyStore.getInstance("JKS");
            //载入keystore
            ks.load(new FileInputStream(System.getProperty("user.dir")+"/src/key/client.keystore"), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream(System.getProperty("user.dir")+"/src/key/ca-trust.keystroe"), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            //KeyManagerFactory对象初始化
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            //TrustManagerFactory对象初始化
            tmf.init(tks);
            //SSLContext对象初始化
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            //创建连接sslSocket对象
            sslClientSocket = (SSLSocket) ctx.getSocketFactory().createSocket(addrIp, portNo);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        final int subTime =5; //间隔时间秒
        Runnable clientRun = new Runnable() {
            @Override
            public void run() {
                //将一个时间片的日志信息转换成SQL，用于上传
                LogToSql logToSql = new LogToSql();
                //文件转换成功后，方可上传
                String upFile = logToSql.transLogToSql(errorNum*subTime);
                if(upFile!=null){
                    ClientCode2 clientCode = null;
                    try {
                        clientCode = new ClientCode2();
                        //初始化SSL配置
                        clientCode.init();
                        //双向认证成功后，可进行文件传输
                        if(clientCode.twoAuth()){
                            //文件传输
                            clientCode.load(clientCode,upFile);
                            errorNum = 1;
                        }else{
                            throw new Exception("客户端身份验证失败！");
                        }
                    } catch (Exception e) {
                        errorNum++;
                        System.out.println(e);
                    }
                }
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(clientRun, 0, subTime, TimeUnit.SECONDS);
    }
}
