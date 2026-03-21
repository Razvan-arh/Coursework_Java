package model;

import java.util.ArrayList;

public class Column 
{
    private String name;
    private ArrayList<String> rows;

    public Column(String name)
    {
        this.name = name;
        this.rows = new ArrayList<String>(); // initialize empty arraylist
    }

    public String getName()
    {
        return this.name;
    }

    public int getSize()
    {
        return this.rows.size();
    }

    public String getRowValue(int row) // return value of specified row
    {
        return this.rows.get(row); 
    }
    
    public void setRowValue(int row, String value) // set value of a specified row
    {
        this.rows.set(row, value);
    }

    public void addRowValue(String value) // adds new value to rows
    {
        this.rows.add(value);
    }

    public void deleteRowValue(int row)
    {
        this.rows.remove(row);
    }
}
