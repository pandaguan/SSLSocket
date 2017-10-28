package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSL Client
 *
 * @author Leo
 */
public class SSLClient {

    private static final String DEFAULT_HOST                    = "127.0.0.1";
    private static final int    DEFAULT_PORT                    = 7777;

    private static final String CLIENT_KEY_STORE_PASSWORD       = "client";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "client";

    private SSLSocket           sslSocket;

    /**
     * 启动客户端程序
     *
     * @param args
     */
    public static void main(String[] args) {
        SSLClient client = new SSLClient();
        client.init();
        client.process();
    }


    public void process() {
        if (sslSocket == null) {
            System.out.println("ERROR");
            return;
        }
        try {
            InputStream input = sslSocket.getInputStream();
            OutputStream output = sslSocket.getOutputStream();

            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);

            bos.write("1234567890".getBytes());
            bos.flush();

            byte[] buffer = new byte[10];
            bis.read(buffer);
            System.out.println(new String(buffer));

            sslSocket.close();
        } catch (IOException e) {
            System.out.println(e);
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
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");
            //载入keystore
            ks.load(new FileInputStream("src/key/kclient.keystore"), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream("src/key/tclient.keystore"), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            //KeyManagerFactory对象初始化
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            //TrustManagerFactory对象初始化
            tmf.init(tks);
            //SSLContext对象初始化
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            //创建连接sslSocket对象
            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
