<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true"%>
<%
    if (session.getAttribute("usuario") != null) {
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="x-ua-compatible" content="ie=edge">

        <title>Sistema Rosvil | Categorias</title>

        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/adminlte.min.css">
        <link href="../sweetAlert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">

            <!-- Navbar -->
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <!-- Left navbar links -->
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>
                    <li class="nav-item d-none d-sm-inline-block">
                        <a href="#" class="nav-link">Inicio</a>
                    </li>
                    <li class="nav-item d-none d-sm-inline-block">
                        <a href="#" class="nav-link">Acerca de Nosotros</a>
                    </li>
                </ul>


                <!-- Right navbar links -->
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a data-toggle="modal" href="#logout"  class="btn btn-danger"><i class="fa fa-power-off btn-flat"></i> Cerrar Sesion </a>
                    </li>
                </ul>
            </nav>
            <!-- /.navbar -->

            <!-- Modal de Cerrar sesion -->
            <div class="modal fade in" id="logout" aria-hidden="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title"><i class="fa fa-sign-out"> Salir</i></h4>
                        </div>
                        <div class="modal-body">
                            <p>¿Seguro que deseas salir del sistema? </p>
                        </div>
                        <div class="modal-footer">
                            <a type="button" class="btn btn-danger" href="srvUsuario?accion=cerrar">Si, Salir</a>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            <!-- /. Modal -->

            <!-- Main Sidebar Container -->
            <aside class="main-sidebar sidebar-dark-primary elevation-4">
                <!-- Brand Logo -->
                <a href="index3.html" class="brand-link">
                    <img src="dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
                         style="opacity: .8;">
                    <span class="brand-text font-weight-light">Sistema Rosvil</span>
                </a>

                <!-- Sidebar -->
                <div class="sidebar">
                    <!-- Sidebar user panel (optional) -->
                    <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                        <div class="image">
                            <img src="dist/img/user2-160x160.jpg" style="margin-top: 13px" class="img-circle elevation-4" alt="User Image">
                        </div>
                        <div class="info">
                            <a href="#" class="d-block">Bienvenido!, ${usuario.usuario}</a>
                            <a class="d-block">
                                <i class="fa fa-circle"></i> ${usuario.cargo.cargo}
                            </a>
                        </div>
                    </div>

                    <!-- Sidebar Menu -->
                    <nav class="mt-2">
                        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                            <!-- Add icons to the links using the .nav-icon class
                                 with font-awesome or any other icon font library -->
                            <li class="nav-item has-treeview menu-open">
                                <a href="#" class="nav-link active">
                                    <i class="nav-icon fas fa-tachometer-alt"></i>
                                    <p>
                                        Menu Registros
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="srvCategoria" class="nav-link active">
                                            <i class="fas fa-archive nav-icon"></i>
                                            <p>Categorias</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvProductos" class="nav-link">
                                            <i class="fab fa-product-hunt nav-icon"></i>
                                            <p>Productos</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvProveedores" class="nav-link">
                                            <i class="fas fa-user-tag nav-icon"></i>
                                            <p>Proveedores</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvClientes" class="nav-link">
                                            <i class="fas fa-users nav-icon"></i>
                                            <p>Clientes</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvEmpleados" class="nav-link">
                                            <i class="fas fa-user-friends"></i>
                                            <p>Empleados</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvUsus" class="nav-link">
                                            <i class="fas fa-users nav-icon"></i>
                                            <p>Usuarios</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvCargo" class="nav-link">
                                            <i class="fas fa-money-check nav-icon"></i>
                                            <p>Cargo</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvTipo_Pago" class="nav-link">
                                            <i class="fas fa-money-check-alt nav-icon"></i>
                                            <p>Opciones Tipo Pago</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview">
                                <a href="#" class="nav-link">
                                    <i class="nav-icon fas fa-cart-plus"></i>
                                    <p>
                                        Menu Ventas
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="srvVenta" class="nav-link">
                                            <i class="fas fa-cart-arrow-down nav-icon"></i>
                                            <p>Nueva Venta</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="srvReporte" class="nav-link">
                                            <i class="fas fa-chart-pie nav-icon"></i>
                                            <p>Reportes</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>

                        </ul>
                    </nav>
                    <!-- /.sidebar-menu -->
                </div>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1>Sección Categorias</h1>
                                <a href="srvCategoria?accion=nuevo" style="margin-top: 10px;" class="btn btn-success">
                                    <i class="fas fa-plus"></i> Nueva Categoria</a>  
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Menu Principal</a></li>
                                    <li class="breadcrumb-item active">Categorias</li>
                                </ol>
                            </div>
                        </div>
                    </div><!-- /.container-fluid -->
                </section>

                <section class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Listado de Categorias</h3>
                                    </div>
                                    <div class="card-body">
                                        <table id="example1" class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Id_Categoria</th>
                                                    <th>Categoria</th>
                                                    <th class="text-center">Estado</th>
                                                    <th class="text-center">Acciones</th>
                                                </tr>
                                            </thead>
                                            <c:forEach var="cat" items="${categorias}" varStatus="iteracion">  
                                                <tr>
                                                    <td>${iteracion.index + 1}</td>
                                                    <td>${cat.categoria}</td>
                                                    <c:if test="${cat.estado == true}">
                                                        <td class="text-center"><span class="badge bg-green active">Activa</span></td> 
                                                    </c:if>
                                                    <c:if test="${cat.estado == false}">
                                                        <td class="text-center"><span class="badge bg-red active">Inactiva</span></td> 
                                                    </c:if>
                                                    <td class="text-center">                                                     
                                                        <a href="<c:url value="srvCategoria">
                                                               <c:param name="accion" value="editar" />
                                                               <c:param name="cod" value="${cat.codigo}" />
                                                           </c:url>">
                                                            <button type="button" class="btn btn-warning" data-toggle="tooltip" title="Editar" data-original-title="Editar">
                                                                <i class="fas fa-edit"></i></button>
                                                        </a>
                                                        <!--Este input nos permite mandar el id de la categoria para que se muestre el sweeet alert -->
                                                        <input type="hidden" id="codigo" value="${cat.codigo}">        
                                                        <a id="deleteCategoria" href="<c:url value="srvCategoria">
                                                               <c:param name="accion" value="eliminar" />
                                                               <c:param name="cod" value="${cat.codigo}" />
                                                           </c:url>">
                                                            <button type="button" class="btn btn-danger" data-toggle="tooltip" title="Eliminar" data-original-title="Editar">
                                                                <i class="far fa-trash-alt"></i></button>
                                                        </a>
                                                        
                                                    </td>
                                                </tr>
                                            </c:forEach>  
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <!-- Main Footer -->
            <footer class="main-footer">
                <!-- To the right -->
                <div class="float-right d-none d-sm-inline">
                    UTP - INGENIERÍA DE SISTEMAS E INFORMÁTICA
                </div>
                <!-- Default to the left -->
                <strong>Copyright &copy; 2022-2024 <a href="https://adminlte.io">Sistema Rosvil</a>.</strong> Todos los derechos reservados.
            </footer>
        </div>
        <!-- ./wrapper -->

        <!-- REQUIRED SCRIPTS -->

        <!-- jQuery -->
        <script src="plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/adminlte.min.js"></script>
        <script src="plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
        <script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
        <script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
        <script src="js/funcionesCategoria.js" type="text/javascript"></script>
        <script src="sweetAlert/sweetalert.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#example1").DataTable({
                    "responsive": true,
                    "autoWidth": false,
                });
            });
        </script>
    </body>
</html>
<%
    } else {
        response.sendRedirect("identificar.jsp");
    }
%>
