package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model 
{
    DataFrame patients;

    // Basic column names based on the data

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

        this.numberOfColumns = patients.getColumnNames().size(); // get the number of columns
    }

    //private boolean validatePatient()
    {

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

    //public void addPatient()
    {

    }

    //public void patientDied()
    {

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
    public HashMap<String,String> patientInfo(int patientId)
    {
        HashMap <String, String> patientInfo = new HashMap<>();

        for (String column : patients.getColumnNames())
        {
            patientInfo.put(column, patients.getValue(column, patientId));
        }

        return patientInfo;
    }
}
