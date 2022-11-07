package DAO;

import Conexion.Conexion;
import Entidades.Clientes;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOClientes extends Conexion {

    public List<Clientes> listar() throws Exception {
        List<Clientes> clientes;
        Clientes cli;
        ResultSet rs = null;
        String sql = "SELECT cli.ID_CLIENTE, cli.NOMBRE_CLIENTE,"
                + " cli.APELLIDO_CLIENTE, cli.DIRECCION_CLIENTE, cli.NUMERODOC"
                + " FROM CLIENTES cli ORDER BY cli.NOMBRE_CLIENTE";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            clientes = new ArrayList<>();
            while (rs.next() == true) {
                cli = new Clientes();
                cli.setCodigo(rs.getInt("ID_CLIENTE"));
                cli.setNombre(rs.getString("NOMBRE_CLIENTE"));
                cli.setApellido(rs.getString("APELLIDO_CLIENTE"));
                cli.setDireccion(rs.getString("DIRECCION_CLIENTE"));
                cli.setDni(rs.getString("NUMERODOC"));

                clientes.add(cli);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return clientes;
    }

    public void registrar(Clientes clientes) throws Exception {
        String sql = "INSERT INTO Clientes( Nombre_Cliente, Apellido_Cliente, NUMERODOC, Direccion_Cliente)"
                + "VALUES( '" + clientes.getNombre() + "', '"
                + clientes.getApellido() + "', '" + clientes.getDni() + "', '"
                + clientes.getDireccion() + "')";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
    }

    public Clientes leer(Clientes clientes) throws Exception {
        Clientes cli = null;
        ResultSet rs = null;
        String sql = "SELECT cli.ID_CLIENTE, cli.NOMBRE_CLIENTE, cli.APELLIDO_CLIENTE, cli.NUMERODOC, cli.DIRECCION_CLIENTE, cli.TIPODOCUMENTO "
                + " FROM CLIENTES cli WHERE cli.ID_CLIENTE =  " + clientes.getCodigo();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                cli = new Clientes();
                cli.setCodigo(clientes.getCodigo());
                cli.setNombre(rs.getString("NOMBRE_CLIENTE"));
                cli.setApellido(rs.getString("APELLIDO_CLIENTE"));
                cli.setDni(rs.getString("NUMERODOC"));
                cli.setDireccion(rs.getString("DIRECCION_CLIENTE"));
                cli.setTipodocumento(rs.getString("TIPODOCUMENTO"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return cli;
    }

    public void actualizar(Clientes clientes) throws Exception {
        String sql;

        sql = "UPDATE CLIENTES SET NOMBRE_CLIENTE = '" + clientes.getNombre()
                + "', APELLIDO_CLIENTE = '" + clientes.getApellido()
                + "', NUMERODOC = '" + clientes.getDni()
                + "', DIRECCION_CLIENTE = '" + clientes.getDireccion()
                + "' WHERE ID_CLIENTE = " + clientes.getCodigo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
    }

    public void eliminarClientes(Clientes cli) throws Exception {
        String sql = "DELETE FROM CLIENTES WHERE ID_CLIENTE = " + cli.getCodigo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }

    }

    public List<Clientes> cargarClientesVentas() throws Exception {
        List<Clientes> clientes;
        Clientes cli;
        ResultSet rs = null;
        String sql = "SELECT cli.ID_CLIENTE, cli.NOMBRE_CLIENTE, cli.APELLIDO_CLIENTE FROM CLIENTES cli ORDER BY cli.NOMBRE_CLIENTE";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            clientes = new ArrayList<>();
            while (rs.next() == true) {
                cli = new Clientes();
                cli.setCodigo(rs.getInt("ID_CLIENTE"));
                cli.setNombre(rs.getString("NOMBRE_CLIENTE"));
                cli.setApellido(rs.getString("APELLIDO_CLIENTE"));
                clientes.add(cli);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return clientes;
    }
}
