package Servlets;

import DAO.DAOCategoria;
import Entidades.Categoria;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvCategoria", urlPatterns = {"/srvCategoria"})
public class srvCategoria extends HttpServlet {

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
                        guardarCategoria(request, response);
                        break;
                    case "editar":
                        presentarCategoria(request, response);
                        break;
                    case "actualizar":
                        actualizarCategoria(request, response);
                        break;
                    case "eliminar":
                        eliminarCategoria(request, response);
                        break;
                }
            } else {
                this.listarCategorias(request, response);
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

    private void listarCategorias(HttpServletRequest request, HttpServletResponse response) {
        DAOCategoria dao = new DAOCategoria();
        List<Categoria> categorias = null;

        try {
            categorias = dao.listar();
            request.setAttribute("categorias", categorias);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar las categorias");
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarCategorias.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void presentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/registrarCategorias.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void guardarCategoria(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOCategoria dao;
        Categoria cat = null;

        if (request.getParameter("txtCategoria") != null) {

            cat = new Categoria();
            cat.setCategoria(request.getParameter("txtCategoria"));
            //TOMA EL VALOR DE LA CAJA DE TEXTO DEL FORMULARIO
            if (request.getParameter("chkEstado") != null) {
                cat.setEstado(true);
            } else {
                cat.setEstado(false);
            }
            //VERIFICA EL ESTADO DE LA CATEGORIA SI ESTA ACTIVA O NO
            dao = new DAOCategoria();
            //INSTANCIA EL OBJETO DAOCATEGORIA PARA PROCEDER CON EL REGISTRO
            try {
                dao.registrar(cat);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                //DAOCATEGORIA PARA INSERTAR EL REGISTRO EN LA BASE DE DATOS.
                response.sendRedirect("srvCategoria");
                //UNA VEZ REGISTRADO REDIRECCIONA LA PAGINA DE CATEGORIA(MUESTRA EL LISTADO)
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar la categoria");
                //MENSAJE EN CASO DE QUE ALGO PASE AL MOMENTO DE REGISTRAR
                request.setAttribute("categoria", cat);
                //ATRIBUTO CAT PARA ACCEDER A LA COLECCION DE CATEGORIAS
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
                //PRESENTAR MENSAJE DE ERROR
            } finally {
            }
        }

    }

    private void presentarCategoria(HttpServletRequest request, HttpServletResponse response) {
        DAOCategoria dao;
        Categoria cat;

        if (request.getParameter("cod") != null) {
            cat = new Categoria();
            cat.setCodigo(Integer.parseInt(request.getParameter("cod")));

            dao = new DAOCategoria();
            try {
                cat = dao.leer(cat);
                if (cat != null) {
                    request.setAttribute("categoria", cat);
                } else {
                    request.setAttribute("msje", "No se encontró la categoria");
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

    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) {
        DAOCategoria daoCat;
        Categoria cat;

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtCategoria") != null) {
            cat = new Categoria();
            cat.setCodigo(Integer.parseInt(request.getParameter("hCodigo")));
            cat.setCategoria(request.getParameter("txtCategoria"));
            if (request.getParameter("chkEstado") != null) {
                cat.setEstado(true);
            } else {
                cat.setEstado(false);
            }
            daoCat = new DAOCategoria();
            try {
                daoCat.actualizar(cat);
                response.sendRedirect("srvCategoria");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar la categoria" + e.getMessage());
                request.setAttribute("categoria", cat);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOCategoria daoCat;
        Categoria cat;
        if (request.getParameter("cod") != null) {
            cat = new Categoria();
            cat.setCodigo(Integer.parseInt(request.getParameter("cod")));
            daoCat = new DAOCategoria();
            try {
                daoCat.eliminarCategoria(cat);
                response.sendRedirect("srvCategoria");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar la categoria " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daoCat = null;
            }
        }

    }

}
