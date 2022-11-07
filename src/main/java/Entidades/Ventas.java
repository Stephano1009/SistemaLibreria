package Entidades;

import java.util.List;

public class Ventas {

    private int codigov;
    private String seriev;
    private String numerov;
    private String tipo_documentov;
    private String fechav;
    private Tipo_Pago tipopago;
    private Empleados empleados;
    private Clientes clientes;
    private boolean estado;

    private List<DetalleVentas> detalles;

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

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
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

    public List<DetalleVentas> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentas> detalles) {
        this.detalles = detalles;
    }
    
    

}
