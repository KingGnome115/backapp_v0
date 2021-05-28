package clases;

import java.sql.Connection;
import java.util.ArrayList;
import poo.bd.Conexion;
import poo.bd.Querys;

/**
 *
 * @author rosal
 */
public class ManipulaBD
{

    private static Conexion con = new Conexion();

    /**
     * Método quehace la conexion con xampp
     *
     * @return la conexion con la bd o null si no logro conectarse
     */
    public static Connection conecta()
    {
        try
        {
            return con.Conecta("localhost:3306", "backapp", "root", "", 2);
//            return con.Conecta("127.0.0.1:3307", "backapp", "root", "", 2);
        } catch (Exception e)
        {
            System.out.println("No se pudo conectar a la bd");
            return null;
        }
    }

    /**
     * desconecta la aplicacion del xampp
     *
     * @param conn la conexion a terminar
     */
    public static void desconecta(Connection conn)
    {
        con.desconectar(conn);
    }

    /**
     * Método que recontruye los objetos dependiendo de los datos traidos de la
     * bd
     *
     * @param reg el arraylist de todos los datos que llegaron
     * @return arreglo de materias
     */
    public static ArrayList<Materias> cargarMaterias(ArrayList<Object> reg)
    {
        ArrayList<Materias> v = new ArrayList<>();
        try
        {
            for (int i = 0; i < reg.size(); i += 6)
            {
                String idS = "";
                idS = (String) reg.get(i);
                idS = idS.trim();
                if (idS != "" && idS != " ")
                {
                    int id = Integer.parseInt(idS.trim());
                    String nombreMateria = ((String) reg.get(i + 1)).trim();
                    String nombreMaestro = ((String) reg.get(i + 2)).trim();
                    String semestreS = ((String) reg.get(i + 3)).trim();
                    int semestre = Integer.parseInt(semestreS);
                    String grupo = ((String) reg.get(i + 4)).trim();
                    String color = ((String) reg.get(i + 5));
                    System.out.println(color + " AQUI LLEGUE");

                    Materias obj = new Materias(id, semestre, grupo, nombreMaestro, nombreMateria, color);
                    v.add(obj);
                }
            }
            return v;
        } catch (Exception e)
        {
            System.err.println("Error en cargarMaterias, "+e.toString());
            if (!v.isEmpty())
            {
                return v;
            } else
            {
                return null;
            }
        }
    }

