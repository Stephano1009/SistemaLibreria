
package Entidades;

public class DetalleVenta {
    private int codigoventas;
    private int cantidadventas;
    private double Precioventaventas;
    private Venta venta;
    private Producto productos;
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
