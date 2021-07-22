// this function creates the url with parameters to initialize the table
function getInitUrl() {

	var getUrl = window.location;
	var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
	var myUrlWithParams;
	var start = moment().subtract(3, "month").startOf("month");
	var end = moment().endOf("day");

	myUrlWithParams = new URL("/proveedorApi", baseUrl);

	myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
	myUrlWithParams.searchParams.append("proveedor", $("#selectedClave").text());
	myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));
	myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));


	return myUrlWithParams.href;

}

//Tabla en si

$(document).ready(function() {

	//Initial Values
	$('#antiguedad').val(moment().subtract(3, "month").startOf("month"))

	var table = $('#facturas').DataTable({
		"drawCallback": function(settings, json) {
			document.getElementById("reloadTableBtn").hidden = false;
			document.getElementById("reloadTableLoading").hidden = true;
		},
		ajax: {
			url: getInitUrl(),
			dataSrc: ""
		},
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		"columns": [

			{
				"className": 'modal-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="list"></i><script> feather.replace()</script></a>'
				},
			},

			{
				"className": 'pago-control',
				data: "estatusPago",
				"orderable": false,
				"bSortable": false,
				"render": function(data) {
					if (data.toUpperCase().includes('PAGADO')){
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="active" data-feather="dollar-sign"></i><script> feather.replace()</script></a>';
					} else {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0 disabled"><i data-feather="dollar-sign"></i><script> feather.replace()</script></a>';
					}
				}
			},
			{
				"className": 'comment-control',
				data: "comentario",
				"orderable": false,
				"bSortable": false,
				"render": function(data) {
					if (data == "") {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark disabled  m-0"><i data-feather="message-square"></i><script> feather.replace()</script></a>';
					} else {
						return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="active" data-feather="message-square"></i><script> feather.replace()</script></a>';
					}
				}
			},

			{ data: "serie" },
			{ data: "folio" },
			{ data: "total" },
			{ data: "pagoTotalPago" },
			{ data: "pagoFecmvto" },
			{ data: "pagoIdNumPago" },
			{ data: "fechaCarga",
				"render": function(data){
						return data.split("T")[0]
					}
			},
			{ data: "fechaEmision" },
			{
				data: "tipoDocumento",
				"render": function(data) {
					if (data == 'I' || data.indexOf('I') === 0 || data.indexOf('i') === 0 ) {
						return '<button class="btn btn-pink btn-icon btn-xs" type="button">I</button>';
					}
					if (data == 'E' || data.indexOf('E') === 0 || data.indexOf('e') === 0 ) {
						return '<button class="btn btn-indigo btn-icon btn-xs" type="button">E</button>';
					}
					if (data == 'P' || data.indexOf('Nota') === 0 || data.indexOf('nota') === 0 ) {
						return '<button class="btn btn-teal btn-icon btn-xs" type="button">P</button>';
					}else {
						return '<span class="badge badge-blue">' + data + '</span>';
					}
				}
			},
			{
				data: "estatusPago",
				"render": function(data) {
					if (data == 'EN PROCESO') {
						return '<span class="badge badge-orange">Pendiente Pago</span>';
					}
					if (data.toUpperCase() == 'PAGO PENDIENTE' || data.toUpperCase() == 'PENDIENTE PAGO') {
						return '<span class="badge badge-orange">Pendiente Pago</span>';
					}
					if (data == 'PAGADO') {
						return '<span class="badge badge-green">Pagado</span>';
					}
					if (data == 'CANCELADO') {
						return '<span class="badge badge-red">Cancelado</span>';
					}
					if (data == 'RECHAZADO') {
						return '<span class="badge badge-red">Rechazado</span>';
					}else {
						return '<span class="badge badge-blue">' + data + '</span>';
					}
				}
			},
		],
		"order": [[10, "desc"]]
	});

	//Tabla de Avisos
	var table2 = $('#avisosDePago').DataTable({
		ajax: {
			url: "/proveedorApi/avisos/" + $("#selectedCompany").text() + "/" + $("#selectedClave").text(),
			dataSrc: ""
		},
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		"columns": [

			{
				"className": 'pdfAviso-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function(data) {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark float-left" href="/proveedoresClient/preview/singlePDF/Pago/' + data.idPago + '" target="_blank"><i class="fa fa-file-pdf fa-lg" style="color:red"></i></a>'
				},
			},
			{ data: "idNumPago" },
			{ data: "fecMvto" },
			{ data: "totalPago" },
			{ data: "totalFactura" }
		]

	});
	

	//check files size
	$('#InputGroupPDF').on('change', function() {
		if (this.files[0].size > 563200) {
			$('#InputGroupPDF').val('');

			$('#fileSizePdf').prop('hidden', false)

			setTimeout(function() {
				$('#fileSizePdf').prop('hidden', true);

			}, 6000);
		}
	})


	$('#inputGroupXML').on('change', function() {
		if (this.files[0].size > 563200) {
			$('#inputGroupXML').val('');
			$('#fileSizeXml').prop('hidden', false)

			setTimeout(function() {
				$('#fileSizeXml').prop('hidden', true);

			}, 6000);
		}
	})


	// Filters
	$('#reloadTableBtn').on('click', function() {

		document.getElementById("reloadTableBtn").hidden = true;
		document.getElementById("reloadTableLoading").hidden = false;

		var getUrl = window.location;
		var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
		var myUrlWithParams;

		var start = moment().subtract(3, "month").startOf("month");
		var end = moment().endOf("day");

		myUrlWithParams = new URL("/proveedorApi", baseUrl);
		myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));
		myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));
		myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
		myUrlWithParams.searchParams.append("proveedor", $("#selectedClave").text());
		myUrlWithParams.searchParams.append("estatusPago", $("#inputFiltroEstatus").val());
		myUrlWithParams.searchParams.append("registeredAfter", $("#registeredAfter").text());
		myUrlWithParams.searchParams.append("registeredBefore", $("#registeredBefore").text());
		myUrlWithParams.searchParams.append("sequenceAfter", $("#inputFiltroFolioInicial").val());
		myUrlWithParams.searchParams.append("sequenceBefore", $("#inputFiltroFolioFinal").val());
		myUrlWithParams.searchParams.append("serie", $("#inputFiltroSerie").val());

		table.ajax.url(myUrlWithParams.href).load();



	});

	// Clear filters
	$('#clearFilters').on('click', function() {

		var start = moment();
		var end = moment();

		$("#inputFiltroEstatus").val("");
		$("#inputFiltroFolioInicial").val('');
		$("#inputFiltroFolioFinal").val('');
		$("#inputFiltroSerie").val('');
		$("#reportrangeEmision span").text("");


	});


	// Funcion para modal detalles
	//Detalles
	$('#facturas tbody').on('click', 'td.modal-control', function() {
		var tr = $(this).closest('tr');
		var jsonData = table.row($(this).parents(tr)).data();

		llenarDetalles(jsonData);

		if (jsonData.pagos[0] != null) {
			habilitarPago(jsonData);
		}else{
			deshabilitarPago();
		}
		
		if (jsonData.comentario == "") {
			deshabilitarComentario();
		}else{
			habilitarComentario(jsonData);
		}

		$('#detallesFactura').addClass('show');
		$('#detallesFactura').addClass('active');
		$('#detallesFacturaTab').addClass("active");
		$("#comentarioFactura, #pagoFactura").removeClass("show");
		$("#comentarioFacturaTab, #pagoFacturaTab").removeClass("active");
		$("#comentarioFactura, #pagoFactura").removeClass("active");


		$('#detailsModal').modal('show');


	});
	//Pago
	$('#facturas tbody').on('click', 'td.pago-control', function() {
		var tr = $(this).closest('tr');
		var jsonData = table.row($(this).parents(tr)).data();

		if (jsonData.pagos[0] != null) {

			llenarDetalles(jsonData);
			habilitarPago(jsonData);

			if (jsonData.comentario != "") {
				habilitarComentario(jsonData);
			} else{
				deshabilitarComentario();
			}

			$('#pagoFactura').addClass("show");
			$('#pagoFacturaTab').addClass("active");
			$('#pagoFactura').addClass("active");
			$('#comentarioFactura, #detallesFactura').removeClass("show");
			$("#comentarioFacturaTab, #detallesFacturaTab").removeClass("active");
			$("#comentarioFactura, #detallesFactura").removeClass("active");

			$(' #detailsModal').modal('show');

		} else{
			deshabilitarPago();
		}
	});

	//Comentario
	$('#facturas tbody').on('click', 'td.comment-control', function() {
		var tr = $(this).closest('tr');
		var jsonData = table.row($(this).parents(tr)).data();

		if (jsonData.comentario != "") {
			llenarDetalles(jsonData);
			habilitarComentario(jsonData);

			if (jsonData.pagos[0] != null) {
				habilitarPago(jsonData);
			} else{
				deshabilitarPago();
			}

			$('#comentarioFactura').addClass("show");
			$('#comentarioFactura').addClass("active");
			$('#comentarioFacturaTab').addClass("active");
			$('#detallesFactura, #pagoFactura').removeClass("active");
			$('#detallesFactura, #pagoFactura').removeClass("show");
			$("#detallesFacturaTab, #pagoFacturaTab").removeClass("active");
			
			$('#detailsModal').modal('show');
		} else {
			deshabilitarComentario();
		}

	});
	
	// Llenar modal
	
	var llenarDetalles = function(jsonData){
		$(' #uuid').val(jsonData.uuid)
			$('#empresa').val(jsonData.empresaNombre)
			$('#serie').val(jsonData.serie)
			$('#folio').val(jsonData.folio)
			$('#rfcProveedor').val(jsonData.rfcProveedor)
			$('#fechaEmision').val(jsonData.fechaEmision)
			$('#timbre').val(jsonData.fechaTimbre)
			$('#moneda').val(jsonData.moneda)
			$('#total').val(jsonData.total)
			$('#tipoDocumento').val(jsonData.tipoDocumento)
			$('#estatusPago').val(jsonData.estatusPago)
			$('#estatusSAT').val(jsonData.estatusSAT)
			$('#respuestaValidacion').val(jsonData.respuestaValidacion)
			$('#errorValidacion').val(jsonData.errorValidacion)
			$('#comentario').val(jsonData.comentario)
			$('#fechaCarga').val(jsonData.fechaCarga)
			$('#remitente').val(jsonData.empresaCorreo)
			
			if (jsonData.serie != null && jsonData.folio != null) {
				$('#headerValue').text('Factura ' + jsonData.serie + jsonData.folio)
			} else if (jsonData.serie == null) {
				$(' #headerValue').text('Factura ' + jsonData.folio)
			} else if (jsonData.folio == null) {
				$('#headerValue').text('Factura ' + jsonData.serie)
			};
		
	}
	
	//Llenar detalles de Pago, si es que hay
	var habilitarPago = function(jsonData){
		$('#pagoFacturaTab').removeClass("disabled");
		
		
		//Tabla de avisos de pago
	var table3 = $('#avisosDePagoPorFactura').DataTable({
		"data": JSON.stringify(jsonData.pagos),
		"searching": false,
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		"columns": [

			{
				"className": 'pdfAviso-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function(data) {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark float-left" href="/proveedoresClient/preview/singlePDF/Pago/' + data.idPago + '" target="_blank"><i class="fa fa-file-pdf fa-lg" style="color:red"></i></a>'
				},
			},
			{ data: "idNumPago" },
			{ data: "fecMvto" },
			{ data: "totalPago" },
			{ data: "totalFactura" }
		]

	});
	}
	
	// Desactivar pago si no hay
	
	var deshabilitarPago = function(){
		$('#pagoFacturaTab').addClass("disabled");

	}
	
	//Llenar detalles de Comentario, si es que hay
	var habilitarComentario = function(jsonData){
		$('#comentarioFacturaTab').removeClass("disabled");
		

	}
	
	// Desactivar comentario si no hay
	
	var deshabilitarComentario = function(){
		$('#comentarioFacturaTab').addClass("disabled");
		
	}




	//PASTEÑAS DEL SIDE NAV BAR

	$("#pestañaInicio").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaInicio").addClass("active")
		document.getElementById("divFacturas").hidden = false;
		document.getElementById("divAvisos").hidden = true;
		document.getElementById("divPerfil").hidden = true;
		document.getElementById("divAyuda").hidden = true;
		$("#pestañaAvisos,  #pestañaAyuda, #pestañaPerfil").removeClass("active")
		table
		table
			.columns(10)
			.search('')
			.draw();
			
		table.ajax.reload(null, false);
	});

	$("#pestañaAvisos").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaAvisos").addClass("active")
		$("#pestañaInicio, #pestañaPerfil, #pestañaAyuda").removeClass("active")
		document.getElementById("divFacturas").hidden = true;
		document.getElementById("divAyuda").hidden = true;
		document.getElementById("divPerfil").hidden = true;
		table2.columns.adjust();
		document.getElementById("divAvisos").hidden = false;
		
		table2.ajax.reload(null, false);
		table2.columns.adjust();

	});

	$("#pestañaPerfil").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaPerfil").addClass("active")
		$("#pestañaInicio, #pestañaAvisos, #pestañaAyuda").removeClass("active")
		document.getElementById("divFacturas").hidden = true;
		document.getElementById("divAyuda").hidden = true;
		document.getElementById("divPerfil").hidden = false;
		document.getElementById("divAvisos").hidden = true;

	});

	$("#pestañaAyuda").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaAyuda").addClass("active")
		$("#pestañaInicio, #pestañaPerfil, #pestañaAvisos").removeClass("active")
		document.getElementById("divFacturas").hidden = true;
		document.getElementById("divAyuda").hidden = false;
		document.getElementById("divPerfil").hidden = true;
		document.getElementById("divAvisos").hidden = true;

	});
	
	
	//Upload a  facturas
	$('#submitNew').on('click',function(event) {

		event.preventDefault();
	
		var data = new FormData(document.getElementById('cargar-facturas'));

		var url = '/proveedoresClient/uploadFactura';

		var newCfdi = $.ajax({
			url: url,
			data: data,
			cache: false,
			contentType: false,
			processData: false,
			type: 'POST',
		});

		newCfdi.done(function(response) {
			var json = JSON.parse(response);
			
			if (json.status == true) {
				$('#alert-true-content').text(json.desc)
				$('#alert-true').prop('hidden', false);
			} else {
				$('#alert-false-content').text(json.desc)
				$('#alert-false').prop('hidden', false);
			} 

			setTimeout(function() {
				$('.alert').prop('hidden', true);

			}, 6000);


		});
		newCfdi.always(function() {

			
			table.ajax.reload(null, false);
		
			$('#nuevoModal').modal('hide');
			
			document.getElementById("InputGroupPDF").value = "";
			document.getElementById("inputGroupXML").value = "";

		});



	})


});