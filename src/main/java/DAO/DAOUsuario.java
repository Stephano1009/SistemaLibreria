package DAO;

import Conexion.Conexion;
import Entidades.Cargo;
import Entidades.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuario extends Conexion {

    public Usuario identificar(Usuario usuario) throws Exception {
        Usuario usu = null;
        ResultSet rs = null;
        String sql = "SELECT U.id_usuario, C.nombre_Cargo FROM Usuario U INNER JOIN "
                + "Cargo C ON C.id_cargo = U.id_cargo WHERE U.estado = 1 AND "
                + "U.Clave = '" + usuario.getClave() + "' AND U.usuario = '" + usuario.getUsuario() + "'";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usu = new Usuario();
                usu.setCodigo(rs.getInt("id_usuario"));
                usu.setUsuario(usuario.getUsuario());
                usu.setCargo(new Cargo());
                usu.getCargo().setCargo(rs.getString("nombre_Cargo"));
                usu.setEstado(true);
            }
        } catch (Exception e) {
            throw e;
        }
        return usu;
    }

    public List<Usuario> listar() throws Exception {
        List<Usuario> usuario;
        Usuario usu;
        ResultSet rs = null;
        String sql = "select usu.id_usuario, usu.usuario, usu.clave, usu.estado "
                + " from usuario usu order by usuario";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuario = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setCodigo(rs.getInt("id_usuario"));
                usu.setUsuario(rs.getString("usuario"));
                usu.setClave(rs.getString("clave"));
                usu.setEstado(rs.getBoolean("estado"));
                usuario.add(usu);
            }
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }

    public Usuario leer(Usuario usuario) throws Exception {
        Usuario usu = null;
        ResultSet rs = null;
        String sql = "SELECT U.ID_USUARIO, U.USUARIO, U.CLAVE, U.ESTADO FROM USUARIO U  WHERE U.ID_USUARIO,  = " + usuario.getCodigo();

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usu = new Usuario();
                usu.setCodigo(usuario.getCodigo());
                usu.setUsuario(rs.getString("USUARIO"));
                usu.setClave(rs.getString("CLAVE"));
                usu.setEstado(rs.getBoolean("ESTADO"));
            }
        } catch (Exception e) {
            throw e;
        }
        return usu;
    }

    public void actualizar(Usuario usuario) throws Exception {
        String sql;

        sql = "UPDATE USUARIO SET USUARIO = '" + usuario.getUsuario()
                + "', CLAVE = '" + usuario.getClave()
                + "', ESTADO = '" + (usuario.isEstado() == true ? "1" : "0")
                + " , ID_CARGO = " + usuario.getUsuario()
                + "  WHERE ID_USUARIO = " + usuario.getCodigo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void registrar(Usuario usuario) throws Exception {
        String sql = "INSERT INTO Usuario(Usuario, Clave, Estado, id_cargo) "
                + "VALUES('" + usuario.getUsuario() + "', '"
                + usuario.getClave() + "', "
                + (usuario.isEstado() == true ? "1" : "0") + ","
                + usuario.getCargo().getCodigo() + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarUsuario(Usuario usu) throws Exception {
        String sql = "DELETE FROM Usuario WHERE ID_USUARIO = " + usu.getCodigo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        }
    }

}
