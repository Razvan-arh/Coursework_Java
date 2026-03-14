package Coursework.src.java.model;

public class Model 
{
    DataFrame patients;

    // Basic column names based on the data

    String firstNameColumn = "FIRST";
    String lastNameColumn = "LAST";
    String IDColumn = "ID";

    public Model()
    {
        this.patients = new DataFrame();
        this.patients = DataLoader.getPatients();
    }

    private boolean validatePatient()
    {

    }

    public List<String> searchByKeyword(String keyword)
    {
        
    }

    public void addPatient()
    {

    }

    public void patientDied()
    {

    }

    // returns patients ID based on name, returns multiple ID's if patients have the same name
    // if no First or Last columns exists returns null
    public List<String> getPatientByName(String patientFirstName, String patientLastName)
    {
        List<String> patientsID = new List();

        if(patients.getColumnNames.indexOf(firstNameColumn) == -1 || patients.getColumnNames.indexOf(lastNameColumn) == -1)
        {
            return null;
        }
        
        for (int i = 1; i < patients.getRowCount(firstNameColumn); i++)
        {
            if (patients.getValue(firstNameColumn, i).compareToIgnoreCase(patientFirstName))
            {
                if (patients.getValue(lastNameColumn, i).compareToIgnoreCase(patientLastName))
                    patientsID.add(patients.getValue(IDColumn, i));
            }
        }

        return patientsID;
    }

    public int numberOfPatients()
    {
        return patients.getRowCount(patients.getColumnNames.get(0)); // the 0 index is going to be ID which all patients must have
    }


}
