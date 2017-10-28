package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guan on 2017/6/9.
 */
public class FileRWD {

    //读取日志文件，取出上传文件的sourceID
    public String readFile(File logFile, String filePath) throws IOException {

        if(!logFile.exists()){
            logFile.createNewFile();
            return "error";
        }
        FileInputStream fileInputStream = new FileInputStream(logFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line ;
        try {
            while ((line =bufferedReader.readLine())!=null){
                String[] logInfo = new String[2];
                logInfo = line.split(":");
                if(logInfo[0].equals(filePath)){
                    return logInfo[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }
        return "error";
    }

    //将上传文件的名称和sourceID，保存到日志文件中
    private void writeFile(File logFile, String filePath, String sourceID) throws IOException {
        //如果文件不存在，则新建一个
        if(!logFile.exists()){
            logFile.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(logFile,true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        try{
            //写入上传文件的名称和sourceID
            bufferedWriter.write(filePath+":"+sourceID);
            bufferedWriter.newLine();
        }finally {
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        }

    }
    //如果文件成功上传，将日志信息删除
    private void deleteFile(File logFile, String filePath) throws IOException {
        if(!logFile.exists()){
            logFile.createNewFile();
        }
        //将文件读取到内存map中；
        Map<String, String> fileMap = new LinkedHashMap<String, String>();
        FileInputStream fileInputStream = new FileInputStream(logFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line ;
        try {
            while ((line =bufferedReader.readLine())!=null){
                String[] logInfo = new String[2];
                logInfo = line.split(":");
                fileMap.put(logInfo[0],logInfo[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }
        //找到sourceID的信息，删除，然后将map重写回文件
        FileOutputStream fileOutputStream = new FileOutputStream(logFile,false);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        try{
            if(fileMap.size()!=0){
                for(String keyNames : fileMap.keySet()){
                    if(keyNames.equals(filePath)){

                        fileMap.remove(filePath);
                    }else{
                        bufferedWriter.write(keyNames+":"+fileMap.get(keyNames));
                        bufferedWriter.newLine();
                    }
                }
            }
        }finally {
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        }

    }

    public static void main(String[] args) throws IOException {
        FileRWD fileRWD = new FileRWD();
        File file = new File("E:\\文件传输\\发送\\upload.log");

        //fileRWD.writeFile(file, "/lala5.exe", "2345675");

        String sourceID =fileRWD.readFile(file, "/lala2.exe");
        System.out.println("sourceID:"+sourceID);

        fileRWD.deleteFile(file, "/lala2.exe");

    }
}
