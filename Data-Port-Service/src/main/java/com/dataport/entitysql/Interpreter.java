package com.dataport.entitysql;

import com.dataport.pojo.Agent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Interpreter {


    public void generateInterpreterSql(List<Agent> agents) throws IOException {

        final StringBuilder interpreter_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        interpreter_data_sql.append("INSERT IGNORE INTO interpreter VALUES\n");
        String NULL = null;

        for (Agent agent : agents) {
            interpreter_data_sql.append("('")
                    .append(agent.getUserId())
                    .append("','")
                    .append(agent.getViId())
                    .append("',")
                    .append(NULL)
                    .append(",")
                    .append(NULL)
                    .append(",'active','")
                    .append(agent.getCreatedAt())
                    .append("',")
                    .append(NULL)
                    .append(",")
                    .append(NULL).append(",").append(NULL).append(",").append(NULL)
                    .append(",").append(NULL)
                    .append("),\n");
        }


        interpreter_data_sql.deleteCharAt(interpreter_data_sql.lastIndexOf(","));
        interpreter_data_sql.append(";");

        System.out.println("============== interpreter_sql =============== ");
        System.out.println(interpreter_data_sql.toString());


        //File interpreter_sql_file= new File("src/main/resources/sql/interpreter_vridb_interpreter.sql");
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/interpreter_vridb_interpreter.sql");
        fileWriter.write(interpreter_data_sql.toString());
        fileWriter.close();


    }


}
