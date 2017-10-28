package test;

import java.util.logging.Logger;

/**
 * Created by guan on 2017/7/19.
 */
public class LogTest {
    private static Logger LOG ;
    public static void  main(String[] arg){
        System.setProperty("java.util.logging.config.file", "src/logging.properties");
        LOG = Logger.getLogger("LogTest");
        LOG.warning("测试信息");
        LOG.info("hello");
    }
}
