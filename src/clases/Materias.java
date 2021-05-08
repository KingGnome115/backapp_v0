package clases;

import java.awt.Color;
import java.util.Scanner;

/**
 *
 * @author Kevin
 */
public class Materias
{

    private int id;
    private int semestre;
    private String grupo;
    private Color color;
    private String nombreMaestro;
    private String nombreMateria;

    public Materias()
    {
    }

    public Materias(int id, int semestre, String grupo, String nombreMaestro, String nombreMateria, String color)
    {
        this.id = id;
        this.semestre = semestre;
        this.grupo = grupo;
        this.nombreMaestro = nombreMaestro;
        this.nombreMateria = nombreMateria;
        Scanner sc = new Scanner(color);
        sc.useDelimiter("\\D+");
        this.color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the semestre
     */
    public int getSemestre()
    {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(int semestre)
    {
        this.semestre = semestre;
    }

    /**
     * @return the nombreMaestro
     */
    public String getNombreMaestro()
    {
        return nombreMaestro;
    }

    /**
     * @param nombreMaestro the nombreMaestro to set
     */
    public void setNombreMaestro(String nombreMaestro)
    {
        this.nombreMaestro = nombreMaestro;
    }

    /**
     * @return the nombreMateria
     */
    public String getNombreMateria()
    {
        return nombreMateria;
    }

    /**
     * @param nombreMateria the nombreMateria to set
     */
    public void setNombreMateria(String nombreMateria)
    {
        this.nombreMateria = nombreMateria;
    }

    /**
     * @return the grupo
     */
    public String getGrupo()
    {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo)
    {
        this.grupo = grupo;
    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

}
