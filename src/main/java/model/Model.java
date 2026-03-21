package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Model 
{
    DataFrame patients;

    // Basic column names based on the data
    ArrayList<String> requireFields;

    String firstNameColumn = "FIRST";
    String lastNameColumn = "LAST";
    String IDColumn = "ID";
    int numberOfColumns;

    public Model()
    {
        // initiate the data frame from the data
        this.patients = new DataFrame();
        DataLoader dataLoader = new DataLoader();
        this.patients = dataLoader.getPatients();

        // required fields for new patients
        this.requireFields = new ArrayList<>();
        List<String> anotherRequireFields = Arrays.asList("BIRTHDATE","SSN","FIRST", "LAST", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE");
        this.requireFields.addAll(anotherRequireFields);
        
        // get the number of columns
        this.numberOfColumns = patients.getColumnNames().size(); 
    }

    // minimal error detection
    private boolean validatePatient(HashMap<String, String> patientInfo)
    {
        for (String essentialKey : requireFields)
        {
            if (!patientInfo.containsKey(essentialKey) || patientInfo.get(essentialKey) == null)
                return false;
        }
        return true;
    }

    public List<String> searchByKeyword(String keyword)
    {
        List<String> patientsIdList = new ArrayList<>();

        for (int i = 1; i < patients.getRowCount(IDColumn); i++)
        {   
            for (String columnName : patients.getColumnNames())
            {
                if (patients.getValue(columnName, i).indexOf(keyword) != -1)
                {
                    patientsIdList.add(patients.getValue(IDColumn, i));
                    break;
                }
            }
        }
        return patientsIdList;
    }

    // adds new patient and creates new csv
    public void addPatient(HashMap<String, String> patientInfo)
    {
        if (!validatePatient(patientInfo))
            return;

        CSVWriter csvWriter = new CSVWriter();
        String randomId = UUID.randomUUID().toString(); // id appears to be of uuid format
        //TODO verify agains previous UUID
        patients.addValue(IDColumn, randomId); // add random ID generator

        for (String field : patients.getColumnNames())
        {   
            if (field.compareTo(IDColumn) == 0)
            {
                continue;
            }
            else if (patientInfo.containsKey(field))
            {
                patients.addValue(field, patientInfo.get(field));
            }
            else
            {
                patients.addValue(field, "");
            }
        }
    
        csvWriter.saveCSV(patients);
    }

    // changes a given value to a new one
    //creates new csv
    public void changePatientData(String columnName, int row, String value)
    {   
        // verifies if row and column exists
        if (!patients.getColumnNames().contains(columnName) || row > patients.getRowCount(columnName))
            return;

        CSVWriter csvWriter = new CSVWriter();

        // modifies DataFrame and adds new csv
        patients.setValue(columnName, row, value);
        csvWriter.saveCSV(patients);
    }

    // deletes a patient from the DataFrame 
    // makes new csv file for the new data
    public void deletePatientData(String patientID)
    {
        // verifies if patient exists
        for (int i = 1; i < patients.getRowCount(IDColumn); i++)
        {
            if (patients.getValue(IDColumn, i).compareTo(patientID) == 0)
            {
                for (String column : patients.getColumnNames())
                {
                    patients.deleteValue(column, i);
                }
                
                CSVWriter csvWriter = new CSVWriter();

                // modifies DataFrame and adds new csv
                csvWriter.saveCSV(patients);

                return; // returns when found patient
            }
        }
    }


    // returns patients ID based on name, returns multiple ID's if patients have the same name
    // if no First or Last columns exists returns null
    public List<String> getPatientByName(String patientFirstName, String patientLastName)
    {
        List<String> patientsID = new ArrayList<>();

        if(patients.getColumnNames().indexOf(firstNameColumn) == -1 || patients.getColumnNames().indexOf(lastNameColumn) == -1)
        {
            return null;
        }
        
        for (int i = 1; i < patients.getRowCount(firstNameColumn); i++)
        {
            if (patients.getValue(firstNameColumn, i).compareToIgnoreCase(patientFirstName) == 0)
            {
                if (patients.getValue(lastNameColumn, i).compareToIgnoreCase(patientLastName) == 0)
                    patientsID.add(patients.getValue(IDColumn, i));
            }
        }

        return patientsID;
    }

    public int numberOfPatients()
    {
        return patients.getRowCount(patients.getColumnNames().get(0)); // the 0 index is going to be ID which all patients must have
    }

    // returns a hashmap with a patient's info maped to the type of info
    public HashMap<String,String> patientInfo(String patientId)
    {
        HashMap <String, String> patientInfo = new HashMap<>();

        int row = 0;

        while (patients.getValue(IDColumn, row).compareTo(patientId) != 0 && row < patients.getRowCount(IDColumn))
        {   
            row++;
        }

        for (String column : patients.getColumnNames())
        {
            patientInfo.put(column, patients.getValue(column, row));
        }

        return patientInfo;
    }

    public String oldestPatient()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate oldestPatientBirthday = null;

        if (patients.getColumnNames().contains("BIRTHDATE") && patients.getColumnNames().contains("DEATHDATE"))
        {
            for (int i = 1; i < patients.getRowCount(IDColumn); i++)
            {
                // make sure the patient is alive
                if (patients.getValue("DEATHDATE", i) == null)
                {
                    LocalDate patientBirthday = LocalDate.parse(patients.getValue("BIRTHDATE", i), formatter);
                    if(oldestPatientBirthday == null)
                        oldestPatientBirthday = patientBirthday;
                    else if(oldestPatientBirthday.isAfter(patientBirthday))
                        oldestPatientBirthday = patientBirthday;
                }
            }
            return oldestPatientBirthday.toString();
        }
        else
        {
            return null;
        }
    }

    //public int numberOfPetientsCity(String city)

}
