package Coursework.src.java.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader 
{
    
    public DattaFrame getPatients()
    {   
        return readFile("data/patients100.csv"); //TODO Add file name
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
        
        } catch(IOException e) // TODO Add error handling
        {
            e.printStackTrace();
        }

        return data;
    }


}
