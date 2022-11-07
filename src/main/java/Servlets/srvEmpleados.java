package Servlets;

import DAO.DAOEmpleados;
import DAO.DAOUsuario;
import Entidades.Empleados;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class srvEmpleados extends HttpServlet {

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
                        guardarEmpleados(request, response);
                        break;
                    case "editar":
                        presentarEmpleados(request, response);
                        break;
                    case "actualizar":
                        actualizarEmpleados(request, response);
                        break;
                    case "eliminar":
                        eliminarEmpleados(request, response);
                }
            } else {
                this.listarEmpleados(request, response);
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
            this.cargarUsuarios(request);
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/registrarEmpleados.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }
    
    private void cargarUsuarios(HttpServletRequest request) {
        DAOUsuario dao = new DAOUsuario();
        List<Usuario> usu = null;
        try {
            usu = dao.listar();
            request.setAttribute("usuarios", usu);
        } catch (Exception e) {
            request.setAttribute("msje", "No se puede cargar los usuarios" + e.getLocalizedMessage());
        } finally {
            usu = null;
            dao = null;
        }
    }

    private void guardarEmpleados(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOEmpleados dao;
        Empleados emp = null;
        
        Usuario usu = new Usuario();
        if (request.getParameter("txtNombreEmpleado") != null) {
            emp = new Empleados();
            emp.setNombreemp(request.getParameter("txtNombreEmpleado"));
            emp.setApellidoemp(request.getParameter("txtApellidoEmpleado"));
            emp.setDniemp(request.getParameter("txtDNI"));
            emp.setDireccionemp(request.getParameter("txtDireccion"));
            emp.setTelefonoemp(request.getParameter("txtTelefono"));
            emp.setCorreoemp(request.getParameter("txtCorreo"));
            usu.setCodigo(Integer.parseInt(request.getParameter("cboUsuario")));
            emp.setUsuario(usu);
            
            if (request.getParameter("chkEstado") != null) {
                //llega parámetro
                emp.setEstadoemp(true);
            } else {
                emp.setEstadoemp(false);
            }
            dao = new DAOEmpleados();
            try {
                dao.registrar(emp);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                response.sendRedirect("srvEmpleados");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el empleados" + e.getLocalizedMessage());
                request.setAttribute("empleados", emp);
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
            } finally {
            }
        }
    }

    private void presentarEmpleados(HttpServletRequest request, HttpServletResponse response) {
        DAOEmpleados dao;
        Empleados empl;

        if (request.getParameter("cod") != null) {
            empl = new Empleados();
            empl.setCodigoemp(Integer.parseInt(request.getParameter("cod")));

             dao = new DAOEmpleados();
            try {
                empl = dao.leer(empl);
                if (empl != null) {
                    request.setAttribute("empleados", empl);
                } else {
                    request.setAttribute("msje", "No se encontró el empleado");
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

    private void actualizarEmpleados(HttpServletRequest request, HttpServletResponse response) {
        DAOEmpleados daoempl;
        Empleados empl;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombreCliente") != null) {
            empl = new Empleados();
            empl.setCodigoemp(Integer.parseInt(request.getParameter("hCodigo")));
            empl.setNombreemp(request.getParameter("txtNombreEmpleado"));
            empl.setApellidoemp(request.getParameter("txtApellidoEmpleado"));
            empl.setDniemp(request.getParameter("txtDNI"));
            empl.setDireccionemp(request.getParameter("txtDireccion"));
            empl.setTelefonoemp(request.getParameter("txtTelefono"));
            empl.setCorreoemp(request.getParameter("txtCorreo")); 
            
            if (request.getParameter("chkEstado") != null) {
                empl.setEstadoemp(true);
            } else {
                empl.setEstadoemp(false);
            }
            
            daoempl = new DAOEmpleados();
            try {
                daoempl.actualizar(empl);
                response.sendRedirect("srvEmpleados");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar los Empleados" + e.getMessage());
                request.setAttribute("empleado", empl);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
        else{
            request.setAttribute("msje","Rellene obligatoriamente los campos");
        }
    }

    private void eliminarEmpleados(HttpServletRequest request, HttpServletResponse response) throws Exception{
       DAOEmpleados daoempl;
        Empleados empl;
        if (request.getParameter("cod") != null) {
            empl = new Empleados();
            empl.setCodigoemp(Integer.parseInt(request.getParameter("cod")));
            daoempl = new DAOEmpleados();
            try {
                daoempl.eliminarEmpleados(empl);
                response.sendRedirect("srvEmpleados");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar el empleado " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daoempl = null;
            }
        }
    }

    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response) {
        DAOEmpleados dao = new DAOEmpleados();
        List<Empleados> empleados = null;

        try {
            empleados = dao.listar();
            request.setAttribute("empleado", empleados);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los empleados");
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarEmpleados.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

}
