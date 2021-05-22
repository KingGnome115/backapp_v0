package clases;

/**
 *
 * @author Kevin
 */
public class Horarios implements Comparable<Horarios>
{

    private int id;
    private int materia;
    private String dia;
    private String horaInicio;
    private String horaFinal;
    private boolean notificar = false;

    public Horarios()
    {
    }

    /**
     * Constructor de la clase horarios
     * @param id el identificador
     * @param materia identificador de la materia al que pertenece el horario
     * @param dia el dia del horario
     * @param horaInicio hora de inicio de la clase
     * @param horaFinal hora en que termina la clase
     * @param notificar si la clase debe ser o no notificada
     */
    public Horarios(int id, int materia, String dia, String horaInicio, String horaFinal, boolean notificar)
    {
        this.id = id;
        this.materia = materia;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.notificar = notificar;
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

    /**
     * Método que ordenara las horas de inicio para su representacion
     * @param o el arreglo de horarios
     * @return los horarios ordenados
     */
    @Override
    public int compareTo(Horarios o)
    {
        String a = new String(String.valueOf(this.getHoraInicio()));
        String b = new String(String.valueOf(o.getHoraInicio()));
        return a.compareTo(b);
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

}
