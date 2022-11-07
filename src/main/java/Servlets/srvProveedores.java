package Servlets;

import DAO.DAOProveedores;
import Entidades.Proveedores;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class srvProveedores extends HttpServlet {

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
                        guardarProveedores(request, response);
                        break;
                    case "editar":
                        presentarProveedores(request, response);
                        break;
                    case "actualizar":
                        actualizarProveedores(request, response);
                        break;
                    case "eliminar":
                        eliminarProveedores(request, response);
                }
            } else {
                this.listarProveedores(request, response);
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
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/registrarProveedores.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void guardarProveedores(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOProveedores dao;
        Proveedores prove = null;

        if (request.getParameter("txtNombreProveedor") != null) {

            prove = new Proveedores();
            prove.setRucProvee(request.getParameter("txtRuc"));
            prove.setNombreProve(request.getParameter("txtNombreProveedor"));
            prove.setTelefonoProve(request.getParameter("txtTelefono"));
            if (request.getParameter("chkEstado") != null) {
                //llega parámetro
                prove.setEstadoProve(true);
            } else {
                prove.setEstadoProve(false);
            }

            //VERIFICA EL ESTADO DE LA CATEGORIA SI ESTA ACTIVA O NO
            dao = new DAOProveedores();
            //INSTANCIA EL OBJETO DAOCATEGORIA PARA PROCEDER CON EL REGISTRO
            try {
                dao.registrar(prove);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                //DAOCATEGORIA PARA INSERTAR EL REGISTRO EN LA BASE DE DATOS.
                response.sendRedirect("srvProveedores");
                //UNA VEZ REGISTRADO REDIRECCIONA LA PAGINA DE CATEGORIA(MUESTRA EL LISTADO)
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el Proveedor" + e.getLocalizedMessage());
                //MENSAJE EN CASO DE QUE ALGO PASE AL MOMENTO DE REGISTRAR
                request.setAttribute("proveedores", prove);
                //ATRIBUTO CAT PARA ACCEDER A LA COLECCION DE CATEGORIAS
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
                //PRESENTAR MENSAJE DE ERROR
            } finally {
            }
        }
    }

    private void presentarProveedores(HttpServletRequest request, HttpServletResponse response) {

        DAOProveedores dao;
        Proveedores prove;

        if (request.getParameter("cod") != null) {
            prove = new Proveedores();
            prove.setCodigoProve(Integer.parseInt(request.getParameter("cod")));

            dao = new DAOProveedores();
            try {
                prove = dao.leer(prove);
                if (prove != null) {
                    request.setAttribute("proveedores", prove);
                } else {
                    request.setAttribute("msje", "No se encontró el proveedor");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos");
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        request.setAttribute("accion", "actualizar");
        this.presentarFormulario(request, response);
    
    }

    
    private void actualizarProveedores(HttpServletRequest request, HttpServletResponse response) {
        DAOProveedores daoProve;
        Proveedores prove;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombreProveedor") != null) {
            prove = new Proveedores();
            prove.setCodigoProve(Integer.parseInt(request.getParameter("hCodigo")));
            prove.setRucProvee(request.getParameter("txtRuc"));
            prove.setNombreProve(request.getParameter("txtNombreProveedor"));
            prove.setTelefonoProve(request.getParameter("txtTelefono"));
            
            
            
            if (request.getParameter("chkEstado") != null) {
                prove.setEstadoProve(true);
            } else {
                prove.setEstadoProve(false);
            }
            daoProve = new DAOProveedores();
            try {
                daoProve.actualizar(prove);
                response.sendRedirect("srvProveedores");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar los proveedores" + e.getMessage());
                request.setAttribute("proveedores", prove);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
        else{
            request.setAttribute("msje","Rellene obligatoriamente los campos");
        }
    }

    private void eliminarProveedores(HttpServletRequest request, HttpServletResponse response)throws Exception{
        DAOProveedores daoprove;
        Proveedores prove;
        if (request.getParameter("cod") != null) {
            prove = new Proveedores();
            prove.setCodigoProve(Integer.parseInt(request.getParameter("cod")));
            daoprove = new DAOProveedores();
            try {
                daoprove.eliminarProveedores(prove);
                response.sendRedirect("srvProveedores");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar el proveedor " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daoprove = null;
            }
        }
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response) {
        DAOProveedores dao = new DAOProveedores();
        List<Proveedores> proveedores = null;

        try {
            proveedores = dao.listar();
            request.setAttribute("proveedores", proveedores);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los proveedores" + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarProveedores.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

}
