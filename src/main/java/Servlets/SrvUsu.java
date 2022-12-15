package Servlets;

import DAO.DAOCargo;
import DAO.DAOUsuario;
import Entidades.Cargo;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvUsu extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                //registrar, actualizar, leer
                switch (accion) {
                    case "nuevo":
                        request.setAttribute("accion", "guardar");//
                        presentarFormulario(request, response);
                        break;
                    case "guardar":
                        guardarUsuario(request, response);
                        break;
                    case "editar":
                        presentarUsuario(request, response);
                        break;
                    case "actualizar":
                        actualizarUsuario(request, response);
                        break;
                    case "eliminar":
                        eliminarUsuario(request, response);
                        break;
                }
            } else {
                this.listarUsuario(request, response);
            }
        } catch (Exception e) {
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                        "/mensaje.jsp").forward(request, response);
            } catch (Exception ex) {
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.cargarCargos(request);
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/registrarUsuarios.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void cargarCargos(HttpServletRequest request) {
        DAOCargo dao = new DAOCargo();
        List<Cargo> car = null;
        try {
            car = dao.listar();
            request.setAttribute("cargo", car);
        } catch (Exception e) {
            request.setAttribute("msje", "No se puede cargar los cargos" + e.getLocalizedMessage());
        } finally {
            car = null;
            dao = null;
        }
    }

    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOUsuario dao;
        Usuario usu = null;
        Cargo car = new Cargo();

        if (request.getParameter("txtNombreUsuario") != null) {
            usu = new Usuario();
            usu.setUsuario(request.getParameter("txtNombreUsuario"));
            usu.setClave(request.getParameter("txtClaveUsuario"));
            car.setCodigo(Integer.parseInt(request.getParameter("cboCargo")));
            usu.setCargo(car);

            if (request.getParameter("chkEstado") != null) {
                //llega parámetro
                usu.setEstado(true);
            } else {
                usu.setEstado(false);
            }
            dao = new DAOUsuario();
            try {
                dao.registrar(usu);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                response.sendRedirect("srvUsus");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el usuario" + e.getLocalizedMessage());
                request.setAttribute("users", usu);
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
            } finally {
            }
        }
    }

    private void presentarUsuario(HttpServletRequest request, HttpServletResponse response) {
        DAOUsuario dao;
        Usuario usu;

        if (request.getParameter("cod") != null) {
            usu = new Usuario();
            usu.setCodigo(Integer.parseInt(request.getParameter("cod")));

            dao = new DAOUsuario();
            try {
                usu = dao.leer(usu);
                if (usu != null) {
                    request.setAttribute("users", usu);
                } else {
                    request.setAttribute("msje", "No se encontró el usuario");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        request.setAttribute("accion", "actualizar");
        this.presentarFormulario(request, response);
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) {
        DAOUsuario daoUsu;
        Usuario Usu;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombreCliente") != null) {
            Usu = new Usuario();
            Usu.setCodigo(Integer.parseInt(request.getParameter("hCodigo")));
            Usu.setUsuario(request.getParameter("txtNombreUsuario"));
            Usu.setClave(request.getParameter("txtClaveUsuario"));
            if (request.getParameter("chkEstado") != null) {
                Usu.setEstado(true);
            } else {
                Usu.setEstado(false);
            }

            daoUsu = new DAOUsuario();
            try {
                daoUsu.actualizar(Usu);
                response.sendRedirect("srvUsuario");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar los Usuarios" + e.getMessage());
                request.setAttribute("users", Usu);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        } else {
            request.setAttribute("msje", "Rellene obligatoriamente los campos");
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)throws Exception{
        DAOUsuario daousu;
        Usuario usu;
        if (request.getParameter("cod") != null) {
            usu = new Usuario();
            usu.setCodigo(Integer.parseInt(request.getParameter("cod")));
            daousu = new DAOUsuario();
            try {
                daousu.eliminarUsuario(usu);
                response.sendRedirect("srvUsus");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar el usuario " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daousu = null;
            }
        }
    }

    private void listarUsuario(HttpServletRequest request, HttpServletResponse response) {
        DAOUsuario dao = new DAOUsuario();
        List<Usuario> usu = null;

        try {
            usu = dao.listar();
            request.setAttribute("usuarios", usu);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuario" + e.getLocalizedMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarUsuarios.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

}
