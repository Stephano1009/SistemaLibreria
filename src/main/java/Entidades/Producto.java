
package Entidades;

public class Producto {
    private int codigopro;
    private String nombrepro;
    private String descripcionpro;
    private String contenidopro;
    private double preciopro;
    private boolean estadopro;
    private int stockpro;
    private Categoria categoriapro;
    private Proveedor proveedorpro;

    public int getCodigopro() {
        return codigopro;
    }

    public void setCodigopro(int codigopro) {
        this.codigopro = codigopro;
    }

    public String getNombrepro() {
        return nombrepro;
    }

    public void setNombrepro(String nombrepro) {
        this.nombrepro = nombrepro;
    }

    public String getDescripcionpro() {
        return descripcionpro;
    }

    public void setDescripcionpro(String descripcionpro) {
        this.descripcionpro = descripcionpro;
    }

    public String getContenidopro() {
        return contenidopro;
    }

    public void setContenidopro(String contenidopro) {
        this.contenidopro = contenidopro;
    }

    
    public double getPreciopro() {
        return preciopro;
    }

    public void setPreciopro(double preciopro) {
        this.preciopro = preciopro;
    }

    public boolean isEstadopro() {
        return estadopro;
    }

    public void setEstadopro(boolean estadopro) {
        this.estadopro = estadopro;
    }

    public int getStockpro() {
        return stockpro;
    }

    public void setStockpro(int stockpro) {
        this.stockpro = stockpro;
    }

    public Categoria getCategoriapro() {
        return categoriapro;
    }

    public void setCategoriapro(Categoria categoriapro) {
        this.categoriapro = categoriapro;
    }

    public Proveedor getProveedorpro() {
        return proveedorpro;
    }

    public void setProveedorpro(Proveedor proveedorpro) {
        this.proveedorpro = proveedorpro;
    }

    
    
    
    
}
