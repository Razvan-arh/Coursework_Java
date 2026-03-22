package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JSONWriter 
{
    public static boolean writeJSON(DataFrame patients)
    {
        final String outputJson = "patients.json";

        ObjectMapper mapper = new ObjectMapper();

        // json list 
        List<Map<String, String>> jsonList = new ArrayList<>();

        //loops through all patients
        for (int i = 1; i < patients.getRowCount(patients.getColumnNames().get(0)); i++)
        {       
            // map that allows to transfer pateints from DataFrame to json
            Map <String, String> patientData = new HashMap<>();

            for (String column : patients.getColumnNames())
            {
                patientData.put(column, patients.getValue(column, i));
            }
            jsonList.add(patientData);
        }       

        try{
            // write the json file
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(outputJson), jsonList);
            return true;
        }catch(IOException e)
        {
            e.printStackTrace(); 
            return false;
        }
    
    }    
    

}
