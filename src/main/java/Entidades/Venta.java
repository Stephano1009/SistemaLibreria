package Entidades;

import java.util.List;

public class Venta {

    private int codigov;
    private String seriev;
    private String numerov;
    private String tipo_documentov;
    private String fechav;
    private Tipo_Pago tipopago;
    private Empleado empleados;
    private Cliente clientes;
    private boolean estado;

    private List<DetalleVenta> detalles;

    public int getCodigov() {
        return codigov;
    }

    public void setCodigov(int codigov) {
        this.codigov = codigov;
    }

    public String getSeriev() {
        return seriev;
    }

    public void setSeriev(String seriev) {
        this.seriev = seriev;
    }

    public String getNumerov() {
        return numerov;
    }

    public void setNumerov(String numerov) {
        this.numerov = numerov;
    }

    public String getTipo_documentov() {
        return tipo_documentov;
    }

    public void setTipo_documentov(String tipo_documentov) {
        this.tipo_documentov = tipo_documentov;
    }

    public Empleado getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleado empleados) {
        this.empleados = empleados;
    }

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
        this.clientes = clientes;
    }

    public String getFechav() {
        return fechav;
    }

    public void setFechav(String fechav) {
        this.fechav = fechav;
    }

    public Tipo_Pago getTipopago() {
        return tipopago;
    }

    public void setTipopago(Tipo_Pago tipopago) {
        this.tipopago = tipopago;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
    
    

}
