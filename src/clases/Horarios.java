package clases;

/**
 *
 * @author Kevin
 */
public class Horarios
{
    private int id;
    private int materia;
    private String dia;
    private String horaInicio;
    private String horaFinal;
    private boolean notificar=false;

    public Horarios()
    {
    }

    public Horarios(int id, int materia, String dia, String horaInicio, String horaFinal, boolean notificar)
    {
        this.id = id;
        this.materia = materia;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.notificar=notificar;
    }

    /**
     * @return the materia
     */
    public int getMateria()
    {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(int materia)
    {
        this.materia = materia;
    }

    /**
     * @return the dia
     */
    public String getDia()
    {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia)
    {
        this.dia = dia;
    }

    /**
     * @return the horaInicio
     */
    public String getHoraInicio()
    {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(String horaInicio)
    {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFinal
     */
    public String getHoraFinal()
    {
        return horaFinal;
    }

    /**
     * @param horaFinal the horaFinal to set
     */
    public void setHoraFinal(String horaFinal)
    {
        this.horaFinal = horaFinal;
    }

    /**
     * @return the notificar
     */
    public boolean isNotificar()
    {
        return notificar;
    }

    /**
     * @param notificar the notificar to set
     */
    public void setNotificar(boolean notificar)
    {
        this.notificar = notificar;
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
    
    
}
