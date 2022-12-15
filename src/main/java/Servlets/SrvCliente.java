package Servlets;

import DAO.DAOCliente;
import Entidades.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvClientes", urlPatterns = {"/srvClientes"})
public class SrvCliente extends HttpServlet {

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
                        guardarClientes(request, response);
                        break;
                    case "editar":
                        presentarClientes(request, response);
                        break;
                    case "actualizar":
                        actualizarClientes(request, response);
                        break;
                    case "eliminar":
                        eliminarClientes(request, response);
                        break;
                }
            } else {
                this.listarClientes(request, response);
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
                    getRequestDispatcher("/vistas/registrarClientes.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void guardarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DAOCliente dao;
        Cliente cli = null;

        if (request.getParameter("txtNombreCliente") != null) {

            cli = new Cliente();
            cli.setNombre(request.getParameter("txtNombreCliente"));
            cli.setApellido(request.getParameter("txtApellidoCliente"));
            cli.setDireccion(request.getParameter("txtDireccion"));
            cli.setDni(request.getParameter("txtDNI"));
            
            //VERIFICA EL ESTADO DE LA CATEGORIA SI ESTA ACTIVA O NO
            dao = new DAOCliente();
            //INSTANCIA EL OBJETO DAOCATEGORIA PARA PROCEDER CON EL REGISTRO
            try {
                dao.registrar(cli);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                //DAOCATEGORIA PARA INSERTAR EL REGISTRO EN LA BASE DE DATOS.
                response.sendRedirect("srvClientes");
                //UNA VEZ REGISTRADO REDIRECCIONA LA PAGINA DE CATEGORIA(MUESTRA EL LISTADO)
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el cliente" + e.getLocalizedMessage());
                //MENSAJE EN CASO DE QUE ALGO PASE AL MOMENTO DE REGISTRAR
                request.setAttribute("cliente", cli);
                //ATRIBUTO CAT PARA ACCEDER A LA COLECCION DE CATEGORIAS
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
                //PRESENTAR MENSAJE DE ERROR
            } finally {
            }
        }
    }

    private void presentarClientes(HttpServletRequest request, HttpServletResponse response) {
        DAOCliente dao;
        Cliente cli;

        if (request.getParameter("cod") != null) {
            cli = new Cliente();
            cli.setCodigo(Integer.parseInt(request.getParameter("cod")));

             dao = new DAOCliente();
            try {
                cli = dao.leer(cli);
                if (cli != null) {
                    request.setAttribute("cliente", cli);
                } else {
                    request.setAttribute("msje", "No se encontró el cliente");
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

    private void actualizarClientes(HttpServletRequest request, HttpServletResponse response) {
        DAOCliente daoCli;
        Cliente cli;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombreCliente") != null) {
            cli = new Cliente();
            cli.setCodigo(Integer.parseInt(request.getParameter("hCodigo")));
            cli.setNombre(request.getParameter("txtNombreCliente"));
            cli.setApellido(request.getParameter("txtApellidoCliente"));
            cli.setDireccion(request.getParameter("txtDireccion"));
            cli.setDni(request.getParameter("txtDNI"));
            
            daoCli = new DAOCliente();
            try {
                daoCli.actualizar(cli);
                response.sendRedirect("srvClientes");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar los Clientes" + e.getMessage());
                request.setAttribute("cliente", cli);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
        else{
            request.setAttribute("msje","Rellene obligatoriamente los campos");
        }
    }

    private void eliminarClientes(HttpServletRequest request, HttpServletResponse response) throws Exception{
        DAOCliente daocli;
        Cliente cli;
        if (request.getParameter("cod") != null) {
            cli = new Cliente();
            cli.setCodigo(Integer.parseInt(request.getParameter("cod")));
            daocli = new DAOCliente();
            try {
                daocli.eliminarClientes(cli);
                response.sendRedirect("srvClientes");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar el cliente " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daocli = null;
            }
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) {
        DAOCliente dao = new DAOCliente();
        List<Cliente> clientes = null;

        try {
            clientes = dao.listar();
            request.setAttribute("clientes", clientes);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los clientes" + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarClientes.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getLocalizedMessage());
        }
    }

}
