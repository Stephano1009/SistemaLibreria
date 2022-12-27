$(document).ready(function () {

});

function cambiarLength(option) {
    var valueTipoDocumento = option.value;
    if (valueTipoDocumento) {
        if (valueTipoDocumento === 'D') {
            $('#documento').removeAttr('disabled');
            $('#documento').val('');
            $('#documento').attr('maxlength', '8');
        } else if (valueTipoDocumento === 'R') {
            $('#documento').removeAttr('disabled');
            $('#documento').val('');
            $('#documento').attr('maxlength', '11');
        } else {
            $('#documento').attr('disabled', '');
            $('#documento').val('');
        }
    }
}