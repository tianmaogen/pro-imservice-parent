package cn.ibabygroup.pros.imservice.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen on 2016/9/19.
 * 对CSV文件的读取和写入操作
 */
public class CSVUtil {
    public static List<String> readIds(String filePath) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
        String line;
        while((line=reader.readLine())!=null){
//                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            list.add(line.trim());
        }
        return list;
    }
}
