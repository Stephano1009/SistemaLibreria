<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="x-ua-compatible" content="ie=edge">

        <title>Sistema| Rosvil</title>

        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    </head>
    <body class="sidebar-closed sidebar-collapse" style="height: auto;">
        <div class="wrapper">
                <div class="content-wrapper">
                    <!-- Content Header (Page header) -->
                    <section class="content-header">
                        <div class="container-fluid">
                            <div class="row mb-2">
                                <div class="col-sm-6">
                                    <h1>500 Página de Error</h1>
                                </div>
                                <div class="col-sm-6">
                                    <ol class="breadcrumb float-sm-right">
                                        <li class="breadcrumb-item"><a href="srvUsuario?accion=verificar">Inicio</a></li>
                                        <li class="breadcrumb-item active">Página Error</li>
                                    </ol>
                                </div>
                            </div>
                        </div><!-- /.container-fluid -->
                    </section>

                    <!-- Main content -->
                    <section class="content">
                        <div class="error-page">
                            <h2 class="headline text-danger">500</h2>

                            <div class="error-content">
                                <h3><i class="fas fa-exclamation-triangle text-danger"></i> Oops! Algo salió mal.</h3>

                                <p>
                                    Trabajaremos para solucionarlo de inmediato.
                                    Mientras tanto, puedes solucionarlo <a href="#">${msje}</a>
                                </p>

                                <form class="search-form">
                                    <div class="input-group">
                                        <input type="text" name="search" class="form-control" placeholder="Buscar">
                                        <div class="input-group-append">
                                            <button type="submit" name="submit" class="btn btn-danger"><i class="fas fa-search"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <!-- /.input-group -->
                                </form>
                            </div>
                        </div>
                        <!-- /.error-page -->
                    </section>
                    <!-- /.content -->
                </div>
        </div>
        <!-- ./wrapper -->

        <!-- REQUIRED SCRIPTS -->

        <!-- jQuery -->
        <script src="plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/adminlte.min.js"></script>
    </body>
</html>
