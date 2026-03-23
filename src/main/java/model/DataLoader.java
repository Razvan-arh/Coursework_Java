package model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.Reader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader 
{
    
    public DataFrame getPatients()
    {   
        // use modified csv if it exists
        File f = new File("data/patientsModified.csv");
        if(f.exists())
            return readFile("data/patientsModified.csv");
        else 
            return readFile("data/patients1000.csv"); 
    }


    public DataFrame readFile(String fileName)
    {

        DataFrame data = new DataFrame();

        // This assumes that the first row is for headers
        try(Reader reader = new FileReader(fileName); 
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) 
        {

        // Iterates through the headers and generates nthe columns 
        // with their appropriate names
        for (String header : csvParser.getHeaderMap().keySet())
        {
            data.addColumn(header);
        }

        // Adds all data to the DataFrame
        for (CSVRecord csvRecord : csvParser)
        {
            
        for (String columnName : csvParser.getHeaderMap().keySet())
        {
            data.addValue(columnName, csvRecord.get(columnName));
        }

        }
        
        } catch(IOException e) 
        {
            // stops execution and gives clear error message
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }

        return data;
    }


}
