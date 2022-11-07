
package Entidades;

public class DetalleVentas {
    private int codigoventas;
    private int cantidadventas;
    private double Precioventaventas;
    private Ventas venta;
    private Productos productos;
    private double monto;

    public int getCodigoventas() {
        return codigoventas;
    }

    public void setCodigoventas(int codigoventas) {
        this.codigoventas = codigoventas;
    }

    public int getCantidadventas() {
        return cantidadventas;
    }

    public void setCantidadventas(int cantidadventas) {
        this.cantidadventas = cantidadventas;
    }

    public double getPrecioventaventas() {
        return Precioventaventas;
    }

    public void setPrecioventaventas(double precioventaventas) {
        Precioventaventas = precioventaventas;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
