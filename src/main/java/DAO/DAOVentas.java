package DAO;

import Conexion.Conexion;
import Entidades.Clientes;
import Entidades.DetalleVentas;
import Entidades.Empleados;
import Entidades.Productos;
import Entidades.Ventas;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOVentas extends Conexion {

    public void registrar(Ventas venta) throws Exception {
        ResultSet rs = null;
        int codigoVenta;
        String sql = "INSERT INTO Ventas(SERIE, NUMERO, TIPODOCUMENTO, FECHA, "
                + "IDTIPOPAGO, IDEMPLEADO, IDCLIENTE, ESTADO)"
                + "VALUES('" + venta.getSeriev() + "', '" + venta.getNumerov()
                + "', '" + venta.getTipo_documentov() + "', '" + venta.getFechav() + "', 4, 3, " + venta.getClientes().getCodigo() + ", 1)";

        try {
            this.conectar(true);
            this.ejecutarOrden(sql);
            rs = this.ejecutarOrdenDatos("SELECT @@IDENTITY AS Codigo"); //obtener codigo generado
            rs.next();
            codigoVenta = rs.getInt("Codigo");
            rs.close();
            for (DetalleVentas detalle : venta.getDetalles()) {
                sql = "INSERT INTO DETALLE_VENTA(ID_VENTA, ID_PRODUCTO, CANTIDAD, PRECIOVENTA) "
                        + "VALUES(" + codigoVenta + ", " + detalle.getProductos().getCodigopro() + ", "
                        + detalle.getCantidadventas() + ", " + detalle.getPrecioventaventas() + ")";
                this.ejecutarOrden(sql);//INSERTA EL DETALLE DE LA VENTA
                sql = "UPDATE productos SET stock = (stock - " + detalle.getCantidadventas() + ") "
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
        String sql = "SELECT TOP 1 NUMERO FROM VENTAS WHERE TIPODOCUMENTO = '" + tipo + "' ORDER BY IDVENTA DESC";

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

    public Productos obtenerProducto(String valor_buscar, int tipo_buscar) throws Exception {
        Productos producto = null;
        ResultSet rs = null;
        String sql = "SELECT ID_PRODUCTO, NOMBRE_PRODUCTO, PRECIO, STOCK FROM Productos WHERE ";
        if (tipo_buscar == 1) { // buscar por nombre
            sql += " NOMBRE_PRODUCTO LIKE '" + valor_buscar + "'";
        } else {
            sql += " ID_PRODUCTO = " + valor_buscar + "";
        }
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                producto = new Productos();
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

    public List<Ventas> listar() throws Exception {
        List<Ventas> ventas;
        Ventas ven;
        Empleados emp;
        Clientes cli;
        ResultSet rs = null;
        String sql = "SELECT V.IDVENTA, V.TIPODOCUMENTO, V.FECHA, E.NOMBRE_EMPLEADO, C.NOMBRE_CLIENTE, V.ESTADO FROM VENTAS V INNER JOIN CLIENTES C \n"
                + "ON C.ID_CLIENTE = V.IDCLIENTE INNER JOIN Empleados E ON\n"
                + "E.ID_EMPLEADO = V.IDEMPLEADO INNER JOIN TIPO_PAGO TP\n"
                + "ON TP.ID_TIPO_PAGO = V.IDTIPOPAGO";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            ventas = new ArrayList<>();
            while (rs.next() == true) {
                ven = new Ventas();
                ven.setCodigov(rs.getInt("IDVENTA"));
                ven.setTipo_documentov(rs.getString("TIPODOCUMENTO"));
                ven.setFechav(rs.getString("FECHA"));
                /*=====================================================*/
                emp = new Empleados();
                emp.setNombreemp(rs.getString("NOMBRE_EMPLEADO"));
                ven.setEmpleados(emp);
                /*=====================================================*/
                cli = new Clientes();
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

    public List<DetalleVentas> listarDetalles(Ventas v) throws Exception {
        List<DetalleVentas> detalles;
        DetalleVentas det;
        ResultSet rs = null;
        String sql = "SELECT P.NOMBRE_PRODUCTO, DV.CANTIDAD, DV.PRECIOVENTA "
                + "FROM VENTAS V INNER JOIN DETALLE_VENTA DV ON "
                + "DV.ID_VENTA = V.IDVENTA INNER JOIN Productos P ON "
                + "P.ID_PRODUCTO = DV.ID_PRODUCTO "
                + "WHERE V.IDVENTA = '" + v.getCodigov() + "'"
                + "GROUP BY  P.NOMBRE_PRODUCTO, DV.CANTIDAD, DV.PRECIOVENTA";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            detalles = new ArrayList<>();
            while (rs.next() == true) {
                det = new DetalleVentas();
                det.setProductos(new Productos());
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
