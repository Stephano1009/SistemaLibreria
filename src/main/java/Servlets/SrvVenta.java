package Servlets;

import DAO.DAOCliente;
import DAO.DAOTipo_Pago;
import DAO.DAOVenta;
import Entidades.Cliente;
import Entidades.DetalleVentaAux;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Tipo_Pago;
import Entidades.Venta;
import Entidades.VentaAux;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SrvVenta extends HttpServlet {

    private final DAOVenta daov = new DAOVenta();
    private final Gson json = new Gson();
    private PrintWriter output = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                //registrar, actualizar, leer
                switch (accion) {
                    case "nuevaVenta":
                        request.setAttribute("accion", "guardar");//
                        presentarFormulario(request, response);
                        break;
                    case "guardar":
                        guardarVenta(request, response);
                        break;
                }
            } else {
                this.presentarFormulario(request, response);
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
        //processRequest(request, response);

        response.setContentType("application/json;charset=UTF-8");

        output = response.getWriter();

        String action = request.getParameter("action");

        if (action.equals("cargarDatos")) {
            cargarClientes();
        } else if (action.equals("obtenerCorrelativo")) {
            obtenerCorrelativo(request.getParameter("tipo"));
        } else if (action.equals("obtenerProducto")) {
            obtenerProducto(request.getParameter("descripcion"));
        } else if (action.equals("cargarTipoPago")) {
            cargarTP();
        } else if (action.equals("registrar")) {
            try {
                registrar(request);
            } catch (Exception ex) {
                Logger.getLogger(SrvVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void registrar(HttpServletRequest request) throws Exception {
        VentaAux aux = json.fromJson(request.getParameter("data_venta"), VentaAux.class);

        Cliente cliente = new Cliente();
        cliente.setCodigo(aux.getCliente());

        cliente = new DAOCliente().leer(cliente);

        if (aux.getComprobante().equals("F") && cliente.getTipodocumento().equals("D")) {
            print_error("Cliente debe ser tipo RUC");
        }

        Venta venta = new Venta();
        venta.setClientes(cliente);
        venta.setTipo_documentov(aux.getComprobante());
        venta.setSeriev(aux.getSerie());
        venta.setFechav(new SimpleDateFormat("YYYY/MM/dd").format(new Date()));
        int correlativo = daov.obtenerCorrelativo(aux.getComprobante());
        venta.setNumerov(String.valueOf(++correlativo));

        Type objType = new TypeToken<ArrayList<DetalleVentaAux>>() {
        }.getType();
        ArrayList<DetalleVentaAux> detallesaux = json.fromJson(request.getParameter("detalles"), objType);

        ArrayList<DetalleVenta> detalles = new ArrayList<>();
        boolean has_stock = true;
        Producto producto = null;

        for (DetalleVentaAux detalle : detallesaux) {
            producto = daov.obtenerProducto(String.valueOf(detalle.getProducto()), 2);
            if (producto.getStockpro() == 0) {
                has_stock = false;
                break;
            }

            DetalleVenta obj = new DetalleVenta();
            obj.setCantidadventas(detalle.getCantidad());
            obj.setPrecioventaventas(detalle.getPrecio());
            obj.setProductos(producto);
            detalles.add(obj);
        }

        if (has_stock) {
            venta.setDetalles(detalles);
            try {
                daov.registrar(venta);
                print_success("");
            } catch (Exception e) {
                print_error(e.getMessage());
            }
        } else {
            print_error("El productos " + producto.getNombrepro() + " no cuenta con stock.");
        }
    }

    private void obtenerCorrelativo(String tipo) {
        int correlativo = 0;
        try {
            correlativo = daov.obtenerCorrelativo(tipo);

            HashMap<String, String> obj = new HashMap<>();
            obj.put("serie", tipo + "0001");
            obj.put("correlativo", "00000" + (++correlativo));

            print_success(obj);
        } catch (Exception e) {
            print_error(e.getMessage());
        }
    }

    private void obtenerProducto(String descripcion) {
        Producto producto = null;
        try {
            producto = daov.obtenerProducto(descripcion, 1);
            print_success(producto);
        } catch (Exception e) {
            print_error(e.getMessage());
        }
    }

    private void cargarClientes() {
        DAOCliente dao = new DAOCliente();
        List<Cliente> clientes = null;

        try {
            clientes = dao.cargarClientesVentas();
            print_success(clientes);
        } catch (Exception e) {
            print_error(e.getMessage());
        }
    }
    
     private void cargarTP() {
        DAOTipo_Pago dao = new DAOTipo_Pago();
        List<Tipo_Pago> tipopago=  null;

        try {
            tipopago = dao.listar();
            print_success(tipopago);
        } catch (Exception e) {
            print_error(e.getMessage());
        }
    }

    private void print_success(Object object) {
        output.print("{\"estado\": true, \"data\": " + json.toJson(object) + "}");
    }

    private void print_error(String mensaje) {
        output.print("{\"estado\": false, \"msj\": \"" + mensaje + "\"}");
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
            this.cargarClientes(request);
            this.cargarPagos(request);
            this.getServletConfig().getServletContext().
                    getRequestDispatcher("/vistas/ventas.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operacion" + e.getLocalizedMessage());
        }
    }

    private void guardarVenta(HttpServletRequest request, HttpServletResponse response) {

    }

    private void cargarClientes(HttpServletRequest request) {
        DAOCliente dao = new DAOCliente();
        List<Cliente> cli = null;
        try {
            cli = dao.listar();
            request.setAttribute("clientes", cli);
        } catch (Exception e) {
            request.setAttribute("msje", "No se puede cargar los clientes" + e.getLocalizedMessage());
        } finally {
            cli = null;
            dao = null;
        }
    }

    private void cargarPagos(HttpServletRequest request) {
        DAOTipo_Pago dao = new DAOTipo_Pago();
        List<Tipo_Pago> tp = null;
        try {
            tp = dao.listar();
            request.setAttribute("pagos", tp);
        } catch (Exception e) {
            request.setAttribute("msje", "No se puede cargar los pagos" + e.getLocalizedMessage());
        } finally {
            tp = null;
            dao = null;
        }
    }

}
