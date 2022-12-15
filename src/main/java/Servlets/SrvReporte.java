package Servlets;

import DAO.DAOVenta;
import Entidades.DetalleVenta;
import Entidades.Venta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SrvReporte", urlPatterns = {"/srvReporte"})
public class SrvReporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                //registrar, actualizar, leer
                switch (accion) {
                    case "verDetalle":
                        request.setAttribute("accion", "guardar");//
                        presentarDetalle(request, response);
                        break;
                }
            } else {
                this.listarVentas(request, response);
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


    private void presentarDetalle(HttpServletRequest request, HttpServletResponse response) {
        DAOVenta dao;
        Venta vent = new Venta();
        List<DetalleVenta> ventas;

        if (request.getParameter("cod") != null) {
            vent.setCodigov(Integer.parseInt(request.getParameter("cod")));

            dao = new DAOVenta();
            try {
                ventas = dao.listarDetalles(vent);
                if (ventas != null) {
                    request.setAttribute("detalle", ventas);
                } else {
                    request.setAttribute("msje", "No se encontró la venta");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/verDetalle.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getLocalizedMessage());
        }

    }

    private void listarVentas(HttpServletRequest request, HttpServletResponse response) {
        DAOVenta dao = new DAOVenta();
        List<Venta> ven = null;

        try {
            ven = dao.listar();
            request.setAttribute("ventas", ven);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar las ventas" + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listadoVentas.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getLocalizedMessage());
        }
    }

}
