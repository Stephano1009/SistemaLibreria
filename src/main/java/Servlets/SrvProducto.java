package Servlets;

import DAO.DAOCategoria;
import DAO.DAOProducto;
import DAO.DAOProveedor;
import Entidades.Categoria;
import Entidades.Producto;
import Entidades.Proveedor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SrvProducto", urlPatterns = {"/srvProductos"})
public class SrvProducto extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
                        guardarProductos(request, response);
                        break;
                    case "editar":
                        presentarProductos(request, response);
                        break;
                    case "actualizar":
                        actualizarProductos(request, response);
                        break;
                    case "eliminar":
                        eliminarProductos(request, response);
                }
            } else {
                this.listarProductos(request, response);
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
            this.cargarCategorias(request);
            this.cargarProveedores(request);
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/registrarProductos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

    private void cargarCategorias(HttpServletRequest request) {
        DAOCategoria dao = new DAOCategoria();
        List<Categoria> cats = null;
        try {
            cats = dao.listar();
            request.setAttribute("categorias", cats);
        } catch (Exception e) {
            request.setAttribute("msje", "No se puede cargar las categorias" + e.getLocalizedMessage());
        } finally {
            cats = null;
            dao = null;
        }
    }

    private void cargarProveedores(HttpServletRequest request) {
        DAOProveedor dao = new DAOProveedor();
        List<Proveedor> prov = null;
        try {
            prov = dao.listar();
            request.setAttribute("proveedores", prov);
        } catch (Exception e) {
            request.setAttribute("msje", "No se puede cargar los proveedores" + e.getLocalizedMessage());
        } finally {
            prov = null;
            dao = null;
        }
    }

    private void guardarProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DAOProducto dao;
        Producto pro = null;
        Proveedor prov = new Proveedor();
        Categoria cat = new Categoria();
        if (request.getParameter("txtNombreProducto") != null) {
            pro = new Producto();
            pro.setNombrepro(request.getParameter("txtNombreProducto"));
            pro.setDescripcionpro(request.getParameter("txtDescripcion"));
            pro.setContenidopro(request.getParameter("txtContenido"));
            pro.setPreciopro(Integer.parseInt(request.getParameter("txtPrecio")));
            pro.setStockpro(Integer.parseInt(request.getParameter("txtStock")));
            

            cat.setCodigo(Integer.parseInt(request.getParameter("cboCategoria")));
            pro.setCategoriapro(cat);
            prov.setCodigoProve(Integer.parseInt(request.getParameter("cboProveedor")));
            pro.setProveedorpro(prov);
            if (request.getParameter("chkEstado") != null) {
                //llega parámetro
                pro.setEstadopro(true);
            } else {
                pro.setEstadopro(false);
            }
            dao = new DAOProducto();
            try {
                dao.registrar(pro);//LLAMA AL METODO REGISTRAR QUE ESTA EN EL 
                response.sendRedirect("srvProductos");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el producto" + e.getLocalizedMessage());
                request.setAttribute("producto", pro);
                request.setAttribute("accion", "guardar");
                response.sendRedirect("error404.jsp");
            } finally {
            }
        }
    }

    private void presentarProductos(HttpServletRequest request, HttpServletResponse response) {
        DAOProducto dao;
        Producto pro;

        if (request.getParameter("cod") != null) {
            pro = new Producto();
            pro.setCodigopro(Integer.parseInt(request.getParameter("cod")));

             dao = new DAOProducto();
            try {
                pro = dao.leer(pro);
                if (pro != null) {
                    request.setAttribute("producto", pro);
                } else {
                    request.setAttribute("msje", "No se encontró el producto");
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

    private void actualizarProductos(HttpServletRequest request, HttpServletResponse response) {
       DAOProducto daoPro;
        Producto pro;
        Categoria cat = new Categoria();
        Proveedor prov = new Proveedor();

        if (request.getParameter("hCodigo") != null
                && request.getParameter("txtNombreProducto") != null) {
            pro = new Producto();
            pro.setCodigopro(Integer.parseInt(request.getParameter("hCodigo")));
            pro.setNombrepro(request.getParameter("txtNombreProducto"));
            pro.setDescripcionpro(request.getParameter("txtDescripcion"));
            pro.setContenidopro(request.getParameter("txtContenido"));
            pro.setPreciopro(Double.parseDouble(request.getParameter("txtPrecio")));
            pro.setStockpro(Integer.parseInt(request.getParameter("txtStock")));
            cat.setCodigo(Integer.parseInt(request.getParameter("cboCategoria")));
            pro.setCategoriapro(cat);
            prov.setCodigoProve(Integer.parseInt(request.getParameter("cboProveedor")));
            pro.setProveedorpro(prov);
            
            if (request.getParameter("chkEstado") != null) {
                pro.setEstadopro(true);
            } else {
                pro.setEstadopro(false);
            }
            daoPro = new DAOProducto();
            try {
                daoPro.actualizar(pro);
                response.sendRedirect("srvProductos");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo actualizar los productos" + e.getMessage());
                request.setAttribute("producto", pro);
                request.setAttribute("accion", "actualizar");
                this.presentarFormulario(request, response);
            } finally {
            }
        }
        else{
            request.setAttribute("msje","Rellene obligatoriamente los campos");
        }
    }

    private void eliminarProductos(HttpServletRequest request, HttpServletResponse response) throws Exception{
        DAOProducto daopro;
        Producto pro;
        if (request.getParameter("cod") != null) {
            pro = new Producto();
            pro.setCodigopro(Integer.parseInt(request.getParameter("cod")));
            daopro = new DAOProducto();
            try {
                daopro.eliminarProductos(pro);
                response.sendRedirect("srvProductos");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo eliminar el producto " + e.getMessage());
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }finally{
                daopro = null;
            }
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) {
        DAOProducto dao = new DAOProducto();
        List<Producto> pro = null;

        try {
            pro = dao.listar();
            request.setAttribute("productos", pro);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los productos" + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/listarProductos.jsp"
            ).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion");
        }
    }

}
