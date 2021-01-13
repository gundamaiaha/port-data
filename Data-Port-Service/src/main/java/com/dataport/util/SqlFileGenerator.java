package com.dataport.util;

import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;

@UtilityClass
public class SqlFileGenerator {


    public void generateSql(String filePath,String sql){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fileWriter!=null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
