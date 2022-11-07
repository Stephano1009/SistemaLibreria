package DAO;

import Conexion.Conexion;
import Entidades.Tipo_Pago;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOTipo_Pago extends Conexion {

    public List<Tipo_Pago> listar() throws Exception {
        List<Tipo_Pago> Tipo_Pago;
        Tipo_Pago tp;
        ResultSet rs = null;
        String sql = "select tp.ID_TIPO_PAGO, tp.NOMBRE_TIPO_PAGO, tp.ESTADO "
                + "from TIPO_PAGO tp order by NOMBRE_TIPO_PAGO";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            Tipo_Pago = new ArrayList<>();
            while (rs.next() == true) {
                tp = new Tipo_Pago();
                tp.setCodigopag(rs.getInt("ID_TIPO_PAGO"));
                tp.setNombretipopag(rs.getString("NOMBRE_TIPO_PAGO"));
                tp.setEstadopag(rs.getBoolean("ESTADO"));
                Tipo_Pago.add(tp);
            }
        } catch (Exception e) {
            throw e;
        }
        return Tipo_Pago;
    }

    public void actualizar(Tipo_Pago tipo_pago) throws Exception {
        String sql;

        sql = "UPDATE Tipo_Pago SET NOMBRE_TIPO_PAGO = '" + tipo_pago.getNombretipopag()
                + "', ESTADO = " + (tipo_pago.isEstadopag() == true ? "1" : "0")
                + " WHERE Id_TIPO_PAGO = " + tipo_pago.getCodigopag();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarTipo_Pago(Tipo_Pago tp) throws Exception {
        String sql = "DELETE FROM Tipo_Pago WHERE Id_Tipo_Pago = " + tp.getCodigopag();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }

    }

    public void registrar(Tipo_Pago tipo_pago) throws Exception {
        String sql;

        sql = "INSERT INTO TIPO_PAGO (NOMBRE_TIPO_PAGO, ESTADO) "
                + "VALUES('" + tipo_pago.getNombretipopag() + "',"
                + (tipo_pago.isEstadopag() == true ? "1" : "0") + ")";

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public Tipo_Pago leer(Tipo_Pago tipo_pago) throws Exception {
        Tipo_Pago tp = null;
        ResultSet rs = null;
        String sql = "SELECT TP.NOMBRE_TIPO_PAGO, TP.Estado FROM TIPO_PAGO TP  WHERE TP.Id_TIPO_PAGO = " + tipo_pago.getCodigopag();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                tp = new Tipo_Pago();
                tp.setCodigopag(tipo_pago.getCodigopag());
                tp.setNombretipopag(rs.getString("NOMBRE_TIPO_PAGO"));
                tp.setEstadopag(rs.getBoolean("Estado"));
            }
        } catch (Exception e) {
            throw e;
        }
        return tp;
    }
}
