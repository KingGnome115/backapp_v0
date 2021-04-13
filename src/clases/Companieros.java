package clases;

/**
 *
 * @author Kevin
 */
public class Companieros
{
    private int id;
    private String nombre;
    private String email;

    public Companieros()
    {
    }

    public Companieros(int id, String nombre, String email)
    {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
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
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    
}
