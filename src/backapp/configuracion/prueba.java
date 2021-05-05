/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backapp.configuracion;

import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class prueba
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String h1 ="07:00";
        String h2 ="19:00";
        String h3 ="06:00";
        String h4 ="10:00";
        
        ArrayList<String> cadenas = new ArrayList<>();
        cadenas.add(h1);
        cadenas.add(h2);
        cadenas.add(h3);
        cadenas.add(h4);
        
        for (int i = 0; i < cadenas.size(); i++)
        {
            System.out.println(cadenas.get(i));
        }
        
        System.out.println("------------------------");
        
        
        cadenas.sort( (s1,s2)-> s1.compareTo(s2) );
        for (int i = 0; i < cadenas.size(); i++)
        {
            System.out.println(cadenas.get(i));
        }
    }
    
}
