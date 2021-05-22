package clases;

import java.io.Serializable;

/**
 *
 * @author Kevin
 */
public class HojaLibreta implements Serializable
{

    private String titulo;
    private String texto;

    public HojaLibreta()
    {
        this.titulo = "Hoja";
        this.texto = "";
    }

    /**
     * Constructor de la clase HojaLibreta
     * @param titulo el titulo de la pagina
     * @param texto el texto de la pagina
     */
    public HojaLibreta(String titulo, String texto)
    {
        this.titulo = titulo;
        this.texto = texto;
    }

    /**
     * @return the titulo
     */
    public String getTitulo()
    {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    /**
     * @return the texto
     */
    public String getTexto()
    {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto)
    {
        this.texto = texto;
    }

}