    /**
     * Método que recibe los datos de materias y los registra en la bd
     *
     * @param id identificador de la materia
     * @param semestre al cual pertenece la materia
     * @param nombreMaestro nombre del maestro de la materia
     * @param nombreMateria nombre de la materia
     * @param grupo el grupo de la materia
     * @param color el color para representar la materia
     */
    public static void AltaMaterias(int id, int semestre, String nombreMaestro, String nombreMateria, String grupo, String color)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            sql.Insertar(con, "materias",
                    "" + id + ","
                    + "'" + nombreMateria + "'" + ","
                    + "'" + nombreMaestro + "'" + ","
                    + semestre + ","
                    + "'" + grupo + "'" + ","
                    + "'" + color + "'"
            );
        }
        desconecta(con);
    }

    /**
     * Método que da de baja la materia de la bd
     *
     * @param id identificador con el cual se hara la eliminacion
     */
    public static void BajasMaterias(int id)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            sql.Delete(con, "materias", "id", "" + id + "");
        }
        desconecta(con);
    }

    /**
     * Método que consulta las materias en la bd
     *
     * @param variable la variable por la cual se consultara
     * @param condicion la condicion que se debe cumplir
     * @return las materias en forma de arraylist nt: las variables se envias
     * así "variable=" la condicion las cadenas "'cadena'" y numeros "1"
     */
    public static ArrayList<Materias> ConsultaMaterias(String variable, String condicion)
    {
        Connection con = ManipulaBD.conecta();
        ArrayList<Materias> ap = null;
        if (con != null)
        {
            Querys sql = new Querys();
            ap = ManipulaBD.cargarMaterias(sql.Seleccion(con, "*", "materias", variable + condicion));
            desconecta(con);
        }
        return ap;
    }

    /**
     * Método que modifica los datos de la bd de materias
     *
     * @param id identificador de la materia
     * @param campos los campos que seran modificados
     * @param datos los nuevos datos nt: las variables se envias así "variable="
     * la condicion las cadenas "'cadena'" y numeros "1"
     */
    public static void ModificarMaterias(int id, String campos, String datos)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            ArrayList<Materias> ap = ManipulaBD.cargarMaterias(sql.Seleccion(con, "*", "materias", "id=" + id + ""));
            if (ap != null)
            {
                sql.Modificar(con, "materias", campos, datos, "id=" + id + "");
                desconecta(con);
                System.out.println("Modificado");
            }
        }
    }

    /**
     * Método que recontruye los objetos dependiendo de los datos traidos de la
     * bd
     *
     * @param reg el arraylist de todos los datos que llegaron
     * @return arreglo de horarios
     */
    public static ArrayList<Horarios> cargarHorarios(ArrayList<Object> reg)
    {
        ArrayList<Horarios> v = new ArrayList<>();
        try
        {
            System.out.println(reg.size());
            for (int i = 0; i < reg.size(); i += 6)
            {
                String idS = "";
                idS = (String) reg.get(i);
                idS = idS.trim();
                if (idS != "" && idS != " ")
                {
                    int id = Integer.parseInt(idS.trim());

                    String materiaS = ((String) reg.get(i + 1)).trim();
                    int materia = Integer.parseInt(materiaS);
                    
                    String dia = ((String) reg.get(i + 2)).trim();
                    
                    String horaInicio = ((String) reg.get(i + 3)).trim();
                    
                    String horaFinal = ((String) reg.get(i + 4)).trim();
                    
                    String notificarS = ((String) reg.get(i + 5)).trim();
                    boolean notificar = Boolean.parseBoolean(notificarS);

                    Horarios obj = new Horarios(id, materia, dia, horaInicio, horaFinal, notificar);
                    v.add(obj);
                }
            }
            return v;
        } catch (Exception e)
        {
            System.err.println("Error en cargarHorarios, "+e.getMessage());
            if (!v.isEmpty())
            {
                return v;
            } else
            {
                return null;
            }
        }
    }

    /**
     * Método que da de alta horarios
     * @param id el identificador
     * @param materia identificador de la materia al que pertenece el horario
     * @param dia el dia del horario
     * @param horaInicio hora de inicio de la clase
     * @param horaFinal hora en que termina la clase
     * @param notificar si la clase debe ser o no notificada
     */
    public static void AltaHorarios(int id, int materia, String dia, String horaInicio, String horaFinal, boolean notificar)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            sql.Insertar(con, "horarios",
                    "" + id + ","
                    + materia + ",'"
                    + dia + "','"
                    + horaInicio + "','"
                    + horaFinal + "','"
                    + notificar + "'"
            );
        }
        desconecta(con);
    }

    /**
     * Método que da de baja horarios dependiendo del campo y del dato
     * @param campo "variable="
     * @param dato "'cadena'" o "numero"
     */
    public static void BajasHorarios(String campo, int dato)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            sql.Delete(con, "horarios", campo, "" + dato + "");
        }
        desconecta(con);
    }

    /**
     * Método que consulta los horarios de la bd
     * @param variable la variable por la cual se consultara
     * @param condicion la condicion que se debe cumplir
     * @return los horarios en forma de arraylist nt: las variables se envias
     * así "variable=" la condicion las cadenas "'cadena'" y numeros "1"
     */
    public static ArrayList<Horarios> ConsultaHorarios(String variable, String condicion)
    {
        Connection con = ManipulaBD.conecta();
        ArrayList<Horarios> ap = null;
        if (con != null)
        {
            Querys sql = new Querys();
            ap = ManipulaBD.cargarHorarios(sql.Seleccion(con, "*", "horarios", variable + condicion));
            desconecta(con);
        }
        return ap;
    }

    /**
     * Método que modifica los datos de la bd de materias
     *
     * @param id identificador del horario
     * @param campos los campos que seran modificados
     * @param datos los nuevos datos nt: las variables se envias así "variable="
     * la condicion las cadenas "'cadena'" y numeros "1"
     */
    public static void ModificarHorarios(int id, String campos, String datos)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            ArrayList<Horarios> ap = ManipulaBD.cargarHorarios(sql.Seleccion(con, "*", "horarios", "id=" + id + ""));
            if (ap != null)
            {
                sql.Modificar(con, "horarios", campos, datos, "id=" + id + "");
                desconecta(con);
                System.out.println("Modificado");
            }
        }
    }

}
