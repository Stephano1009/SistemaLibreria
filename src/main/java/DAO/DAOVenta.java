package DAO;

import Conexion.Conexion;
import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Empleado;
import Entidades.Producto;
import Entidades.Venta;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOVenta extends Conexion {

    public void registrar(Venta venta) throws Exception {
        ResultSet rs = null;
        int codigoVenta;
        String sql = "INSERT INTO Venta(SERIE, NUMERO, TIPO_DOCUMENTO, FECHA, "
                + "ID_TIPO_PAGO, ID_EMPLEADO, ID_CLIENTE, ESTADO)"
                + "VALUES('" + venta.getSeriev() + "', '" + venta.getNumerov()
                + "', '" + venta.getTipo_documentov() + "', '" + venta.getFechav() + "'," + venta.getTipopago().getCodigopag() + ", 1, " + venta.getClientes().getCodigo() + ", 1)";

        try {
            this.conectar(true);
            this.ejecutarOrden(sql);
            rs = this.ejecutarOrdenDatos("SELECT @@IDENTITY AS Codigo"); //obtener codigo generado
            rs.next();
            codigoVenta = rs.getInt("Codigo");
            rs.close();
            for (DetalleVenta detalle : venta.getDetalles()) {
                sql = "INSERT INTO DETALLE_VENTA(ID_VENTA, ID_PRODUCTO, CANTIDAD, PRECIOVENTA) "
                        + "VALUES(" + codigoVenta + ", " + detalle.getProductos().getCodigopro() + ", "
                        + detalle.getCantidadventas() + ", " + detalle.getPrecioventaventas() + ")";
                this.ejecutarOrden(sql);//INSERTA EL DETALLE DE LA VENTA
                sql = "UPDATE producto SET stock = (stock - " + detalle.getCantidadventas() + ") "
                        + "WHERE id_producto = " + detalle.getProductos().getCodigopro();
                this.ejecutarOrden(sql); //ACTUALIZA EL STOCK DE PRODUDCTOS
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public int obtenerCorrelativo(String tipo) throws Exception {
        int correlativo = 0;
        ResultSet rs = null;
        String sql = "SELECT TOP 1 NUMERO FROM VENTA WHERE TIPO_DOCUMENTO = '" + tipo + "' ORDER BY ID_VENTA DESC";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                correlativo = rs.getInt("NUMERO");
            }
        } catch (Exception e) {
            throw e;
        }
        return correlativo;
    }

    public Producto obtenerProducto(String valor_buscar, int tipo_buscar) throws Exception {
        Producto producto = null;
        ResultSet rs = null;
        String sql = "SELECT ID_PRODUCTO, NOMBRE_PRODUCTO, PRECIO, STOCK FROM Producto WHERE ";
        if (tipo_buscar == 1) { // buscar por nombre
            sql += " NOMBRE_PRODUCTO LIKE '" + valor_buscar + "'";
        } else {
            sql += " ID_PRODUCTO = " + valor_buscar + "";
        }
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                producto = new Producto();
                producto.setCodigopro(rs.getInt("ID_PRODUCTO"));
                producto.setNombrepro(rs.getString("NOMBRE_PRODUCTO"));
                producto.setPreciopro(rs.getDouble("PRECIO"));
                producto.setStockpro(rs.getInt("STOCK"));
            }
        } catch (Exception e) {
            throw e;
        }
        return producto;
    }

    public List<Venta> listar() throws Exception {
        List<Venta> ventas;
        Venta ven;
        Empleado emp;
        Cliente cli;
        ResultSet rs = null;
        String sql = "SELECT V.ID_VENTA, V.TIPO_DOCUMENTO, V.FECHA, E.NOMBRE_EMPLEADO, C.NOMBRE_CLIENTE, V.ESTADO FROM VENTA V INNER JOIN CLIENTE C \n"
                + "ON C.ID_CLIENTE = V.ID_CLIENTE INNER JOIN Empleado E ON "
                + "E.ID_EMPLEADO = V.ID_EMPLEADO INNER JOIN TIPO_PAGO TP "
                + "ON TP.ID_TIPO_PAGO = V.ID_TIPO_PAGO";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            ventas = new ArrayList<>();
            while (rs.next() == true) {
                ven = new Venta();
                ven.setCodigov(rs.getInt("ID_VENTA"));
                ven.setTipo_documentov(rs.getString("TIPO_DOCUMENTO"));
                ven.setFechav(rs.getString("FECHA"));
                /*=====================================================*/
                emp = new Empleado();
                emp.setNombreemp(rs.getString("NOMBRE_EMPLEADO"));
                ven.setEmpleados(emp);
                /*=====================================================*/
                cli = new Cliente();
                cli.setNombre(rs.getString("NOMBRE_CLIENTE"));
                ven.setClientes(cli);
                ven.setEstado(rs.getBoolean("ESTADO"));
                ventas.add(ven);
            }
        } catch (Exception e) {
            throw e;
        }
        return ventas;
    }

    public List<DetalleVenta> listarDetalles(Venta v) throws Exception {
        List<DetalleVenta> detalles;
        DetalleVenta det;
        ResultSet rs = null;
        String sql = "SELECT P.NOMBRE_PRODUCTO, DV.CANTIDAD, DV.PRECIOVENTA "
                + "FROM VENTA V INNER JOIN DETALLE_VENTA DV ON "
                + "DV.ID_VENTA = V.ID_VENTA INNER JOIN Producto P ON "
                + "P.ID_PRODUCTO = DV.ID_PRODUCTO "
                + "WHERE V.ID_VENTA = '" + v.getCodigov() + "'"
                + "GROUP BY  P.NOMBRE_PRODUCTO, DV.CANTIDAD, DV.PRECIOVENTA";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            detalles = new ArrayList<>();
            while (rs.next() == true) {
                det = new DetalleVenta();
                det.setProductos(new Producto());
                det.getProductos().setNombrepro(rs.getString("NOMBRE_PRODUCTO"));
                det.setCantidadventas(rs.getInt("CANTIDAD"));
                det.setPrecioventaventas(rs.getDouble("PRECIOVENTA"));
                detalles.add(det);
            }
        } catch (Exception e) {
            throw e;
        }
        return detalles;
    }
}
