package DAO;

import Conexion.Conexion;
import Entidades.Cliente;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOCliente extends Conexion {

    public List<Cliente> listar() throws Exception {
        List<Cliente> clientes;
        Cliente cli;
        ResultSet rs = null;
        String sql = "SELECT cli.ID_CLIENTE, cli.NOMBRE_CLIENTE,"
                + " cli.APELLIDO_CLIENTE, cli.DIRECCION_CLIENTE, cli.NUMERODOC, cli.TIPO_DOCUMENTO"
                + " FROM CLIENTE cli ORDER BY cli.NOMBRE_CLIENTE";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            clientes = new ArrayList<>();
            while (rs.next() == true) {
                cli = new Cliente();
                cli.setCodigo(rs.getInt("ID_CLIENTE"));
                cli.setNombre(rs.getString("NOMBRE_CLIENTE"));
                cli.setApellido(rs.getString("APELLIDO_CLIENTE"));
                cli.setDireccion(rs.getString("DIRECCION_CLIENTE"));
                cli.setDni(rs.getString("NUMERODOC"));
                cli.setTipodocumento(rs.getString("TIPO_DOCUMENTO"));
                clientes.add(cli);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return clientes;
    }

    public void registrar(Cliente clientes) throws Exception {
        String sql = "INSERT INTO Cliente( Nombre_Cliente, Apellido_Cliente, NUMERODOC, Direccion_Cliente, TIPO_DOCUMENTO)"
                + "VALUES( '" + clientes.getNombre() + "', '"
                + clientes.getApellido() + "', '" + clientes.getDni() + "', '"
                + clientes.getDireccion() + "', '" + clientes.getTipodocumento() + "')";
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

    public Cliente leer(Cliente clientes) throws Exception {
        Cliente cli = null;
        ResultSet rs = null;
        String sql = "SELECT cli.ID_CLIENTE, cli.NOMBRE_CLIENTE, cli.APELLIDO_CLIENTE, cli.NUMERODOC, cli.DIRECCION_CLIENTE, cli.TIPO_DOCUMENTO"
                + " FROM CLIENTE cli WHERE cli.ID_CLIENTE =  " + clientes.getCodigo();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                cli = new Cliente();
                cli.setCodigo(clientes.getCodigo());
                cli.setNombre(rs.getString("NOMBRE_CLIENTE"));
                cli.setApellido(rs.getString("APELLIDO_CLIENTE"));
                cli.setDni(rs.getString("NUMERODOC"));
                cli.setDireccion(rs.getString("DIRECCION_CLIENTE"));
                cli.setTipodocumento(rs.getString("TIPO_DOCUMENTO"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return cli;
    }

    public void actualizar(Cliente clientes) throws Exception {
        String sql;

        sql = "UPDATE CLIENTE SET NOMBRE_CLIENTE = '" + clientes.getNombre()
                + "', APELLIDO_CLIENTE = '" + clientes.getApellido()
                + "', NUMERODOC = '" + clientes.getDni()
                + "', DIRECCION_CLIENTE = '" + clientes.getDireccion()
                + "', TIPO_DOCUMENTO = '" + clientes.getTipodocumento()
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

    public void eliminarClientes(Cliente cli) throws Exception {
        String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = " + cli.getCodigo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }

    }

    public List<Cliente> cargarClientesVentas() throws Exception {
        List<Cliente> clientes;
        Cliente cli;
        ResultSet rs = null;
        String sql = "SELECT cli.ID_CLIENTE, cli.NOMBRE_CLIENTE, cli.APELLIDO_CLIENTE FROM CLIENTE cli ORDER BY cli.NOMBRE_CLIENTE";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            clientes = new ArrayList<>();
            while (rs.next() == true) {
                cli = new Cliente();
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
