package test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * 文件传输Client端<br>
 * 功能说明：
 *
 * @author 大智若愚的小懂
 * @Date 2016年09月01日
 * @version 1.0
 */
public class FileTransferClient1 extends Socket {

    private static final String SERVER_IP = "127.0.0.1"; // 服务端IP
    private static final int SERVER_PORT = 8899; // 服务端端口

    private Socket client;

    private FileInputStream fis;

    private DataOutputStream dos;

    /**
     * 构造函数<br/>
     * 与服务器建立连接
     * @throws Exception
     */
    public FileTransferClient1() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }

    /**
     * 向服务端传输文件
     * @throws Exception
     */
    public void sendFile() throws Exception {
        try {
            File file = new File("E:\\文件传输\\发送\\VisioProfessional_x86_zh-cn.exe");
            if(file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());

                // 文件名和长度
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            client.close();
        }
    }

    /**
     * 入口
     * @param args
     */
    public static void main(String[] args) {
        try {
            FileTransferClient1 client = new FileTransferClient1(); // 启动客户端连接
            client.sendFile(); // 传输文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}