
package Servlets;

import DAO.DAOTipo_Pago;
import Entidades.Tipo_Pago;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvTipo_Pago", urlPatterns = {"/srvTipo_Pago"})
public class srvTipo_Pago extends HttpServlet {

    
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
                        guardarTipo_Pago(request, response);
                        break;
                    case "editar":
                        presentarTipo_Pago(request, response);
                        break;
                    case "actualizar":
                        actualizarTipo_Pago(request, response);
                        break;
                    case "eliminar":
                        eliminarTipo_Pago(request, response);
                }
            } else {
                this.listarTipo_Pago(request, response);
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
                    getRequestDispatcher("/vistas/registrarTipo_Pago.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void guardarTipo_Pago(HttpServletRequest request, HttpServletResponse response) throws Exception{
        DAOTipo_Pago dao;
        Tipo_Pago tp = null;

        if (request.getParameter("txtPago") != null) {

            tp = new Tipo_Pago();
            tp.setNombretipopag(request.getParameter("txtPago"));
            //TOMA EL VALOR DE LA CAJA DE TEXTO DEL FORMULARIO
            if (request.getParameter("chkEstado") != null) {
                tp.setEstadopag(true);
            } else {
                tp.setEstadopag(false);
            }
            //VERIFICA EL ESTADO DE LA CATEGORIA SI ESTA ACTIVA O NO
            dao = new DAOTipo_Pago();
            //INSTANCIA EL OBJETO DAOCATEGORIA PARA PROCEDER CON EL REGISTRO
            try {
                dao.registrar(tp);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                //DAOCATEGORIA PARA INSERTAR EL REGISTRO EN LA BASE DE DATOS.
                response.sendRedirect("srvTipo_Pago");
                //UNA VEZ REGISTRADO REDIRECCIONA LA PAGINA DE CATEGORIA(MUESTRA EL LISTADO)
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar la opcion de pago");
                //MENSAJE EN CASO DE QUE ALGO PASE AL MOMENTO DE REGISTRAR
                request.setAttribute("tipo_pago", tp);
                //ATRIBUTO CAT PARA ACCEDER A LA COLECCION DE CATEGORIAS
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
                //PRESENTAR MENSAJE DE ERROR
            } finally {
            }
        }
    }

    private void presentarTipo_Pago(HttpServletRequest request, HttpServletResponse response) {
        DAOTipo_Pago dao;
        Tipo_Pago tp;

        if (request.getParameter("cod") != null) {
            tp = new Tipo_Pago();
            tp.setCodigopag(Integer.parseInt(request.getParameter("cod")));

            dao = new DAOTipo_Pago();
            try {
                tp = dao.leer(tp);
                if (tp != null) {
                    request.setAttribute("tipo_pago", tp);
                } else {
                    request.setAttribute("msje", "No se encontró el tipo de pago");
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

    private void actualizarTipo_Pago(HttpServletRequest request, HttpServletResponse response) {
        DAOTipo_Pago daotp;
        Tipo_Pago tp;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtPago") != null) {
            tp = new Tipo_Pago();
            tp.setCodigopag(Integer.parseInt(request.getParameter("hCodigo")));
            tp.setNombretipopag(request.getParameter("txtPago"));
            if (request.getParameter("chkEstado") != null) {
                tp.setEstadopag(true);
            } else {
                tp.setEstadopag(false);
            }
            daotp = new DAOTipo_Pago();
            try {
                daotp.actualizar(tp);
                response.sendRedirect("srvTipo_Pago");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar la opcion de pago" + e.getMessage());
                request.setAttribute("tipo_pago", tp);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
    }

    private void eliminarTipo_Pago(HttpServletRequest request, HttpServletResponse response)throws Exception{
        DAOTipo_Pago daotp;
        Tipo_Pago tp;
        if (request.getParameter("cod") != null) {
            tp = new Tipo_Pago();
            tp.setCodigopag(Integer.parseInt(request.getParameter("cod")));
            daotp = new DAOTipo_Pago();
            try {
                daotp.eliminarTipo_Pago(tp);
                response.sendRedirect("srvTipo_Pago");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar la opcion de pago " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daotp = null;
            }
        }

    }

    private void listarTipo_Pago(HttpServletRequest request, HttpServletResponse response) {
        DAOTipo_Pago dao = new DAOTipo_Pago();
        List<Tipo_Pago> tp = null;

        try {
            tp = dao.listar();
            request.setAttribute("Tipo_Pago", tp);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los Tipos de Pago" + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarTipo_Pago.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

}
