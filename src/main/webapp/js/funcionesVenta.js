$(function() {
    
    var idproducto = 0,
        array_productos = [];
    
    $('#cbocomprobante').change(function(ev) {
        obtenerCorrelativo(this.value);
    });
    
    $('#txtproducto').keydown(function(ev) {
        if (ev.keyCode === 13) {
            $('#btn_buscar').focus();
            ev.preventDefault();
        }
    });
    
    $('#btn_buscar').keydown(function(ev) {
        if (ev.keyCode === 13) {
            if ($('#txtproducto').val().trim() != '') {
                obtenerProducto($('#txtproducto').val());
            } else {
                $('#txtproducto').focus();
            }
            ev.preventDefault();
        }
    }).click(function(ev) {
        if ($('#txtproducto').val().trim() != '') {
            obtenerProducto($('#txtproducto').val());
        } else {
            $('#txtproducto').focus();
        }
    });
    
    $('#txtcantidad').keydown(function(ev) {
        if (ev.keyCode === 13) {
            $('#btn_agregar').focus();
            ev.preventDefault();
        }
    });
    
    $('#btn_agregar').keydown(function(ev) {
        if (ev.keyCode === 13) {
            agregar_producto_array();
            ev.preventDefault();
        }
    });
    
    $('#form_venta').submit(function(ev) {
        var self = this;
        swal({
            title: "Confirmar",
            text : "¿Está seguro de guardar los datos?",
            showCancelButton: true,
            confirmButtonColor: "#4CAF50",
            confirmButtonText: "Sí",
            cancelButtonText: "No",
            closeOnConfirm: false,
            closeOnCancel: true
        }, function(isConfirm) {
            if (isConfirm) {
                procesar_venta(self);
            }
        });
        ev.preventDefault();
    });
    
    $('#table_body').on('click', '.btn-delete', function(ev) {
        var $tr = $(this).parents('tr'),
            position = $tr.rowIndex - 1;
        array_productos.splice(position, 1);
        refrescar_array_producto();
    });
    
    
    function agregar_producto_array() {
        var is_validate = true,
            precio = $('#txtprecio').val(),
            cantidad = $('#txtcantidad').val(),
            nombre_producto = $('#txtproducto').val().trim();
    
        if (idproducto == 0) is_validate = false;
        if (precio == '') is_validate = false;
        if (cantidad == '' || cantidad == 0) is_validate = false;
        if (nombre_producto == '') is_validate = false;
        
        if (is_validate) {
            array_productos.push({
                producto: idproducto,
                nombre: nombre_producto,
                precio: precio,
                cantidad: cantidad,
                total: precio * cantidad
            });
            
            limpiar_inputs_producto();
            refrescar_array_producto();
        }
    }
    
    function limpiar_inputs_producto(is_agregar=true) {
        idproducto = 0;
        if (is_agregar) $('#txtproducto').val('');
        $('#txtprecio').val('');
        $('#txtcantidad').val('');
        $('#txtproducto').focus();
    }
    
    function refrescar_array_producto() {
        var template = '', total = 0;
        for (var i = 0; i < array_productos.length; i++) {
            template += '<tr>';
            template += '<td>'+ (i+1) +'</td>';
            template += '<td>'+ array_productos[i].nombre +'</td>';
            template += '<td>'+ array_productos[i].cantidad +'</td>';
            template += '<td>'+ array_productos[i].precio +'</td>';
            template += '<td>'+ array_productos[i].total +'</td>';
            template += '<td><button type="button" class="btn btn-danger btn-delete" title="Eliminar" title="Editar"><i class="fas fa-trash"></i></button></td>';
            template += '</tr>';
            total += (array_productos[i].total);
        }
        $('#table_body').html(template);
        
        var subtotal = total / 1.18;
        var igv = total - subtotal;
        
        $('#txtsubtotal').val(subtotal.toFixed(2));
        $('#txtigv').val(igv.toFixed(2));
        $('#txttotal').val(total.toFixed(2));
    }
    
    function procesar_venta(self) {
        var elements = self.elements, 
                objventa = {};
        objventa.cliente = elements[1].value;
        objventa.comprobante = elements[2].value;
        objventa.serie = elements[3].value;
        objventa.correlativo = elements[4].value;
        
        if (array_productos.length > 0) {
            registrarVenta(objventa, array_productos);
        } else {
            alert('Detalle de venta no puede ir vacío.');
        }
    }
    
    function obtenerProducto(descripcion) {
        $.ajax({
            type: 'POST',
            url: "srvVentas",
            data: {
              action: 'obtenerProducto',
              descripcion: descripcion
            },
            dataType: 'json',
            success: function (data) {
                var response = data;
                if (response.estado && response.data !== null) {
                    idproducto = response.data.codigopro;
                    $('#txtprecio').val(response.data.preciopro);
                    $('#txtcantidad').focus();
                } else {
                    limpiar_inputs_producto(false);
                }
            }
        });
    }
    
    function obtenerCorrelativo(tipo) {
        $.ajax({
            type: 'POST',
            url: "srvVentas",
            data: {
              action: 'obtenerCorrelativo',
              tipo: tipo
            },
            dataType: 'json',
            success: function (data) {
                var response = data;
                if (response.estado) {
                    $('#txtserie').val(response.data.serie);
                    $('#txtcorrelativo').val(response.data.correlativo);
                }
            }
        });
    }
    
    function cargarDatos() {
        $.ajax({
            type: 'POST',
            url: "srvVentas",
            data: {
              action: 'cargarDatos'  
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                var response = data;
                if (response.estado) {
                    var data = response.data, 
                            template = '';
                    for (var i = 0; i < data.length; i++) {
                        template += '<option value="'+ data[i].codigo +'">'+ data[i].nombre +'</option>';
                    }
                    $('#cbocliente').append(template);
                }
            }
        });
    };
    
    function cargarPagos() {
        $.ajax({
            type: 'POST',
            url: "srvVentas",
            data: {
              action: 'cargarTipoPago'  
            },
            dataType: 'json',
            async: true,
            success: function (data) {
                var response = data;
                if (response.estado) {
                    var data = response.data, 
                            template = '';
                    for (var i = 0; i < data.length; i++) {
                        template += '<option value="'+ data[i].codigopag +'">'+ data[i].nombretipopag +'</option>';
                    }
                    $('#cbotipopago').append(template);
                }
            }
        });
    };
    
    function registrarVenta(data, detalles) {
        $.ajax({
            type: 'POST',
            url: "srvVentas",
            data: {
              action: 'registrar',
              data_venta: JSON.stringify(data),
              detalles: JSON.stringify(detalles) 
            },
            dataType: 'json',
            success: function (data) {
                if (data.estado) {
                    swal("Éxito", "Venta registrada correctamente", "success");
                    $('#form_venta')[0].reset();
                    $('#cbocomprobante').val('B').change();
                    array_productos = [];
                    refrescar_array_producto();
                } else {
                    swal("Error", data.msj, "error");
                }
            }
        });
    };
    
    cargarDatos();
    cargarPagos();
});
