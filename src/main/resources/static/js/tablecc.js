
$(document).ready(function() {

	var table = $('#formularios').DataTable({
		ajax: {
			url: "/cajachicaclient/loadallforms?idSucursal=" + Cookies.get("suc"),
			dataSrc:""
		},
		scrollX: true,
		stateSave: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		"columns": [
			{
				"className": 'zip-control',
				"orderable": false,
				"data": "idFormularioCajaChica",
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0" href="/cajachicaclient/excel?id="><i class="fas fa-file-archive 2x"></i></a>'
				},
			},
			{
				"className": 'details-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="list"></i><script> feather.replace()</script></a>'
				},
			},
			{
				"className": 'delete-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="trash"></i><script> feather.replace()</script></a>';
				},
			},

			{ data: "fecha" },
			{ data: "folio" },
			{
				data: "estatus",
				"render": function(data) {
					if (data.toUpperCase() == 'CERRADO') {
						return '<span class="badge badge-orange">Cerrado</span>';
					}
					if (data.toUpperCase() == 'ABIERTO') {
						return '<span class="badge badge-green">Abierto</span>';
					}
					if (data.toUpperCase() == 'CANCELADO') {
						return '<span class="badge badge-red">Cancelado</span>';
					}
					else {
						return '<span class="badge badge-blue">'+data+'</span>';
					}
				}
			},
			{ data: "responsable" },
			{ data: "total" }
		],

		"order": [[3,  "desc"]],
		"columnDefs": [
		    { "width": "2%", "targets": [0,1,2] }
		  ]
	});


	//Nueva Form
	$('#btn-add-form').on('click', function(event) {

		
		//Llena los parametros del formulario 
		var href = $(this).attr('href');

		$.get(href, function(formulario, status) {
			$("#folioFormularioNew").text(formulario.folio);
			$("#estatusNewForm").val(formulario.estatus);
			$("#idCajaChicaNew").val(formulario.claveProvSucursal);
			$("#sucursalNew").val(formulario.nombreSucursal);
			$("#responsableNew").val(formulario.responsable);
			$("#fechaNew").val(formulario.fecha.split("T")[0]);
			$("#idFormNew").val(formulario.idFormularioCajaChica);
			$("#comentarioNew").val(formulario.comentario);
			$("#totalNew").val(formulario.total);
			$("#responsableNew").val(formulario.responsable);
			$("#idFormulario").val(formulario.idFormularioCajaChica);

			//prepare cancel button too
			$("#cancelarNewForm").attr("href", "/cajachicaclient/cancelarformcc?id=" + formulario.idFormularioCajaChica);
		});
		
		document.getElementById("divTabla").hidden = true;
		document.getElementById("divNuevo").hidden = false;

		//Crea la tabla de los detalles
		var table2 = $('#detallesNuevoCajaChica').DataTable({
			"drawCallback": function(settings, row, data, start, end, display) {
				table2.columns.adjust();
				
				var api = this.api(), data;
				

				var total = this.api()
                .column( 9 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

				$('#totalNew').val(total);
				
			},
			"paging": false,
			"ordering": false,
			"info": false,
			"searching": false,
			ajax: {
				url: "/cajachicaclient/loadformdetails?id=" + $("#idFormNew").val(),
				dataSrc: ""
			},
			scrollX: true,
			"language": {
				"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			},
			"columns": [
				{
					"className": 'detailsdet-control',
					"orderable": false,
					"bSortable": false,
					"data": null,
					"render": function() {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="list"></i><script> feather.replace()</script></a>'
					},
				},
				{
					"className": 'deletedet-control',
					"orderable": false,
					"bSortable": false,
					"data": null,
					"render": function() {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0" href="/cajachicaclient/deletedetformcc?id=' +  $("#idFormNew").val() + '"><i data-feather="trash"></i><script> feather.replace()</script></a>';
					},
				},
				{ data: "nombreClasificacion" },
				
				{data: "nombreArchivoPDF",
					"className": 'detpdf-control',
					"render": function(data) {
						if (data != null) {
							return '<u><font color="blue"><br>'+data+'</font></u>';
						}
					}
				},
				{data: "nombreArchivoXML",
					"className": 'detxml-control',
					"render": function(data) {
						if (data != null) {
							return '<u><font color="blue"><br>'+data+'</font></u>';
						}
					}
				},
				{ data: "folio" },
				{ data: "nombreProveedor" },
				{ data: "fecha" },
				{ data: "beneficiario" },
				{ data: "monto" }
			],

			"order": [[0, "asc"]],
			"columnDefs": [
		    { "width": "2%", "targets": [0,1] }
		  ]

		});

	});


	// Darle submit a New Det
	$('#submitNewDet').on('click', function(event) {
		
		$('#newDetModal').modal('hide');
		$('#wizard2-tab').removeClass('active');
		$('#wizard1-tab').addClass('active');
		document.getElementById("wizard2").hidden = true;
		document.getElementById("btns-next").hidden = false;
		document.getElementById("btns-prev").hidden = true;
		$('#siguienteNewDet').prop("disabled", true);
		document.getElementById("div-add-xml").hidden = true;
		document.getElementById("div-btn-add-xml").hidden = false;
		document.getElementById("div-add-pdf").hidden = true;
		document.getElementById("div-btn-add-pdf").hidden = false;

		document.getElementById("dateNewDiv").hidden = false;
		document.getElementById("montoNewDiv").hidden = false;
		document.getElementById("folioNewDiv").hidden = false;
		document.getElementById("provNewDiv").hidden = false;
		

	});
	
	$('#formNewDet').submit( function(event) {
		
		event.preventDefault();
		
		var data = new FormData(this);
				
		var url = "/cajachicaclient/savedetformcc";
		
		$.ajax({
				url: url,
				data: data,
				cache: false,
				contentType: false, 
				processData: false,
				type: 'POST',
				success: function(response){
					document.getElementById("formNewDet").reset();
					table2.columns.adjust();
					$('#detallesNuevoCajaChica').DataTable().ajax.reload();
				
					var api = this.api(), data;
					
	
					var total = this.api()
	                .column( 9 )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	
					$('#totalNew').val(total);
				}
		})
		
		
		
		
	})
	
	// Darle Siguiente a New Det
	$('#siguienteNewDet').on('click', function() {

		$('#wizard1-tab').removeClass('active');
		$('#wizard2-tab').addClass('active');
		document.getElementById("wizard2").hidden = false;
		document.getElementById("btns-next").hidden = true;
		document.getElementById("btns-prev").hidden = false;


	});
	// Darle Anterior a New Det
	$('#anteriorNewDet').on('click', function() {
		$('#wizard2-tab').removeClass('active');
		$('#wizard1-tab').addClass('active');
		$('#siguienteNewDet').prop("disabled", true);
		document.getElementById("wizard2").hidden = true;
		document.getElementById("btns-next").hidden = false;
		document.getElementById("btns-prev").hidden = true;

		document.getElementById("div-add-xml").hidden = true;
		document.getElementById("div-btn-add-xml").hidden = false;
		document.getElementById("div-add-pdf").hidden = true;
		document.getElementById("div-btn-add-pdf").hidden = false;

		document.getElementById("dateNewDiv").hidden = false;
		document.getElementById("montoNewDiv").hidden = false;
		document.getElementById("folioNewDiv").hidden = false;
		document.getElementById("provNewDiv").hidden = false;

		document.getElementById("formNewDet").reset();
	});
	// Cancelar el upload xml
	$('#closexml').on('click', function() {

		document.getElementById("div-add-xml").hidden = true;
		document.getElementById("div-btn-add-xml").hidden = false;
		document.getElementById("dateNewDiv").hidden = false;
		document.getElementById("montoNewDiv").hidden = false;
		document.getElementById("folioNewDiv").hidden = false;
		document.getElementById("provNewDiv").hidden = false;

		document.getElementById("xml").value = "";
	});
	// Cancelar el upload pdf
	$('#closepdf').on('click', function() {

		document.getElementById("div-add-pdf").hidden = true;
		document.getElementById("div-btn-add-pdf").hidden = false;

		document.getElementById("pdf").value = "";
	});
	// Darle Cancelar a New Det
	$('#cancelarNewDet').on('click', function() {

		$('#newDetModal').modal('hide');
		$('#siguienteNewDet').prop("disabled", true);
		$('#wizard2-tab').removeClass('active');
		$('#wizard1-tab').addClass('active');
		document.getElementById("wizard2").hidden = true;
		document.getElementById("btns-next").hidden = false;
		document.getElementById("btns-prev").hidden = true;

		document.getElementById("div-add-xml").hidden = true;
		document.getElementById("div-btn-add-xml").hidden = false;
		document.getElementById("div-add-pdf").hidden = true;
		document.getElementById("div-btn-add-pdf").hidden = false;

		document.getElementById("dateNewDiv").hidden = false;
		document.getElementById("montoNewDiv").hidden = false;
		document.getElementById("folioNewDiv").hidden = false;
		document.getElementById("provNewDiv").hidden = false;

	});

	// Darle Submit a New Formulario
	$('#cancelarNewForm').on('click', function(event) {


		var href = $(this).attr('href');

		$.get(href);

		document.getElementById("divTabla").hidden = false;
		document.getElementById("divNuevo").hidden = true;
		table2.destroy();



	});
	// Darle Cancelar a New Formulario
	$('#submitNewForm').on('click', function() {

		document.getElementById("divTabla").hidden = false;
		document.getElementById("divNuevo").hidden = true;
		table2.destroy();


	});

	// Adjuntar un pdf al new details
	$('#btn-add-pdf').on('click', function() {
		document.getElementById("div-add-pdf").hidden = false;
		document.getElementById("div-btn-add-pdf").hidden = true;

		$('#siguienteNewDet').prop("disabled", false);
	});
	// adjuntar un xml al new details
	$('#btn-add-xml').on('click', function() {
		document.getElementById("div-add-xml").hidden = false;
		document.getElementById("div-btn-add-xml").hidden = true;

		document.getElementById("dateNewDiv").hidden = true
		document.getElementById("montoNewDiv").hidden = true
		document.getElementById("folioNewDiv").hidden = true
		document.getElementById("provNewDiv").hidden = true;
		$('#siguienteNewDet').prop("disabled", false);
	});


	// Funcion para modal de informacion de detalle

	$('#detallesNuevoCajaChica tbody').on('click', 'td.detailsdet-control', 'tr', function(event) {

		var tr = $(this).closest('tr');
		var data = table.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		$('#headerValue').text('Detalle ' + jsonData.folio)
		$('#detail-fechaDet').val(jsonData.fecha);
		$('#detail-clasificacion').val(jsonData.clasificacion);
		$('#detail-monto').val(jsonData.monto);
		$('#detail-folio').val(jsonData.folio);
		$('#detail-beneficiario').val(jsonData.beneficiario);
		$('#detail-nombreProveedor').val(jsonData.nombreProveedor);
		
		$('#detailsModal').modal('show');
		
		

	});


	// Funcion para delete formulario

	$('#formularios tbody').on('click', 'td.delete-control', 'tr', function(event) {

		event.preventDefault();

		var tr = $(this).closest('tr');
		var data = table.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		$('.deleteForm .delBtn').attr("href", "/cajachicaclient/deleteformcc?id=" + jsonData.idFormularioCajaChica);
		$('#deleteModal').modal('show');
	});
	
	// Funcion para delete detalle

	$('#detallesNuevoCajaChica tbody').on('click', 'td.deletedet-control', 'tr', function(event) {
				
				var api = this.api(), data;
				

				var total = this.api()
                .column( 9 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

				$('#totalNew').val(total);

		
		
	});

	// Funcion para detalles del Formulario

	$('#formularios tbody').on('click', 'td.details-control', function() {
		var tr = $(this).closest('tr');
		var data = table.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var formulario = JSON.parse(modData);

		document.getElementById("divTabla").hidden = true;
		document.getElementById("divNuevo").hidden = false;
		
		//Llena los parametros del formulario 
		$("#folioFormularioNew").text(formulario.folio);
		$("#estatusNewForm").val(formulario.estatus);
		$("#idCajaChicaNew").val(formulario.claveProvSucursal);
		$("#sucursalNew").val(formulario.nombreSucursal);
		$("#fechaNew").val(formulario.fecha.split("T")[0]);
		$("#idFormNew").val(formulario.idFormularioCajaChica);
		$("#comentarioNew").val(formulario.comentario);
		$("#responsableNew").val(formulario.responsable);
		$('#nombreProveedor').val(formulario.nombreProveedor);
		$("#totalNew").val(formulario.total);
		$("#idFormulario").val(formulario.idFormularioCajaChica);

		//prepare cancel button too
		$("#cancelarNewForm").attr("href", "#!");


		//Crea la tabla de los detalles
		var table2 = $('#detallesNuevoCajaChica').DataTable({
			"drawCallback": function(settings, row, data, start, end, display) {
				table2.columns.adjust();
				
				var api = this.api(), data;
				

				var total = this.api()
                .column( 9 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );

				$('#totalNew').val(total);
				
			},
			"paging": false,
			"ordering": false,
			"info": false,
			"searching": false,
			ajax: {
				url: "/cajachicaclient/loadformdetails?id=" + $("#idFormNew").val(),
				dataSrc: ""
			},
			scrollX: true,
			"language": {
				"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			},
			"columns": [
				{
					"className": 'detailsdet-control',
					"orderable": false,
					"bSortable": false,
					"data": null,
					"render": function() {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="list"></i><script> feather.replace()</script></a>'
					},
				},
				{
					"className": 'deletedet-control',
					"orderable": false,
					"bSortable": false,
					"data": null,
					"render": function() {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0" href="/cajachicaclient/deletedetformcc?id=' +  $("#idFormNew").val() + '"><i data-feather="trash"></i><script> feather.replace()</script></a>';
					},
				},
				{ data: "nombreClasificacion" },
				
				{data: "nombreArchivoPDF",
					"className": 'detpdf-control',
					"render": function(data) {
						if (data != null) {
							return '<u><font color="blue"><br>'+data+'</font></u>';
						}
					}
				},
				{data: "nombreArchivoXML",
					"className": 'detxml-control',
					"render": function(data) {
						if (data != null) {
							return '<u><font color="blue"><br>'+data+'</font></u>';
						}
					}
				},
				{ data: "folio" },
				{ data: "nombreProveedor" },
				{ data: "fecha" },
				{ data: "beneficiario" },
				{ data: "monto" }
			],

			"order": [[0, "asc"]],
			"columnDefs": [
		    { "width": "2%", "targets": [0,1] }
		  ]

		});





	});
	
	var intVal = function ( i ) {
                return typeof i === 'string' ?
                    i.replace(/[\$,]/g, '')*1 :
                    typeof i === 'number' ?
                        i : 0;
            };



});