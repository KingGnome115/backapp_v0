package basededatos;

import clases.CompanierosObj;
import clases.Materias;
import java.sql.Connection;
import java.util.ArrayList;
import poo.bd.Conexion;
import poo.bd.Querys;

/**
 *
 * @author Kevin
 */
public class ManipulaBD
{

    private static Conexion con = new Conexion();

    public static Connection conecta()
    {
        try
        {
            return con.Conecta("localhost:3306", "backapp", "root", "", 2);
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

    public static ArrayList<CompanierosObj> cargarCom(ArrayList<Object> reg)
    {
        ArrayList<CompanierosObj> v = new ArrayList<>();
        try
        {
            for (int i = 0; i < reg.size(); i += 4)
            {
                String idS = "";
                idS = (String) reg.get(i);
                idS = idS.trim();
                if (idS != "" && idS != " ")
                {
                    int id = Integer.parseInt(idS.trim());
                    String nombre = ((String) reg.get(i + 1)).trim();
                    String email = ((String) reg.get(i + 2)).trim();
                    String es = ((String) reg.get(i + 3)).trim();
                    boolean estatus = Boolean.parseBoolean(es);
                    CompanierosObj obj = new CompanierosObj(id, nombre, email, estatus);
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

    /**
     * Método para dar de alta aun compañero en la bd
     *
     * @param nombre Nombre de la persona
     * @param email Correo electronico de la persona
     */
    public static void AltaCompanieros(int id, String nombre, String email, boolean estatus)
    {
        Connection con = ManipulaBD.conecta();
        String estatusS = String.valueOf(estatus);
        if (con != null)
        {
            Querys sql = new Querys();
            sql.Insertar(con, "companieros",
                    "" + id + ",'"
                    + nombre + "','"
                    + email + "','"
                    + estatusS + "'"
            );
        }
        desconecta(con);
    }

    /**
     * Método para dar de baja a un compañero de la bd
     *
     * @param id Identificador de la persona
     */
    public static void BajasCompanieros(int id)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            sql.Delete(con, "companieros", "id", "" + id + "");
        }
        desconecta(con);
    }

    /**
     * Método para traer los datos requeridos de la bd
     *
     * @param variable con ella se va a buscar en la base ejemplo "id="
     * importate poner el '='
     * @param condicion la condicion por la cual se seleccionara un dato ejemplo
     * '1' Tener en cuenta que si quiero traer toda la base debo mandar esto
     * Variable = "id!=" condicion "-1"
     * @return el o los datos seleccionados en la bd
     */
    public static ArrayList<CompanierosObj> ConsultaCompanieros(String variable, String condicion)
    {
        Connection con = ManipulaBD.conecta();
        ArrayList<CompanierosObj> ap = null;
        if (con != null)
        {
            Querys sql = new Querys();
            ap = ManipulaBD.cargarCom(sql.Seleccion(con, "*", "companieros", variable + condicion));
            desconecta(con);
        }
        return ap;
    }

    /**
     * Método para modificar los datos de los Companieros
     *
     * @param id identificador de la persona
     * @param campos los campos que seran cambiados "Nombre,Email"
     * @param datos los nuevos datos
     */
    public static void ModificarCompanieros(int id, String campos, String datos)
    {
        Connection con = ManipulaBD.conecta();
        if (con != null)
        {
            Querys sql = new Querys();
            ArrayList<CompanierosObj> ap = ManipulaBD.cargarCom(sql.Seleccion(con, "*", "companieros", "id=" + id + ""));
            if (ap != null)
            {
                sql.Modificar(con, "companieros", campos, datos, "id=" + id + "");
                desconecta(con);
                System.out.println("Modificado");
            }
        }
    }

    public static ArrayList<Materias> cargarMaterias(ArrayList<Object> reg)
    {
        ArrayList<Materias> v = new ArrayList<>();
        try
        {
            for (int i = 0; i < reg.size(); i += 5)
            {
                String idS = "";
                idS = (String) reg.get(i);
                idS = idS.trim();
                if (idS != "" && idS != " ")
                {
                    int id = Integer.parseInt(idS.trim());
                    String libretaS = ((String) reg.get(i + 1)).trim();
                    int libreta = Integer.parseInt(libretaS);
                    String semestreS = ((String) reg.get(i + 2)).trim();
                    int semestre = Integer.parseInt(semestreS);
                    String nombreMaestro = ((String) reg.get(i + 3)).trim();
                    String nombreMateria = ((String) reg.get(i + 4)).trim();
                    Materias obj = new Materias(id, libreta, semestre, nombreMaestro, nombreMateria);
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
}
