package Entidades;

public class Empleado {

    private int codigoemp;
    private String nombreemp;
    private String apellidoemp;
    private String dniemp;
    private String direccionemp;
    private String telefonoemp;
    private boolean estadoemp;
    private String correoemp;
    private Usuario usuario;

    public int getCodigoemp() {
        return codigoemp;
    }

    public void setCodigoemp(int codigoemp) {
        this.codigoemp = codigoemp;
    }

    public String getNombreemp() {
        return nombreemp;
    }

    public void setNombreemp(String nombreemp) {
        this.nombreemp = nombreemp;
    }

    public String getApellidoemp() {
        return apellidoemp;
    }

    public void setApellidoemp(String apellidoemp) {
        this.apellidoemp = apellidoemp;
    }

    public String getDniemp() {
        return dniemp;
    }

    public void setDniemp(String dniemp) {
        this.dniemp = dniemp;
    }

    public String getDireccionemp() {
        return direccionemp;
    }

    public void setDireccionemp(String direccionemp) {
        this.direccionemp = direccionemp;
    }

    public String getTelefonoemp() {
        return telefonoemp;
    }

    public void setTelefonoemp(String telefonoemp) {
        this.telefonoemp = telefonoemp;
    }

    public boolean isEstadoemp() {
        return estadoemp;
    }

    public void setEstadoemp(boolean estadoemp) {
        this.estadoemp = estadoemp;
    }

    public String getCorreoemp() {
        return correoemp;
    }

    public void setCorreoemp(String correoemp) {
        this.correoemp = correoemp;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

}
