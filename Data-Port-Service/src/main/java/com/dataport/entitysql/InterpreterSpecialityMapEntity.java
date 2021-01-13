package com.dataport.entitysql;

import com.dataport.pojo.Agent;
import com.dataport.util.SqlFileGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class InterpreterSpecialityMapEntity {


    public void generateInterpreterSpecialityMap(List<Agent> agents) throws IOException {


        final StringBuilder interpreter_speciality_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        interpreter_speciality_data_sql.append("INSERT IGNORE INTO interpreter_speciality_map (")
                .append("interpreter_id,speciality_tag,average_rating,rating_count,status,created_on,created_by," +
                        "modified_on,modified_by,deleted_on,deleted_by)")
                .append("VALUES\n");
        String NULL = null;

        for (Agent agent : agents) {


            String specialitiesJSON = agent.getAttributes();
            JsonObject jsonObject = JsonParser.parseString(specialitiesJSON).getAsJsonObject();
            JsonArray specialitiesArray = jsonObject.get("services").getAsJsonArray();
            Iterator<JsonElement> specialitesIterator = specialitiesArray.iterator();
            while (specialitesIterator.hasNext()) {
                String speciality = specialitesIterator.next().getAsString();
                System.out.println("speciality --> " + speciality);


                interpreter_speciality_data_sql.append("('")
                        .append(agent.getUserId())
                        .append("','")
                        .append(speciality.toLowerCase())
                        .append("',")
                        .append(NULL).append(',')
                        .append(0).append(",")
                        .append("'active','")
                        .append(agent.getCreatedAt()).append("',")
                        .append(NULL).append(",")
                        .append(NULL).append(",").append(NULL).append(",").append(NULL)
                        .append(",").append(NULL)
                        .append("),\n");
            }

        }

        interpreter_speciality_data_sql.deleteCharAt(interpreter_speciality_data_sql.lastIndexOf(","));
        interpreter_speciality_data_sql.append(";");

        System.out.println("============== interpreter_speciality_data_sql =============== ");
        System.out.println(interpreter_speciality_data_sql.toString());


        //File interpreter_sql_file= new File("src/main/resources/sql/interpreter_vridb_interpreter.sql");
//        FileWriter fileWriter = new FileWriter("src/main/resources/sql/interpreter_vridb_interpreter_speciality_map.sql");
//        fileWriter.write(interpreter_speciality_data_sql.toString());
//        fileWriter.close();

        SqlFileGenerator.generateSql("src/main/resources/sql/interpreter_vridb_interpreter_speciality_map.sql", interpreter_speciality_data_sql.toString());


    }

}
