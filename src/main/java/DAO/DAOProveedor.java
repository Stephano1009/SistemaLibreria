package DAO;

import Conexion.Conexion;
import Entidades.Proveedor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOProveedor extends Conexion {

    public List<Proveedor> listar() throws Exception {
        List<Proveedor> proveedores;
        Proveedor prove;
        ResultSet rs = null;
        String sql = "select Prov.ID_PROVEEDOR, Prov.NOMBRE_PROVEEDOR, Prov.RUC, Prov.TELEFONO_PROVEEDOR "
                + ", Prov.ESTADO from PROVEEDOR Prov order by NOMBRE_PROVEEDOR";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            proveedores = new ArrayList<>();
            while (rs.next() == true) {
                prove = new Proveedor();
                prove.setCodigoProve(rs.getInt("ID_PROVEEDOR"));
                prove.setNombreProve(rs.getString("NOMBRE_PROVEEDOR"));
                prove.setRucProvee(rs.getString("RUC"));
                prove.setTelefonoProve(rs.getString("TELEFONO_PROVEEDOR"));
                prove.setEstadoProve(rs.getBoolean("ESTADO"));
                proveedores.add(prove);
            }
        } catch (Exception e) {
            throw e;
        }
        return proveedores;
    }

    public void registrar(Proveedor proveedores) throws Exception {
        String sql = "INSERT INTO Proveedor(Ruc, Nombre_Proveedor, Telefono_Proveedor, Estado) "
                + "VALUES('" + proveedores.getRucProvee() + "', '"
                + proveedores.getNombreProve() + "', '"
                + proveedores.getTelefonoProve() + "', "
                + (proveedores.isEstadoProve() == true ? "1" : "0") + ")";

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public Proveedor leer(Proveedor proveedores) throws Exception {
        Proveedor prove = null;
        ResultSet rs = null;
        String sql = "select RUC,NOMBRE_PROVEEDOR,TELEFONO_PROVEEDOR, ESTADO FROM PROVEEDOR WHERE ID_PROVEEDOR = "
                + proveedores.getCodigoProve();

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                prove = new Proveedor();
                prove.setCodigoProve(proveedores.getCodigoProve());
                prove.setRucProvee(rs.getString("RUC"));
                prove.setNombreProve(rs.getString("NOMBRE_PROVEEDOR"));
                prove.setTelefonoProve(rs.getString("TELEFONO_PROVEEDOR"));
                prove.setEstadoProve(rs.getBoolean("ESTADO"));

            }
        } catch (Exception e) {
            throw e;
        }
        return prove;
    }

    public void actualizar(Proveedor proveedores) throws Exception {
        String sql;

        sql = "update PROVEEDOR set RUC = '" + proveedores.getRucProvee()
                + "', NOMBRE_PROVEEDOR = '" + proveedores.getNombreProve()
                + "', TELEFONO_PROVEEDOR = '" + proveedores.getTelefonoProve()
                + "', ESTADO = " + (proveedores.isEstadoProve() == true ? "1" : "0")
                + " WHERE ID_PROVEEDOR = " + proveedores.getCodigoProve();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarProveedores(Proveedor prove) throws Exception {
        String sql = "DELETE FROM PROVEEDOR WHERE ID_PROVEEDOR = " + prove.getCodigoProve();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }

    }
}
