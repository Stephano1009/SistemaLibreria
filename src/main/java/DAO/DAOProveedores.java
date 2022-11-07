package DAO;

import Conexion.Conexion;
import Entidades.Proveedores;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOProveedores extends Conexion {

    public List<Proveedores> listar() throws Exception {
        List<Proveedores> proveedores;
        Proveedores prove;
        ResultSet rs = null;
        String sql = "select Prov.ID_PROVEEDOR, Prov.NOMBRE_PROVEEDOR, Prov.RUC, Prov.TELEFONO_PROVEEDOR "
                + ", Prov.ESTADO from PROVEEDORES Prov order by NOMBRE_PROVEEDOR";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            proveedores = new ArrayList<>();
            while (rs.next() == true) {
                prove = new Proveedores();
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

    public void registrar(Proveedores proveedores) throws Exception {
        String sql = "INSERT INTO Proveedores(Ruc, Nombre_Proveedor, Telefono_Proveedor, Estado) "
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

    public Proveedores leer(Proveedores proveedores) throws Exception {
        Proveedores prove = null;
        ResultSet rs = null;
        String sql = "select RUC,NOMBRE_PROVEEDOR,TELEFONO_PROVEEDOR, ESTADO FROM PROVEEDORES WHERE ID_PROVEEDOR = "
                + proveedores.getCodigoProve();

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                prove = new Proveedores();
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

    public void actualizar(Proveedores proveedores) throws Exception {
        String sql;

        sql = "update PROVEEDORES set RUC = '" + proveedores.getRucProvee()
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

    public void eliminarProveedores(Proveedores prove) throws Exception {
        String sql = "DELETE FROM PROVEEDORES WHERE ID_PROVEEDOR = " + prove.getCodigoProve();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }

    }
}
