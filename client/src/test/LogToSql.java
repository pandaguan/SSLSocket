package test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guan on 2017/6/12.
 */
public class LogToSql {
    private boolean transLogToSql(String mysqlBinLog,String startTime, String endTime, String logPath, String sqlPath){
        //获取所有日志文件的路径
        File logDir = new File(logPath);
        String logFiles = null;
        if (logDir.exists()){
            File[] files = logDir.listFiles();
            for (int i=0; i<files.length-1; i++){
                if (logFiles==null){
                    logFiles=logPath+"/"+files[i].getName()+" ";
                }else {
                    logFiles+=logPath+"/"+files[i].getName()+" ";
                }
            }
        }
        //组合成可执行的命令行
        String cmdArr = mysqlBinLog+" --start-datetime=\""+startTime+"\""+" --stop-datetime=\""+endTime+"\" "+logFiles+" > "+sqlPath;
        try {
            Runtime.getRuntime().exec(cmdArr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void  main(String[] arg){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:00:00");
        String endTime = dateFormat.format(now);
        System.out.println(endTime);

        /*LogToSql logToSql = new LogToSql();
        logToSql.transLogToSql("cmd /c mysqlbinlog",
                "2017-6-13 13:00:00", "2017-6-13 14:45:00",
                "D:/studysoft/MySQL/binlog","E:/文件传输/发送/test.sql");*/
    }
}
