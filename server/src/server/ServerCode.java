package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Properties;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

import Sense4.SecDogs;

/**
 * @author cyberpecker
 *
 */
public class ServerCode {

	// 端口号
    private static  int portNo ;
    //服务端私钥库的密钥
    private static  String SERVER_KEY_STORE_PASSWORD ;
    //CA根证书库的密钥
    private static  String SERVER_TRUST_KEY_STORE_PASSWORD ;
    //输出文件地址
    private static  String dirPath ;
    //服务端序列号
    private static String ServerSeq ;
    //数据库驱动
    private static String driver;
    //数据库url
    private static String dburl;
    //数据库用户名
    private static String username;
    //数据库密码
    private static String password;
    //加载日志
	private static Logger logger;
    
    
    
    private SSLServerSocket  serverSocket;
    
	public static SQLExec getConnection(){
		SQLExec sqlExec = new SQLExec();
		
		//设置数据库参数
	    sqlExec.setDriver(driver);
	    sqlExec.setUrl(dburl);
	    sqlExec.setUserid(username);
	    sqlExec.setPassword(password);
	    
	    return sqlExec;
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
	/*
     * 采用线程，异步处理每个客户端的文件传输
     * */
    private void load(SQLExec sqlExec) throws Exception {
    	long count =1;
    	 if (serverSocket == null) {
             logger.warning("服务端Socket创建失败！");
             return;
         }
    	while (true) {
    		// 等待连接
    		logger.info("等待客户端接入。。");
			Socket socket = serverSocket.accept();
			//每接收到一个socket，就建立一个线程进行处理
			new Thread(new Task(socket, sqlExec)).start();
			logger.info("客户端"+count+++"准备接入");
    	}
    }
    /*
     * 处理文件传输的线程类
     * */
	class Task implements Runnable {

		private Socket socket;
		
		private SQLExec sqlExec;

		public Task(Socket socket, SQLExec sqlExec) {
			this.socket = socket;
			this.sqlExec = sqlExec;
		}
		
		public synchronized void run() {
			// TODO Auto-generated method stub
			logger.info("接入的客户端: " + socket);
			BufferedReader in = null;
			PrintWriter out = null;
			try{
				
				// 设置IO
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				
				// 生成盐值和服务端随机数
				/*generSaltRand();
				// 从UKey中获取Key关键字
				ukey = "ukey";
				// 使用MD5加密验证信息，用于证明服务端的身份
				String md5_info = String.valueOf(new SimpleHash("MD5", Ser_seq + ukey + Ser_rand, Ser_salt, 3));
				out.println(md5_info + "," + Ser_seq + "," + Ser_rand + "," + Ser_salt);*/
			    //向客户端发送服务端的密文、序列号和随机数
	            //Random random = new Random();
	            String rand = genRandomNum(20);
	            //初始化加密锁
	            SecDogs secDogs = new SecDogs();
	            secDogs.init();
	            String encrypt = secDogs.encryptDog(rand);
	            	
	            out.println(encrypt+ServerSeq+rand);
				
				// 接收客户端发送过来的验证信息
				String client_infos = in.readLine();
				// 客户端身份认证
				if (secDogs.clientAuth(client_infos)==false) {
					logger.warning("客户端身份验证失败！");
					throw new Exception("客户端身份验证失败！");
				} else {
					out.println("success");
					logger.info("客户端认证通过，开始上传文件吧！");
					DataInputStream dis = null;
					FileOutputStream fos = null;
					try{
						
					    dis = new DataInputStream(socket.getInputStream());
						
						// 文件名和长度
						String fileName = dis.readUTF();
						long fileLength = dis.readLong();
						File directory = new File(dirPath);
						if (!directory.exists()) {
							directory.mkdir();
						}
						File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
					   
						fos = new FileOutputStream(file);
						
						// 开始接收文件
						byte[] bytes = new byte[1024];
						int length = 0;
						while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
							fos.write(bytes, 0, length);
							fos.flush();
						}
						logger.info("======== 文件接收成功 [File Name：" + fileName + "] [Size：" +(float) fileLength/1024 + " KB] ========");
						//执行传输过来的SQL文件
						sqlExec.setSrc(file);
						sqlExec.setProject(new Project());
					    sqlExec.setEncoding("UTF-8");
						sqlExec.execute();
						logger.info("======== 文件更新到数据库Name：" + fileName + "] [Size：" + (float)fileLength/1024 + " KB] ========");
					} catch (Exception e1) {
						e1.printStackTrace();
					}finally{
						dis.close();
						fos.close();
					}
				}
			} catch (Exception e) {
				
			}finally{
				try {
					in.close();
					out.close();
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
	public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("PKCS12");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream(System.getProperty("user.dir")+"/src/key/server.keystore"), SERVER_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream(System.getProperty("user.dir")+"/src/key/ca-trust.keystroe"), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());

            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(portNo);
            serverSocket.setNeedClientAuth(true); 
        } catch (Exception e) {
            logger.warning(String.valueOf(e));
        }
    }
	public static void main(String[] args) throws IOException, Exception
	{
		//加载日志
        InputStream inputStreamlog = ServerCode.class.getResourceAsStream("/logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(inputStreamlog);
            logger = Logger.getLogger("ClientCode");
        } catch (IOException e) {
            logger.warning(String.valueOf(e));
        }
        
		InputStream inputStream = ServerCode.class.getResourceAsStream("/serverData.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            portNo = Integer.parseInt(properties.getProperty("portNo"));
            SERVER_KEY_STORE_PASSWORD = properties.getProperty("SERVER_KEY_STORE_PASSWORD");
            SERVER_TRUST_KEY_STORE_PASSWORD = properties.getProperty("SERVER_TRUST_KEY_STORE_PASSWORD");
            dirPath = properties.getProperty("dirPath");
            ServerSeq = properties.getProperty("ServerSeq");
            driver = properties.getProperty("driver");
            dburl = properties.getProperty("dburl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");                     
        }
        catch (Exception e) {
            logger.warning("不能读取serverData.properties配置文件." );
        }
        
		ServerCode serverCode = new ServerCode();
		//初始化SSL配置
		serverCode.init();
		//初始化数据连接
		SQLExec sqlExec=null;
		if(sqlExec==null){
			sqlExec = getConnection();
		}
		serverCode.load(sqlExec);
		
	}
}
