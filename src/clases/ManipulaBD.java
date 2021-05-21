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

    public static void desconecta(Connection conn)
    {
        con.desconectar(conn);
    }

    /*
    Avion
    
    nuas 0
    bole 1
    dest 2
    
    nuas bole dest
    1    5    mexico
    3    7    francia
    1,5,mexico,3,7,francia
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
                    String color = ((String) reg.get(i + 5)).replaceAll("|", ",");
                    System.out.println(color + " AQUI LLEGUE");

                    Materias obj = new Materias(id, semestre, grupo, nombreMaestro, nombreMateria, color);
                    v.add(obj);
                }
            }
            return v;
        } catch (Exception e)
        {
            System.out.println("Error al crear objetos");
            if (v != null)
            {
                return v;
            } else
            {
                return null;
            }
        }
    }

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
                    + "'" + color.replaceAll(",", "|") + "'"
            );
        }
        desconecta(con);
    }

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
     * 
     * @param id
     * @param campos 
     * @param datos
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

    public static ArrayList<Horarios> cargarHorarios(ArrayList<Object> reg)
    {
        ArrayList<Horarios> v = new ArrayList<>();
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
            System.out.println("Error al crear objetos");
            if (v != null)
            {
                return v;
            } else
            {
                return null;
            }
        }
    }

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
