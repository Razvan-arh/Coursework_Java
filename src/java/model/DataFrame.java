package Coursework.src.java.model;

import java.util.ArrayList;

public class DataFrame 
{
    private ArrayList<Column> columnArray;

    public DataFrame()
    {
        this.columnArray = new ArrayList<>();
    }

    private int getIndex(String columnName) // find the index of a column with specified name
    {
        for (int i = 0; i < columnArray.size(); i++)
        {
            if (columnArray.get(i).getName().equals(columnName))
            {
                return i;
            }
        }
        return -1; // column wasn't found
    }

    public void addColumn(String columnName)
    {
        columnArray.add(new Column(columnName));
    }
    
    public ArrayList<String> getColumnNames()
    {
        ArrayList<String> names = new ArrayList<>();
        
        for (int i = 0; i < columnArray.size(); i++)
        {
            names.add(columnArray.get(i).getName()); // adds the name of a column to an arraylist
        }
        return names;
    }

    public int getRowCount(String columnName) // return number of rows of a named column if column isn't found returns -1 
    {
        int index = getIndex(columnName);

        if (index >= 0)
            return columnArray.get(index).getSize();
        return -1;
    }

    public String getValue(String columnName, int row) // returns value of a row from a given column if it exists, otherwise returns null
    {
        int index = getIndex(columnName);
        
        if (index >= 0 && row < columnArray.get(index).getSize())
        {
            return columnArray.get(index).getRowValue(row);
        }
        return null;
    }

    public void setValue(String columnName, int row, String value) // sets value if column and row exist, otherwise does nothing
    {
        int index = getIndex(columnName);

        if (index >= 0 && row < columnArray.get(index).getSize())
        {
            columnArray.get(index).setRowValue(row, value);
        }
    }

    public void addValue(String columnName, String value) // adds value if column exists, otherwise does nothing
    {
        int index = getIndex(columnName);

        if (index >= 0)
        {
            columnArray.get(index).addRowValue(value);
        }
    }
}
