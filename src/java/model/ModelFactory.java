package Coursework.src.java.model;

import java.io.File;
import java.io.IOException;

public class ModelFactory 
{
    private static Model model;

    public static Model getModel()
    {
        if (model == null)
        {
            model = new Model(); // TODO add file optionality reading
        }
        
        return model;
    }
    
}
