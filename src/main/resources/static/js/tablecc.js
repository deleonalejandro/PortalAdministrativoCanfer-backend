
$(document).ready(function() {

	var table = $('#formularios').DataTable({
		"drawCallback": function(settings) {
			table.columns.adjust();
		},
		ajax: {
			url: "/cajachicaclient/loadallforms?idSucursal=" + Cookies.get("suc"),
			dataSrc: ""
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
						return '<span class="badge badge-blue">' + data + '</span>';
					}
				}
			},
			{ data: "responsable" },
			{ data: "total" }
		],

		"order": [[3, "desc"]],
		"columnDefs": [
			{ "width": "2%", "targets": [0, 1, 2] }
		]
	});

	//Crea la tabla de los detalles
	var table2 = $('#detallesNuevoCajaChica').DataTable({
		"drawCallback": function(settings, row, data, start, end, display) {
			table2.columns.adjust();

			var total = this.api()
				.column(9)
				.data()
				.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

			$('#totalNew').val(total);

		},
		"paging": false,
		"ordering": false,
		"info": false,
		"searching": false,
		ajax: {
			url: '/documentosFiscalesApi/init',
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
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0" href="/cajachicaclient/deletedetformcc?id=' + $("#idFormNew").val() + '"><i data-feather="trash"></i><script> feather.replace()</script></a>';
				},
			},
			{ data: "nombreClasificacion" },

			{
				data: "nombreArchivoPDF",
				"className": 'detpdf-control',
				"render": function(data,row) {
					if (data != null) {
						return '<a href="/cajachicaclient/download/pdf?='+ row.idDocumento+'"><u><font color="blue">' + data + '</font></u></a>';
					} else {
						return '<a>N/D</a>';
					}
				}
			},
			{
				data: ["nombreArchivoXML","idDocumento"],
				"className": 'detxml-control',
				"render": function(row) {
					if (data != null) {
						return '<a href="/cajachicaclient/download/xml?='+ row.idDocumento+'"><u><font color="blue">' + data + '</font></u></a>';
					} else {
						return '<a>N/D</a>';
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
			{ "width": "2%", "targets": [0, 1] }
		]

	});


	//Nueva Form
	$('#btn-add-form').on('click', function(event) {


		//Llena los parametros del formulario 
		var href = $(this).attr('href');

		$.get(href, function(formulario, status) {
			llenarFormulario(formulario);

			//prepare cancel button too
			$("#cancelarNewForm").attr("href", "/cajachicaclient/cancelarformcc?id=" + formulario.idFormularioCajaChica);
		});
		
		table2.ajax.url("/cajachicaclient/loadformdetails?id=" + formulario.idFormularioCajaChica).load();

		deshabilitarEntradas();

	});

	//Darle submit a un nuevo detalle
	$('#formNewDet').submit(function(event) {

		event.preventDefault();

		var data = new FormData(this);

		var url = "/cajachicaclient/savedetformcc";

		var saveDet = $.ajax({
			url: url,
			data: data,
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
		});

		saveDet.done(function(upload){

			if(upload == true) {
				$('.alert-success').prop('hidden', false);
			} else {
				$('.alert-danger').prop('hidden', false);
			}
			
			setTimeout(function(){
				$('.alert').prop('hidden', true);
				
			}, 4000);
			
			
		});
		saveDet.always(function() {
			

			table2.ajax.url("/cajachicaclient/loadformdetails?id=" + $("#idFormNew").val()).load();
			
			$('#newDetModal').modal('hide');
			
			reestablecerModal();
			
			deshabilitarPDF();
	
			deshabilitarXML();


		});



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
		
		reestablecerModal();

	});
	// Cancelar el upload xml
	$('#closexml').on('click', function() {
		
		deshabilitarXML();

		
	});
	// Cancelar el upload pdf
	$('#closepdf').on('click', function() {

		deshabilitarPDF();
		
	});
	// Darle Cancelar a New Det
	$('#cancelarNewDet').on('click', function() {

		$('#newDetModal').modal('hide');
		
		reestablecerModal();


	});

	// Darle Cancelar a New Formulario
	$('#cancelarNewForm').on('click', function(event) {


		var href = $(this).attr('href');

		$.get(href);

		habilitarEntradas();


	});
	
	// Darle Submit a New Formulario
	$('#submitNewForm').on('click', function() {
		
		habilitarEntradas();

	});

	// Adjuntar un pdf al new details
	$('#btn-add-pdf').on('click', function() {
		
		habilitarPDF();

		$('#siguienteNewDet').prop("disabled", false);
	});
	
	// adjuntar un xml al new details
	$('#btn-add-xml').on('click', function() {

		habilitarXML();
		
		$('#siguienteNewDet').prop("disabled", false);
	});


	// Funcion para modal de informacion de detalle

	$('#detallesNuevoCajaChica tbody').on('click', 'td.detailsdet-control', 'tr', function(event) {

		var tr = $(this).closest('tr');
		var jsonData = table.row($(this).parents(tr)).data();
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

		table2.ajax.url("/cajachicaclient/loadformdetails?id=" + $("#idFormNew").val()).load();

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
		llenarFormulario(formulario);

		//prepare cancel button too
		$("#cancelarNewForm").attr("href", "#!");
		
		table2.ajax.url("/cajachicaclient/loadformdetails?id=" + $("#idFormNew").val()).load();

	});
	
	//Funcion para hacer una suma

	var intVal = function(i) {
		return typeof i === 'string' ?
			i.replace(/[\$,]/g, '') * 1 :
			typeof i === 'number' ?
				i : 0;
	};
	
	//Llenar el formulario con la info que se proporciona
	
	var llenarFormulario = function(formulario){
		
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
	}
	
	//Habilitar la carga de xml
	
	var habilitarXML = function() {
		
		document.getElementById("dateNewDiv").hidden = true;
		document.getElementById("montoNewDiv").hidden = true;
		document.getElementById("folioNewDiv").hidden = true;
		document.getElementById("provNewDiv").hidden = true;
		
		document.getElementById("div-add-xml").hidden = false;
		document.getElementById("div-btn-add-xml").hidden = true;
		
	}
	
	//Quitar un xml con sus divs
	
	var deshabilitarXML = function(){
		
		document.getElementById("dateNewDiv").hidden = false;
		document.getElementById("montoNewDiv").hidden = false;
		document.getElementById("folioNewDiv").hidden = false;
		document.getElementById("provNewDiv").hidden = false;
		
		document.getElementById("fechaDet").value = "";
		document.getElementById("realDate").value = "";
		document.getElementById("monto").value = "";
		document.getElementById("folio").value = "";
		document.getElementById("nombreProveedor").value = "";
		
		document.getElementById("xml").value = "";
		
		document.getElementById("div-add-xml").hidden = true;
		document.getElementById("div-btn-add-xml").hidden = false;
		
	}
	
	//Habilitar la carga de pdf 
	
	var habilitarPDF = function(){
		
		document.getElementById("div-add-pdf").hidden = false;
		document.getElementById("div-btn-add-pdf").hidden = true;
		
	}
	
	//Quitar un pdf con sus divs

	var deshabilitarPDF = function(){
		
		document.getElementById("div-add-pdf").hidden = true;
		document.getElementById("div-btn-add-pdf").hidden = false;

		document.getElementById("pdf").value = "";
		
	}
	
	//Deja en blanco los parametros y reestablece los divs
	
	var reestablecerModal = function(){
		
		$('#siguienteNewDet').prop("disabled", true);
		$('#wizard2-tab').removeClass('active');
		$('#wizard1-tab').addClass('active');
		document.getElementById("wizard2").hidden = true;
		document.getElementById("btns-next").hidden = false;
		document.getElementById("btns-prev").hidden = true;
		
		document.getElementById("formNewDet").reset();
		
		deshabilitarPDF();

		deshabilitarXML();
		
		
	}
	
	//Habilitar vista tabla entradas
	
	var habilitarEntradas = function(){
		
		document.getElementById("divTabla").hidden = false;
		document.getElementById("divNuevo").hidden = true;
		
		
	}
	
	
	//Deshabilitar vista tabla entradas

	var deshabilitarEntradas = function() {
		
		document.getElementById("divTabla").hidden = true;
		document.getElementById("divNuevo").hidden = false;
		
		
	}
	
});