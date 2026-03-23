package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // constructor
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

    //
    // modification methods
    //

    // adds new patient and creates new csv
    public boolean addPatient(HashMap<String, String> patientInfo)
    {
        if (!validatePatient(patientInfo))
            return false;

        CSVWriter csvWriter = new CSVWriter();
        
        // generates unique id
        boolean stop;
        String randomId = new String();
        do{
            stop = true;
            randomId = UUID.randomUUID().toString(); // id appears to be of uuid format

            for (int i = 0; i< patients.getRowCount(IDColumn); i++)
                if(patients.getValue(IDColumn, i).compareTo(randomId) == 0)
                    stop = false;
        }while(!stop);
        patients.addValue(IDColumn, randomId); // add randomly generated ID

        // add the rest of the info to the new patient
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
        return true;
    }

    // changes a given value to a new one
    // creates new csv
    public void changePatientData(String columnName, String ID, String value)
    {   
        // finds the row of the patient
        int row = 0;
        for (int i = 0; i < patients.getRowCount(IDColumn); i++)
        {
            if (patients.getValue(IDColumn, i).compareTo(ID) == 0)
            {
                row = i;
                break;
            }
        }

        // verifies if row and column exists
        if (!patients.getColumnNames().contains(columnName) || row > patients.getRowCount(columnName) || row == 0)
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
        for (int i = 0; i < patients.getRowCount(IDColumn); i++)
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

    //
    // search methods
    //

    public List<String> searchByKeyword(String keyword)
    {
        List<String> patientsIdList = new ArrayList<>();

        for (int i = 0; i < patients.getRowCount(IDColumn); i++)
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

    // returns patients ID based on name, returns multiple ID's if patients have the same name
    // if no First or Last columns exists returns null
    public List<String> getPatientByName(String patientFirstName, String patientLastName)
    {
        List<String> patientsID = new ArrayList<>();

        if(patients.getColumnNames().indexOf(firstNameColumn) == -1 || patients.getColumnNames().indexOf(lastNameColumn) == -1)
        {
            return null;
        }
        
        for (int i = 0; i < patients.getRowCount(firstNameColumn); i++)
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate oldestPatientBirthday = null;
        int oldestPatientRow = 0;

        if (patients.getColumnNames().contains("BIRTHDATE") && patients.getColumnNames().contains("DEATHDATE"))
        {
            for (int i = 0; i < patients.getRowCount(IDColumn); i++)
            {
                // make sure the patient is alive
                if (patients.getValue("DEATHDATE", i).isEmpty())
                {
                    try{
                    LocalDate patientBirthday = LocalDate.parse(patients.getValue("BIRTHDATE", i), formatter);
                    if(oldestPatientBirthday == null)
                    {
                        oldestPatientBirthday = patientBirthday;
                        oldestPatientRow = i; 
                    }
                    else if(oldestPatientBirthday.isAfter(patientBirthday))
                    {
                       
                        oldestPatientBirthday = patientBirthday;
                        oldestPatientRow = i;                        
                    }
                    } catch(Exception e)
                    {
                        continue; // skips invalid birthdays
                    }
                }
            }
            // constructs the oldest patient full name
            String patientFullName = patients.getValue(firstNameColumn, oldestPatientRow) + 
            " " + patients.getValue(lastNameColumn, oldestPatientRow);
            return patientFullName;
        }
        else
        {
            return null;
        }
    }

    public int numberOfPetientsCity(String city)
    {
        String cityColumn = "CITY";

        if (!patients.getColumnNames().contains(cityColumn))
            return 0;

        int totalPatients = 0;

        for (int i = 0; i < patients.getRowCount(cityColumn); i++)
        {
            if (patients.getValue(cityColumn, i).compareTo(city) == 0)
                totalPatients++;
        }
        return totalPatients;
    }
    
    public HashMap<String, Integer> patientsGender()
    {
        HashMap<String, Integer> patientsGender = new HashMap<>();

        if (!patients.getColumnNames().contains("GENDER"))
            return null;

        // initiate the 3 options at 0
        Integer male = 0;
        Integer female = 0;
        Integer other = 0;

        for (int i = 0; i < patients.getRowCount(IDColumn); i++)
        {
            if(patients.getValue("GENDER", i).compareTo("M") == 0)
                male++;
            else if(patients.getValue("GENDER", i).compareTo("F") == 0)
                female++;
            else
                other++;
        }

        patientsGender.put("M", male);
        patientsGender.put("F", female);
        patientsGender.put("other", other);

        return patientsGender;
    }

    //
    // graph methods
    //

    public Map<String, Integer> getAgeDistribution()
    {
        Map <String, Integer> distribution = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now(); // today's date

        String birthColumn = "BIRTHDATE";
        String deathColumn = "DEATHDATE";

        // return null if there is no field for birthdates and deathdates
        if (!patients.getColumnNames().contains(birthColumn) ||
            !patients.getColumnNames().contains(deathColumn))
            return null;

        for (int i = 0; i < patients.getRowCount(birthColumn); i++)
        {
            if (!patients.getValue(deathColumn, i).isEmpty())
                continue; // skip if patient isn't alive
            try
            {
                LocalDate birthDate = LocalDate.parse(patients.getValue(birthColumn, i), formatter);
                int age = Period.between(birthDate, today).getYears();

                // determine 10-year age group
                int lower = (age / 10) * 10;
                int upper = lower + 9;
                String group = lower + "-" + upper;

                distribution.put(group, distribution.getOrDefault(group, 0) + 1);

            }catch(Exception e)
            {
                continue; // skip malformed birthdates
            }
        }            
        return distribution;
    }

    public Map<String, Integer> getCityDistribution()
    {
        Map<String, Integer> distribution = new HashMap<>();

        String cityColumn = "CITY";
        String deathColumn = "DEATHDATE";

        // return null if CITY column doesn't exist
        if (!patients.getColumnNames().contains(cityColumn))
            return null;

        for (int i = 0; i < patients.getRowCount(cityColumn); i++)
        {
            if (patients.getColumnNames().contains(deathColumn) &&
                !patients.getValue(deathColumn, i).isEmpty())
                continue;

            try
            {
                String city = patients.getValue(cityColumn, i);

                // skip null or empty values
                if (city == null || city.isBlank())
                    continue;

                // normalize 
                city = city.trim();

                distribution.put(city, distribution.getOrDefault(city, 0) + 1);

            }
            catch (Exception e)
            {
                continue; // skip malformed entries
            }
        }

        return distribution;
    }

    //
    // json writer
    //

    public boolean writeJSON()
    {
        return JSONWriter.writeJSON(patients);
    }
}
