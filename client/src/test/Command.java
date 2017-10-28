package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Command {
    public static void exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream() ,"GBK"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String commandStr = "cmd /c mysqlbinlog -v --database=test  --start-datetime=\"2017-07-31 13:51:22\" --stop-datetime=\"2017-07-31 14:05:37\" D:/WorkSoft/mysql/binlog/binlog.000016  | findstr ### ";
        //String commandStr = "net user";

        Command.exeCmd(commandStr);

        //System.out.println(System.getProperty("java.library.path"));
    }
}