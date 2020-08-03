$(document).ready(function() {
    $('#userTable').DataTable({
		"sAjaxSource": "/usuarios/getUsers",
		"sAjaxDataProp": "",
		"order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "username" },
            { "mData": "password" },
            { "mData": "nombre" },
            { "mData": "apellido" },
            { "mData": "correo" },
            { "mData": "activo" },
			{ "mData": "rol" },
			{ "mData": "permisos"},
			
			

        ]
    });
});
