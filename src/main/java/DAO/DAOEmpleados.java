package DAO;

import Conexion.Conexion;
import Entidades.Empleados;
import Entidades.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOEmpleados extends Conexion {

    public List<Empleados> listar() throws Exception {
        List<Empleados> empleados;
        Empleados emp;
        ResultSet rs = null;
        String sql = "SELECT E.ID_EMPLEADO, E.NOMBRE_EMPLEADO, E.APELLIDO_EMPLEADO, E.DNI, "
                + "E.DIRECCION,E.CORREO, E.ESTADO FROM Empleados E INNER JOIN usuario U "
                + "ON E.id_usuario = U.id_usuario "
                + "ORDER BY E.NOMBRE_EMPLEADO";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            empleados = new ArrayList<>();
            while (rs.next() == true) {
                emp = new Empleados();
                emp.setCodigoemp(rs.getInt("ID_EMPLEADO"));
                emp.setNombreemp(rs.getString("NOMBRE_EMPLEADO"));
                emp.setApellidoemp(rs.getString("APELLIDO_EMPLEADO"));
                emp.setDniemp(rs.getString("DNI"));
                emp.setDireccionemp(rs.getString("DIRECCION"));
                emp.setCorreoemp(rs.getString("CORREO"));
                emp.setEstadoemp(rs.getBoolean("ESTADO"));

                empleados.add(emp);
            }
        } catch (Exception e) {
            throw e;
        }
        return empleados;
    }

    public void registrar(Empleados empleados) throws Exception {
        String sql = "INSERT INTO Empleados(NOMBRE_EMPLEADO, APELLIDO_EMPLEADO, DNI, DIRECCION, TELEFONO, ESTADO, "
                + "CORREO, ID_USUARIO)"
                + "VALUES('" + empleados.getNombreemp() + "','"
                + empleados.getApellidoemp() + "', '"
                + empleados.getDniemp() + "', '"
                + empleados.getDireccionemp() + "', '"
                + empleados.getTelefonoemp() + "', 1,  '"
                + empleados.getCorreoemp() + "', "
                + empleados.getUsuario().getCodigo() + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public Empleados leer(Empleados empleados) throws Exception {
        Empleados empl = null;
        Usuario usu;
        ResultSet rs = null;
        String sql = "SELECT empl.ID_EMPLEADO, empl.NOMBRE_EMPLEADO, empl.APELLIDO_EMPLEADO, empl.DNI, empl.DIRECCION, empl.TELEFONO,"
                + "empl. ESTADO, empl.CORREO, empl.ID_USUARIO "
                + " FROM EMPLEADOS empl WHERE empl.ID_EMPLEADO =  " + empleados.getCodigoemp();

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                empl = new Empleados();
                empl.setCodigoemp(empleados.getCodigoemp());
                empl.setNombreemp(rs.getString("NOMBRE_EMPLEADO"));
                empl.setApellidoemp(rs.getString("APELLIDO_EMPLEADO"));
                empl.setDniemp(rs.getString("DNI"));
                empl.setDireccionemp(rs.getString("DIRECCION"));
                empl.setTelefonoemp(rs.getString("TELEFONO"));
                empl.setEstadoemp(rs.getBoolean("ESTADO"));
                empl.setCorreoemp(rs.getString("CORREO"));
                usu = new Usuario();
                usu.setCodigo(rs.getInt("ID_USUARIO"));

            }
        } catch (Exception e) {
            throw e;
        }
        return empl;
    }

    public void actualizar(Empleados empleados) throws Exception {
        String sql;

        sql = "UPDATE EMPLEADOS SET NOMBRE_EMPLEADO = '" + empleados.getNombreemp()
                + "', APELLIDO_EMPLEADO = '" + empleados.getApellidoemp()
                + "', DNI = '" + empleados.getDniemp()
                + "', DIRECCION = '" + empleados.getDireccionemp()
                + "', TELEFONO = '" + empleados.getTelefonoemp()
                + "', ESTADO = '" + (empleados.isEstadoemp() == true ? "1" : "0")
                + "', CORREO = " + empleados.getCorreoemp()
                + " , ID_USUARIO = " + empleados.getUsuario().getCodigo()
                + "  WHERE ID_EMPLEADOS = " + empleados.getCodigoemp();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarEmpleados(Empleados empl) throws Exception {
        String sql = "DELETE FROM Empleados WHERE ID_Empleado = " + empl.getCodigoemp();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }

    }
}
