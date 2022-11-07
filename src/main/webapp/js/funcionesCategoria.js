$(document).ready(function () {
    $("#activarCate").click(function (e) {
        e.preventDefault();
        var idc = $(this).parent().find('#item').val();        
        swal({
            title: "Esta Seguro de Activar esta Categoria?",
            text: "Una una Vez Activado, Se Volverá a mostrar en un nuevo registro de producto!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                activarCat(idc);
                swal(" ¡Oh! ¡Categoria Activada! ", {
                    icon: "success",
                }).then((willDelete) => {
                    if (willDelete) {
                        parent.location.href = "Categorias?accion=listar";
                    }
                });
            }
        });
    });
});
function activarCat(idc) {
        var url = "Categorias?cambiar=des&cod=" + idc;
        console.log("hol");
        $.ajax({
            type: 'POST',
            url: url,
            async: true,
            success: function (r) {
            }
        });
    }
    
$(document).ready(function () {
    $("#desactivarCate").click(function (e) {
        e.preventDefault();
        var idc = $(this).parent().find('#item').val();        
        swal({
            title: "Esta Seguro de Desactivar esta Categoria?",
            text: "Una una Vez Desactivada, No se mostrará en un nuevo registro de producto!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                desactivarCat(idc);
                swal(" ¡Oh! ¡Categoria Desactivada! ", {
                    icon: "success",
                }).then((willDelete) => {
                    if (willDelete) {
                        parent.location.href = "Categorias?accion=listar";
                    }
                });
            }
        });
    });
});
function desactivarCat(idc) {
        var url = "Categorias?cambiar=act&cod=" + idc;
        console.log("hol");
        $.ajax({
            type: 'POST',
            url: url,
            async: true,
            success: function (r) {
            }
        });
    }
    
////$(document).ready(function () {
//    $("tr #deleteCategoria").click(function (e) {
//        e.preventDefault();
//        var idCate = $(this).parent().find('#codigo').val();
//        swal({
//            title: "Está Seguro de Eliminar?",
//            text: "Una Vez Eliminado, Deberá Agregar de nuevo!",
//            icon: "warning",
//            buttons: true,
//            dangerMode: true
//        }).then((willDelete) => {
//            if (willDelete) {
//                eliminarCategoria(idCate);
//                swal(" ¡Oh! ¡Registro Borrado! ", {
//                    icon: "success",
//                }).then((willDelete) => {
//                    if (willDelete) {
//                        parent.location.href = "srvCategoria";
//                    }
//                });
//            }
//        });
//    });
//    }    
//
//    function eliminarCategoria(idCate) {
//        var url = "srvCategoria?accion=eliminar&cod=" + idCate;
//        console.log("Eliminado");
//        $.ajax({
//            type: 'POST',
//            url: url,
//            async: true,
//            success: function (r) {
//            }
//        });
//    }



