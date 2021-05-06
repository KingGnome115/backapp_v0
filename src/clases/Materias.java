package clases;

/**
 *
 * @author Kevin
 */
public class Materias
{
    private int id;
    private int semestre;
    private String nombreMaestro;
    private String nombreMateria;

    public Materias()
    {
    }

    public Materias(int id, int semestre, String nombreMaestro, String nombreMateria)
    {
        this.id = id;
        this.semestre = semestre;
        this.nombreMaestro = nombreMaestro;
        this.nombreMateria = nombreMateria;
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
    
    
}
