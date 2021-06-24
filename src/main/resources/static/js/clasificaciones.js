$(document).ready(function() {
	// Tabla de proveedores
	var tableProveedor = $('#proveedorTable').DataTable({
		select: true,
		ajax: {
			url: "/cajachicaclient/getclasificaciones",
			dataSrc: ""
		},
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		columns: [
			{
				"className": 'delete-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function(row) {
					return '<a href="/cajachicaclient/removeclasificacion?id=' + row.idClasificacionCajaChica  +' class="deleteBtn btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="trash-2"></i></a><script>feather.replace()</script>';

				},
			},
			{ data: "clasificacion" },
		]
	});
	
	
 

});
