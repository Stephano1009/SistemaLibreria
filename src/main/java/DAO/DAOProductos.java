package DAO;

import Conexion.Conexion;
import Entidades.Categoria;
import Entidades.Productos;
import Entidades.Proveedores;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOProductos extends Conexion {

    public List<Productos> listar() throws Exception {
        List<Productos> Productos;
        Productos pro;
        Categoria cate;
        Proveedores prov;
        ResultSet rs = null;
        String sql = "select p.ID_PRODUCTO, p.NOMBRE_PRODUCTO, P.STOCK, P.ESTADO, P.DESCRIPCION_PRODUCTO,P.CONTENIDO, P.PRECIO, "
                + "c.NOMBRE_CATEGORIA , PRO.NOMBRE_PROVEEDOR "
                + "From Productos P INNER JOIN CATEGORIAS C "
                + "on p.ID_CATEGORIA = c.ID_CATEGORIA INNER JOIN PROVEEDORES PRO "
                + "ON PRO.ID_PROVEEDOR = P.ID_PROVEEDOR "
                + "order by p.NOMBRE_PRODUCTO ";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            Productos = new ArrayList<>();
            while (rs.next() == true) {
                pro = new Productos();
                pro.setCodigopro(rs.getInt("ID_PRODUCTO"));
                pro.setNombrepro(rs.getString("NOMBRE_PRODUCTO"));
                pro.setStockpro(rs.getInt("STOCK"));
                pro.setEstadopro(rs.getBoolean("ESTADO"));
                pro.setDescripcionpro(rs.getString("DESCRIPCION_PRODUCTO"));
                pro.setContenidopro(rs.getString("CONTENIDO"));
                pro.setPreciopro(rs.getDouble("PRECIO"));
                cate = new Categoria();
                cate.setCategoria(rs.getString("NOMBRE_CATEGORIA"));
                pro.setCategoriapro(cate);
                prov = new Proveedores();
                prov.setNombreProve(rs.getString("NOMBRE_PROVEEDOR"));
                pro.setProveedorpro(prov);
                Productos.add(pro);
            }
        } catch (Exception e) {
            throw e;
        }
        return Productos;
    }

    public void registrar(Productos productos) throws Exception {
        String sql = "INSERT INTO Productos(NOMBRE_PRODUCTO, DESCRIPCION_PRODUCTO, CONTENIDO, PRECIO, STOCK, ESTADO, "
                + "ID_CATEGORIA, ID_PROVEEDOR)"
                + "VALUES( '" + productos.getNombrepro() + "','" + productos.getDescripcionpro() + "', '"
                + productos.getContenidopro() + "', " + productos.getPreciopro() + ", "
                + productos.getStockpro() + ", 1, " + productos.getCategoriapro().getCodigo() + ", "
                + productos.getProveedorpro().getCodigoProve() + ")";

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public Productos leer(Productos productos) throws Exception {
        Productos pro = null;
        Categoria cat;
        Proveedores prove;
        ResultSet rs = null;
        String sql = "SELECT p.ID_PRODUCTO, p.NOMBRE_PRODUCTO, p.DESCRIPCION_PRODUCTO, p.CONTENIDO, p.PRECIO, p.ESTADO, "
                + "p.STOCK, p.ID_CATEGORIA, p.ID_PROVEEDOR FROM Productos p WHERE p.ID_PRODUCTO =  " + productos.getCodigopro();

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                pro = new Productos();
                pro.setCodigopro(productos.getCodigopro());
                pro.setNombrepro(rs.getString("NOMBRE_PRODUCTO"));
                pro.setDescripcionpro(rs.getString("DESCRIPCION_PRODUCTO"));
                pro.setContenidopro(rs.getString("CONTENIDO"));
                pro.setPreciopro(rs.getDouble("PRECIO"));
                pro.setEstadopro(rs.getBoolean("ESTADO"));
                pro.setStockpro(rs.getInt("STOCK"));
                cat = new Categoria();
                cat.setCodigo(rs.getInt("ID_CATEGORIA"));
                pro.setCategoriapro(cat);
                prove = new Proveedores();
                prove.setCodigoProve(rs.getInt("ID_PROVEEDOR"));
                pro.setProveedorpro(prove);

            }
        } catch (Exception e) {
            throw e;
        }
        return pro;
    }

    public void actualizar(Productos productos) throws Exception {
        String sql;

        sql = "UPDATE Productos SET NOMBRE_PRODUCTO = '" + productos.getNombrepro()
                + "', DESCRIPCION_PRODUCTO = '" + productos.getDescripcionpro()
                + "', CONTENIDO = '" + productos.getContenidopro()
                + "', PRECIO = " + productos.getPreciopro()
                + ", ESTADO = " + (productos.isEstadopro() == true ? "1" : "0")
                + ", STOCK = " + productos.getStockpro()
                + ", ID_CATEGORIA = " + productos.getCategoriapro().getCodigo()
                + ", ID_PROVEEDOR = " + productos.getProveedorpro().getCodigoProve()
                + " WHERE ID_PRODUCTO = " + productos.getCodigopro();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarProductos(Productos pro) throws Exception {
        String sql = "DELETE FROM Productos WHERE ID_PRODUCTO = " + pro.getCodigopro();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }

    }
}
