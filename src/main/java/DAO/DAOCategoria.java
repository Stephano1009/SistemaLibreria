package DAO;

import Conexion.Conexion;
import Entidades.Categoria;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOCategoria extends Conexion {

    public void registrar(Categoria categoria) throws Exception {
        String sql;

        sql = "INSERT INTO Categoria (Nombre_Categoria, Estado) "
                + "VALUES('" + categoria.getCategoria() + "',"
                + (categoria.isEstado() == true ? "1" : "0") + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
    }

    public void actualizar(Categoria categoria) throws Exception {
        String sql;

        sql = "UPDATE Categoria SET Nombre_Categoria = '" + categoria.getCategoria()
                + "', estado = " + (categoria.isEstado() == true ? "1" : "0")
                + " WHERE Id_Categoria = " + categoria.getCodigo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
    }

    public Categoria leer(Categoria categoria) throws Exception {
        Categoria cat = null;
        ResultSet rs = null;
        String sql = "SELECT C.Nombre_Categoria, C.Estado FROM Categoria C  WHERE C.Id_Categoria = " + categoria.getCodigo();

        try {
             this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                cat = new Categoria();
                cat.setCodigo(categoria.getCodigo());
                cat.setCategoria(rs.getString("Nombre_Categoria"));
                cat.setEstado(rs.getBoolean("Estado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return cat;
    }

    public List<Categoria> listar() throws Exception {
        List<Categoria> categorias;
        Categoria cat;
        ResultSet rs = null;
        String sql = "SELECT C.Id_Categoria, C.Nombre_Categoria, C.Estado FROM Categoria C ORDER BY C.Id_Categoria";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            categorias = new ArrayList<>();
            while (rs.next() == true) {
                cat = new Categoria();
                cat.setCodigo(rs.getInt("Id_Categoria"));
                cat.setCategoria(rs.getString("Nombre_Categoria"));
                cat.setEstado(rs.getBoolean("Estado"));

                categorias.add(cat);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
        return categorias;
    }

    public void cambiarVigencia(Categoria cate) throws Exception {
        String sql = "UPDATE Categoria SET Estado = "
                + (cate.isEstado() == true ? "1" : "0")
                + " WHERE Id_Categoria = " + cate.getCodigo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }
    }

    public void eliminarCategoria(Categoria cate) throws Exception {
        String sql = "DELETE FROM Categoria WHERE Id_Categoria = " + cate.getCodigo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);//INSERT, UPDATE, DELETE
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(true);
        }

    }
}
