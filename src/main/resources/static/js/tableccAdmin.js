$(document).ready(function() {


	var table = $('#formularios').DataTable({
		"drawCallback": function(settings) {
			table.columns.adjust();
		},
		ajax: {
			url: "/cajachicaclient/loadallcanceledforms?idSucursal=" + Cookies.get("suc"),
			dataSrc: ""
		},
		scrollX: true,
		stateSave: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		"columns": [
			{
				"className": 'open-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<button class="btn-transparent-dark" type="button">Abrir Formulario</button>';
				},
			},
			{
				"className": 'delete-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function(row) {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="trash"></i><script> feather.replace()</script></a>';
	
				},
			},

			{ data: "fecha",
				"render": function(data){
					return data.split("T")[0]
				}
			
			},
			{ data: "folio" },
			{
				data: "estatus",
				"render": function(data) {
				
					return '<span class="badge badge-red">CANCELADO</span>';
					
				}
			},
			{ data: "responsable" },
			{ data: "total" }
		],

		"order": [[3, "desc"]],
		"columnDefs": [
			{ "width": "2%", "targets": [1] }
		]
	});

	
	// Funcion para delete formulario

	$('#formularios tbody').on('click', 'td.delete-control', 'tr', function(event) {
		
		event.preventDefault();

		var jsonData = table.row(this).data();
	
		
		$('#deleteForm').modal('show');
			
		$('#confirmDeleteForm').click(function(){
		
			var borrarForm = $.ajax({
							  url: "/cajachicaclient/deletecanceledformcc?id=" + jsonData.idFormularioCajaChica,
							  cache: false,
							  contentType: false,
							  processData: false,
							  type: 'GET',
							});
			
			borrarForm.done(function(success) {
				table.ajax.reload( null, false );
				$('#deleteForm').modal('hide');
				
				if (success == 'true') {
					$('#alert-delete').prop('hidden', false);
				} else {
					$('#alert-delete-error').prop('hidden', false);
				} 
		
				setTimeout(function() {
					$('.alert').prop('hidden', true);
		
				}, 6000);
			})
			
			
		
		})
		
		
	});

	// Funcion para open formulario

	$('#formularios tbody').on('click', 'td.open-control', 'tr', function(event) {
		
		event.preventDefault();

		var jsonData = table.row(this).data();
	

			$('#openForm').modal('show');
			
			$('#confirmOpenForm').click(function(){
			
				var openForm = $.ajax({
								  url: "/cajachicaclient/openformcc?id=" + jsonData.idFormularioCajaChica,
								  cache: false,
								  contentType: false,
								  processData: false,
								  type: 'GET',
								});
				
				openForm.done(function(success) {
					table.ajax.reload( null, false );
					$('#openForm').modal('hide');
					
					
				if (success == 'true') {
					$('#alert-open').prop('hidden', false);
				} else {
					$('#alert-open-error').prop('hidden', false);
				} 
		
				setTimeout(function() {
					$('.alert').prop('hidden', true);
		
				}, 6000);
				})
			
			})
		
		
	});

	
	
});