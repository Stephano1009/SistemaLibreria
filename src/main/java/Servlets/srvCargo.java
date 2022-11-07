
package Servlets;

import DAO.DAOCargo;
import Entidades.Cargo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class srvCargo extends HttpServlet {

    
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
                        guardarCargo(request, response);
                        break;
                    case "editar":
                        presentarCargo(request, response);
                        break;
                    case "actualizar":
                        actualizarCargo(request, response);
                        break;
                    case "eliminar":
                        eliminarCargo(request, response);
                }
            } else {
                this.listarCargo(request, response);
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

    private void listarCargo(HttpServletRequest request, HttpServletResponse response) {
        DAOCargo dao = new DAOCargo();
        List<Cargo> cargo = null;

        try {
            cargo = dao.listar();
            request.setAttribute("cargo", cargo);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los cargos");
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarCargos.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void eliminarCargo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        DAOCargo daoCar;
        Cargo car;
        if (request.getParameter("cod") != null) {
            car = new Cargo();
            car.setCodigo(Integer.parseInt(request.getParameter("cod")));
            daoCar = new DAOCargo();
            try {
                daoCar.eliminarCargo(car);
                response.sendRedirect("srvCargo");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar el cargo " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daoCar = null;
            }
        }
    }

    private void actualizarCargo(HttpServletRequest request, HttpServletResponse response) {
        DAOCargo daoCar;
        Cargo car;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtCargo") != null) {
            car = new Cargo();
            car.setCodigo(Integer.parseInt(request.getParameter("hCodigo")));
            car.setCargo(request.getParameter("txtCargo"));
            if (request.getParameter("chkEstado") != null) {
                car.setEstado(true);
            } else {
                car.setEstado(false);
            }
            daoCar = new DAOCargo();
            try {
                daoCar.actualizar(car);
                response.sendRedirect("srvCargo");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar la categoria" + e.getMessage());
                request.setAttribute("categoria", car);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
    }

    private void presentarCargo(HttpServletRequest request, HttpServletResponse response) {
        DAOCargo dao;
        Cargo car;

        if (request.getParameter("cod") != null) {
            car = new Cargo();
            car.setCodigo(Integer.parseInt(request.getParameter("cod")));

            dao = new DAOCargo();
            try {
                car = dao.leer(car);
                if (car != null) {
                    request.setAttribute("cargo", car);
                } else {
                    request.setAttribute("msje", "No se encontró el cargo");
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

    private void guardarCargo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        DAOCargo dao;
        Cargo car = null;
        if (request.getParameter("txtCargo") != null) {
            car = new Cargo();
            car.setCargo(request.getParameter("txtCargo"));
            if (request.getParameter("chkEstado") != null) {
                //llega parámetro
                car.setEstado(true);
            } else {
                car.setEstado(false);
            }
            dao = new DAOCargo();
            try {
                dao.registrar(car);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                response.sendRedirect("srvCargo");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el cargo" + e.getLocalizedMessage());
                request.setAttribute("cargo", car);
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
            } finally {
            }
        }
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/registrarCargos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

}
