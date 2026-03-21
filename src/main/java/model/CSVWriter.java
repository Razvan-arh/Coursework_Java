package model;

import java.io.FileWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.IOException;
import java.io.Writer;



public class CSVWriter 
{
    // writes a new csv if one hasn't been initiated
    private void writeCSV(DataFrame patients)
    {
        CSVFormat format = CSVFormat.DEFAULT;

        try (Writer writer = new FileWriter("data/patientsModified.csv"))
        {
            CSVPrinter csvPrinter = new CSVPrinter(writer, format);

            // print the column headers
            csvPrinter.printRecord(patients.getColumnNames());

            // complete the data
            for (int i = 1; i < patients.getRowCount(patients.getColumnNames().get(0)); i++) 
            {
                // this is a bit of a convoluted way of iterating through the Data frame
                // however since this is only used here I think this does not cause many issues
                for (String column : patients.getColumnNames())
                {   
                    csvPrinter.print(patients.getValue(column, i));
                }
                csvPrinter.println();
            }
            csvPrinter.close();

        }catch(IOException e)
        {
            e.printStackTrace(); // add error handling
        }
    }

    public void saveCSV(DataFrame patients)
    {
        writeCSV(patients);
    }
}
