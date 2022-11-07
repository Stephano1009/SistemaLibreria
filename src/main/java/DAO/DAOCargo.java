
package DAO;

import Conexion.Conexion;
import Entidades.Cargo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DAOCargo extends Conexion{
    public List<Cargo> listar() throws Exception {
        List<Cargo> cargo;
        Cargo car;
        ResultSet rs = null;
        String sql = "select car.id_cargo, car.nombre_Cargo, car.estado  "
                + "from cargo car order by car.nombre_Cargo";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            cargo = new ArrayList<>();
            while (rs.next() == true) {
                car = new Cargo();
                car.setCodigo(rs.getInt("id_cargo"));
                car.setCargo(rs.getString("nombre_Cargo"));
                car.setEstado(rs.getBoolean("estado"));              
                cargo.add(car);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return cargo;
    }
    
    public void registrar(Cargo cargo) throws Exception {
        String sql;

        sql = "INSERT INTO Cargo (Nombre_Cargo, Estado) "
                + "VALUES('" + cargo.getCargo()+ "',"
                + (cargo.isEstado() == true ? "1" : "0") + ")";

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
    
    public Cargo leer(Cargo cargo) throws Exception {
        Cargo car = null;
        ResultSet rs = null;
        String sql = "SELECT Car.Nombre_Cargo, Car.Estado FROM Cargo Car  WHERE Car.Id_Cargo = " + cargo.getCodigo();

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                car = new Cargo();
                car.setCodigo(cargo.getCodigo());
                car.setCargo(rs.getString("Nombre_Cargo"));
                car.setEstado(rs.getBoolean("Estado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return car;
    }
    
    public void actualizar(Cargo cargo) throws Exception {
        String sql;

        sql = "UPDATE Cargo SET Nombre_Cargo = '" + cargo.getCargo()
                + "', estado = " + (cargo.isEstado() == true ? "1" : "0")
                + " WHERE Id_Cargo = " + cargo.getCodigo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
    }
    
    public void eliminarCargo(Cargo car) throws Exception {
        String sql = "DELETE FROM Cargo WHERE Id_Cargo = " + car.getCodigo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }

    }
}
