//Formato para rows tipo child	  
function format(d) {
	if (!d.hasComplemento) {
		// `d` is the original data object for the row
		return '<table class="" style="text-align: left">' +
			'<tr>' +
			'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singlePDF/ComprobanteFiscal/' + d.idComprobanteFiscal + '" target="_blank"><i class="fa fa-file-pdf fa-2x" style="color:red"></i></a>' +
			'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singleXML/ComprobanteFiscal/' + d.idComprobanteFiscal + '" target="_blank"><i class="fas fa-file-code fa-2x" style="color:green"></i></a>' +
			'<span class="ml-0 float-left fa-stack fa-2x"><i class="fas fa-file-invoice-dollar fa-stack-1x " style="color:teal"></i><i class="fas fa-slash fa-stack-1x" style="color:red"></i></span>' +
			'</tr>' +
			'</table>';
	} else {
		return '<table class="" style="text-align: left">' +
			'<tr>' +
			'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singlePDF/ComprobanteFiscal/' + d.idComprobanteFiscal + '" target="_blank"><i class="fa fa-file-pdf fa-2x" style="color:red"></i></a>' +
			'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singleXML/ComprobanteFiscal/' + d.idComprobanteFiscal + '" target="_blank"><i class="fas fa-file-code fa-2x" style="color:green"></i></a>' +
			'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singleXML/ComprobanteFiscal/' + d.idComplemento + '" target="_blank"><i class="fas fa-file-invoice-dollar fa-2x" style="color:teal"></i></a>' +
			'</tr>' +
			'</table>';

	}
};


// this function creates the url with parameters to initialize the table
function getInitUrl() {

	var getUrl = window.location;
	var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
	var myUrlWithParams;

	if (Cookies.get('fltr_on')) {

		$("#collapseExample").addClass('show');
		$("#inputFiltroProveedor").val(Cookies.get('fltr_proveedor'));
		$("#inputFiltroIdSap").val(Cookies.get('fltr_idNumSap'));
		$("#inputFiltroUUID").val(Cookies.get('fltr_uuid'));
		$("#inputFiltroEstatus").val(Cookies.get('fltr_estatusPago'));
		$("#inputFiltroFolioInicial").val(Cookies.get('fltr_sequenceAfter'));
		$("#inputFiltroFolioFinal").val(Cookies.get('fltr_sequenceBefore'));
		$("#inputFiltroImporteDesde").val(Cookies.get('fltr_totalAfter'));
		$("#inputFiltroImporteHasta").val(Cookies.get('fltr_totalBefore'));
		$("#uploadAfter").text(Cookies.get('fltr_uploadAfter'));
		$("#uploadBefore").text(Cookies.get('fltr_uploadBefore'));
		$("#reportrangeCarga span").html(Cookies.get('fltr_upload'));
		$("#registeredAfter").text(Cookies.get('fltr_registeredAfter'));
		$("#registeredBefore").text(Cookies.get('fltr_registeredBefore'));
		$("#reportrangeEmision span").html(Cookies.get('fltr_register'));




		//checkbox values (check them later)
		if (Cookies.get('fltr_checkSap') == 'true') {
			$("#checkRS").prop('checked', true);
		} else {
			$("#checkRS").prop('checked', false);
		}

		if (Cookies.get('fltr_hasComplemento') == 'true') {
			$("#checkComplemento").prop('checked', true);
		} else {
			$("#checkComplemento").prop('checked', false);
		}

		if (Cookies.get('fltr_generico') == "PROVEEDOR GENÉRICO") {
			$("#checkGenerico").prop('checked', true);
		} else {
			$("#checkGenerico").prop('checked', false);
		}

		// loading the URL
		if ($("#checkComplemento").is(":checked")) {

			myUrlWithParams = new URL("/documentosFiscalesApi/facturas", baseUrl);

		} else {

			myUrlWithParams = new URL("/documentosFiscalesApi", baseUrl);
		}

		// setting URL parameters
		myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
		myUrlWithParams.searchParams.append("proveedor", $("#inputFiltroProveedor").val());
		myUrlWithParams.searchParams.append("idNumSap", $("#inputFiltroIdSap").val());
		myUrlWithParams.searchParams.append("uuid", $("#inputFiltroUUID").val());
		myUrlWithParams.searchParams.append("estatusPago", $("#inputFiltroEstatus").val());
		myUrlWithParams.searchParams.append("uploadAfter", $("#uploadAfter").text());
		myUrlWithParams.searchParams.append("uploadBefore", $("#uploadBefore").text());
		myUrlWithParams.searchParams.append("registeredAfter", $("#registeredAfter").text());
		myUrlWithParams.searchParams.append("registeredBefore", $("#registeredBefore").text());
		myUrlWithParams.searchParams.append("sequenceAfter", $("#inputFiltroFolioInicial").val());
		myUrlWithParams.searchParams.append("sequenceBefore", $("#inputFiltroFolioFinal").val());
		myUrlWithParams.searchParams.append("totalAfter", $("#inputFiltroImporteDesde").val());
		myUrlWithParams.searchParams.append("totalBefore", $("#inputFiltroImporteHasta").val());

		if ($("#checkGenerico").is(":checked")) {
			myUrlWithParams.searchParams.append("generico", "PROVEEDOR GENÉRICO");
		}

		if ($("#checkRS").is(":checked")) {
			myUrlWithParams.searchParams.append("checkSap", true);
		}

		if ($("#checkComplemento").is(":checked")) {
			myUrlWithParams.searchParams.append("hasComplemento", true);
		}

	} else {

		var start = moment().startOf("day");
		var end = moment().endOf("day");

		myUrlWithParams = new URL("/documentosFiscalesApi", baseUrl);

		myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
		myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));
		myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));

		// setting the initial values on daterange picker
		$("#reportrangeCarga span").html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
		$("#uploadAfter").text(start.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));
		$("#uploadBefore").text(end.format('YYYY-MM-DD' + 'T' + 'HH:mm:ss'));
	}




	return myUrlWithParams.href;

}
//Tabla en si

$(document).ready(function() {

	//Crear unica variable de http request
	var xhttp = new XMLHttpRequest();
	var delhttp = new XMLHttpRequest();

	var table = $('#facturas').DataTable({
		"drawCallback": function(settings, json) {
			document.getElementById("reloadTableBtn").hidden = false;
			document.getElementById("reloadTableLoading").hidden = true;
		},
		stateSave: true,
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
				"className": 'select-control',
				"orderable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark"><i data-feather="check-circle"></i><script> feather.replace()</script></a>';
				},
			},
			{
				"className": 'details-control',
				"orderable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-chevrons btn-transparent-dark m-0"><i data-feather="chevrons-down"></i><script> feather.replace()</script></a>';
				},
			},
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
				"className": 'delete-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="trash"></i><script> feather.replace()</script></a>';
				},
			},

			{ data: "idNumSap" },
			{
				data: null,
				"render": function(row) {
					if (row.bitRS == false && row.bitRSusuario == false) {
						return '<i class="far fa-square" ></i>';
					}
					if (row.bitRS == true || row.bitRSusuario == true) {
						return '<i class="far fa-check-square" ></i>';
					}
				}
			},
			{ data: "folio" },
			{ data: "proveedorClaveProv" },
			{ data: "proveedorNombre" },
			{ data: "rfcProveedor" },
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
			{ data: "total" },
			{ data: "moneda" },
			{
				data: "estatusPago",
				"render": function(data) {
					if (data.toUpperCase() == 'EN PROCESO') {
						return '<span class="badge badge-orange">En Proceso</span>';
					}
					if (data.toUpperCase() == 'PAGO PENDIENTE' || data.toUpperCase() == 'PENDIENTE PAGO') {
						return '<span class="badge badge-orange">Pendiente Pago</span>';
					}
					if (data.toUpperCase() == 'PAGADO') {
						return '<span class="badge badge-green">Pagado</span>';
					}
					if (data.toUpperCase() == 'CANCELADO') {
						return '<span class="badge badge-red">Cancelado</span>';
					}
					if (data == 'RECHAZADO') {
						return '<span class="badge badge-red">Rechazado</span>';
					}
					else {
						return '<span class="badge badge-blue">' + data + '</span>';
					}
				}
			},
			{
				data: "fechaCarga",
				"render": function(data) {
					return data.split("T")[0]
				}
			},
			{
				data: "fechaEmision",
				"render": function(data) {
					return data.split("T")[0]
				}
			}
		],
		"order": [[14, 'desc']],
		"columnDefs": [
			{ "width": "1%", "targets": [0, 1, 2,3] },
			{ "width": "2%", "targets": [4,7,10] }
		]

	});

	//Tabla de Avisos
	var table2 = $('#avisosDePago').DataTable({
		stateSave: true,
		ajax: {
			url: "/documentosFiscalesApi/avisos/" + $("#selectedCompany").text(),
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
					return '<a class="btn btn-datatable btn-icon btn-transparent-dark float-left" href="/documentosFiscalesClient/preview/singlePDF/Pago/' + data.idPago + '" target="_blank"><i class="fa fa-file-pdf fa-lg" style="color:red"></i></a>'
				},
			},

			{ data: "idNumPago" },
			{ data: "totalPago" },
			{ data: "moneda" },
			{
				data: "nuevoEstatusFactura",
				"render": function(data) {
					if (data.toUpperCase() == 'EN PROCESO') {
						return '<span class="badge badge-orange">En Proceso</span>';
					}
					if (data.toUpperCase() == 'PAGO PENDIENTE' || data.toUpperCase() == 'PENDIENTE PAGO') {
						return '<span class="badge badge-orange">Pendiente Pago</span>';
					}
					if (data.toUpperCase() == 'PAGADO') {
						return '<span class="badge badge-green">Pagado</span>';
					}
					if (data.toUpperCase() == 'CANCELADO') {
						return '<span class="badge badge-red">Cancelado</span>';
					}
					if (data == 'RECHAZADO') {
						return '<span class="badge badge-red">Rechazado</span>';
					}
					else {
						return '<span class="badge badge-blue">' + data + '</span>';
					}
				}
			},
			{ data: "fecMvto" },
			{ data: "claveProveedor" },
			{ data: "rfcProveedor" }
		],
		"order": [[5, 'desc']]
	});

	//Tabla de Log
	var table3 = $('#logMov').DataTable({

		ajax: {
			url: "/catalogsAPI/log",
			dataSrc: ""
		},
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		"columns": [
			{
				data: "fecha",
				width: '10%',
				"render": function(data) {
					var string = data.split(' ')
					return string[0]
				}
			},
			{
				data: "empresa",
				width: '25%',
			},
			{
				data: "concepto",
				width: '5%',
				"render": function(data) {
					if (data == "ERROR") {

						return '<i style="color:red" data-feather="x-circle"></i><script> feather.replace()</script>';

					} if (data == "UPDATE") {

						return '<i style="color:purple" data-feather="edit"></i><script> feather.replace()</script>';

					} else if (data == "PAYMENT") {

						return '<i style="color:green" data-feather="dollar-sign"></i><script> feather.replace()</script>';

					} else if (data == "NEW_DOC") {

						return '<i style="color:green" data-feather="file-plus"></i><script> feather.replace()</script>';

					} else if (data == "ERROR_CONNECTION") {

						return '<i style="color:orange" data-feather="zap-off"></i><script> feather.replace()</script>';

					} else if (data == "NEW_USER") {

						return '<i style="color:green" data-feather="user-plus"></i><script> feather.replace()</script>';

					} else if (data == "ERROR_STORAGE") {

						return '<i style="color:red" data-feather="folder-minus"></i><script> feather.replace()</script>';

					} else if (data == "ERROR_DB") {

						return '<i style="color:red" data-feather="database"></i><script> feather.replace()</script>';

					} else if (data == "ERROR_FILE") {

						return '<i style="color:red" data-feather="file-minus"></i><script> feather.replace()</script>';

					} else if (data == "DELETE") {

						return '<i style="color:black" data-feather="trash-2"></i><script> feather.replace()</script>';

					} else if (data == "DELETE_USER") {

						return '<i style="color:red" data-feather="user-minus"></i><script> feather.replace()</script>';

					} else if (data == "ERROR_UPDATE") {

						return '<i style="color:red" data-feather="edit"></i><script> feather.replace()</script>';

					} else {

						return '<i style="color:blue" data-feather="activity"></i><script> feather.replace()</script>';

					}

				}
			},

			{
				data: "mensaje",
				width: '50%',
			},
			{
				data: "fecha",
				width: '10%',
				type: 'time-uni',
				"render": function(data) {
					var string = data.split(' ')
					string = string[1].split('.')
					return string[0]
				}
			}
		],
		"order": [[0, "desc"], [4, "desc"]]
	});

	// Tabla de catalogo de proveedores

	var tableProveedor = $('#proveedorTable').DataTable({
		stateSave: true,
		bFilter: true,
		orderCellsTop: true,
		ajax: {
			url: "/documentosFiscalesApi/catalogo?selectedCompany=" + $("#selectedCompany").text(),
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
				"render": function() {
					return '<a class="deleteBtn btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="trash-2"></i></a><script>feather.replace()</script>';

				},
			},
			{
				"className": 'edit-control',
				"orderable": false,
				"bSortable": false,
				"data": null,
				"defaultContent": '',
				"render": function() {
					return '<a class="editBtn btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="edit"></i></a><script>feather.replace()</script>';

				},
			},
			{ data: "nombreEmpresa" },
			{ data: "nombre" },
			{ data: "claveProv" },
			{ data: "rfc" },
			{ data: "moneda" },
			{
				data: "bitActivo",
				"render": function(data) {
					if (data == 'true' || data == true) {
						return '<i class="far fa-check-square" ></i>';
					}
					else{
						return '<i class="far fa-square" ></i>';
					}
				}
			},
			{ data: "contacto" },
			{ data: "correo" },
			{ data: "telefono" },
			{ data: "paginaWeb" },
			{ data: "localidad" },
		],
	});
	
	// Filtros tabla proveedores

	// Setup - add a text input to each footer cell
	$('#proveedorTable thead tr').clone(true).appendTo('#proveedorTable thead');
	$('#proveedorTable thead tr:eq(1) th').each(function(i) {
		var title = $(this).text();
		if (title != '' && title != 'Activo') {
	
			$(this).html('<input type="text" onclick="stopPropagation(event);" placeholder="Buscar ' + title + '" />');
	
			$('input', this).on('keyup change', function() {
				if (tableProveedor.column(i).search() !== this.value) {
					tableProveedor
						.column(i)
						.search( this.value )
						.draw();
				}
			});
		} else {
			$(this).html('');
	
		}
	});

	// Funcion para editar proveedores

	$('#proveedorTable tbody').on('click', 'td.edit-control', function() {

		var jsonData = tableProveedor.row(this).data();

		$("#idProveedor").val(jsonData.idProveedor);
		$("#inputEmpresa").val(jsonData.nombreEmpresa);
		$("#inputRfc").val(jsonData.rfc);
		$("#inputClaveProv").val(jsonData.claveProv);
		$("#inputNombre").val(jsonData.nombre);
		$("#inputCorreo").val(jsonData.correo);
		$("#inputCalle").val(jsonData.calle);
		$("#inputExterior").val(jsonData.numExt);
		$("#inputInterior").val(jsonData.numInt);
		$("#inputLocalidad").val(jsonData.localidad);
		$("#inputReferencia").val(jsonData.referencia);
		$("#inputMoneda").val(jsonData.moneda);
		$("#inputPaginaWeb").val(jsonData.paginaWeb);
		$("#checkActivo").prop('checked', jsonData.bitActivo);
		$("#dropdownEmpresas").val(jsonData.empresasId);
		$("#inputTelefono").val(jsonData.telefono);
		$("#inputContacto").val(jsonData.contacto);



		$('#editModal').modal('show');

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


	$('#pdf1').on('change', function() {
		if (this.files[0].size > 563200) {
			$('#pdf1').val('');
			$('#fileSizeDetailPdf').prop('hidden', false)

			setTimeout(function() {
				$('#fileSizeDetailPdf').prop('hidden', true);

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

	// Funcion para delete en proveedores

	$('#proveedorTable tbody').on('click', 'td.delete-control', 'tr', function(event) {

		event.preventDefault();

		var jsonData = tableProveedor.row(this).data();

		$('.deleteForm .delBtn').attr("href", "supplier/delete/" + jsonData.idProveedor)
		$('#deleteModal').modal("show");
	});



	// Filters action and refresh cookies
	$('#reloadTableBtn').on('click', function() {

		document.getElementById("reloadTableBtn").hidden = true;
		document.getElementById("reloadTableLoading").hidden = false;

		var getUrl = window.location;
		var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
		var myUrlWithParams;

		if ($("#checkComplemento").is(":checked")) {

			myUrlWithParams = new URL("/documentosFiscalesApi/facturas", baseUrl);

		} else {

			myUrlWithParams = new URL("/documentosFiscalesApi", baseUrl);
		}

		// setting URL parameters
		myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
		myUrlWithParams.searchParams.append("proveedor", $("#inputFiltroProveedor").val());
		myUrlWithParams.searchParams.append("idNumSap", $("#inputFiltroIdSap").val());
		myUrlWithParams.searchParams.append("uuid", $("#inputFiltroUUID").val());
		myUrlWithParams.searchParams.append("estatusPago", $("#inputFiltroEstatus").val());
		myUrlWithParams.searchParams.append("uploadAfter", $("#uploadAfter").text());
		myUrlWithParams.searchParams.append("uploadBefore", $("#uploadBefore").text());
		myUrlWithParams.searchParams.append("registeredAfter", $("#registeredAfter").text());
		myUrlWithParams.searchParams.append("registeredBefore", $("#registeredBefore").text());
		myUrlWithParams.searchParams.append("sequenceAfter", $("#inputFiltroFolioInicial").val());
		myUrlWithParams.searchParams.append("sequenceBefore", $("#inputFiltroFolioFinal").val());
		myUrlWithParams.searchParams.append("totalAfter", $("#inputFiltroImporteDesde").val());
		myUrlWithParams.searchParams.append("totalBefore", $("#inputFiltroImporteHasta").val());

		// saving values into cookies
		Cookies.set('fltr_proveedor', $("#inputFiltroProveedor").val());
		Cookies.set('fltr_idNumSap', $("#inputFiltroIdSap").val());
		Cookies.set('fltr_uuid', $("#inputFiltroUUID").val())
		Cookies.set('fltr_estatusPago', $("#inputFiltroEstatus").val());
		Cookies.set('fltr_uploadAfter', $("#uploadAfter").text());
		Cookies.set('fltr_uploadBefore', $("#uploadBefore").text());
		Cookies.set('fltr_upload', $('#reportrangeCarga span').html())
		Cookies.set('fltr_registeredAfter', $("#registeredAfter").text());
		Cookies.set('fltr_registeredBefore', $("#registeredBefore").text());
		Cookies.set('fltr_register', $('#reportrangeEmision span').html());
		Cookies.set('fltr_sequenceAfter', $("#inputFiltroFolioInicial").val());
		Cookies.set('fltr_sequenceBefore', $("#inputFiltroFolioFinal").val());
		Cookies.set('fltr_totalAfter', $("#inputFiltroImporteDesde").val());
		Cookies.set('fltr_totalBefore', $("#inputFiltroImporteHasta").val());
		Cookies.set('fltr_generico', "");
		Cookies.set('fltr_checkSap', false);
		Cookies.set('fltr_hasComplemento', false);



		if ($("#checkGenerico").is(":checked")) {
			myUrlWithParams.searchParams.append("generico", "PROVEEDOR GENÉRICO");
			Cookies.set('fltr_generico', "PROVEEDOR GENÉRICO");
		}
		if ($("#checkRS").is(":checked")) {
			myUrlWithParams.searchParams.append("checkSap", true);
			Cookies.set('fltr_checkSap', true);
		}
		if ($("#checkComplemento").is(":checked")) {
			myUrlWithParams.searchParams.append("hasComplemento", true);
			Cookies.set('fltr_hasComplemento', true);
			table.ajax.url(myUrlWithParams.href).load();
		} else {
			table.ajax.url(myUrlWithParams.href).load();
		}

		Cookies.set('fltr_on', true);

	});

	// Clear cookies before when changing company
	$("#dashboardButton").on("click", function() {

		Cookies.remove('fltr_on');

		Cookies.remove('fltr_proveedor');
		Cookies.remove('fltr_idNumSap');
		Cookies.remove('fltr_uuid');
		Cookies.remove('fltr_estatusPago');
		Cookies.remove('fltr_uploadAfter');
		Cookies.remove('fltr_uploadBefore');
		Cookies.remove('fltr_upload');
		Cookies.remove('fltr_registeredAfter');
		Cookies.remove('fltr_registeredBefore');
		Cookies.remove('fltr_register');
		Cookies.remove('fltr_sequenceAfter');
		Cookies.remove('fltr_sequenceBefore');
		Cookies.remove('fltr_totalAfter');
		Cookies.remove('fltr_totalBefore');
		Cookies.remove('fltr_generico');
		Cookies.remove('fltr_checkSap');
		Cookies.remove('fltr_hasComplemento');


	});

	// Clear filters
	$('#clearFilters').on('click', function() {

		$("#inputFiltroProveedor").val('');
		$("#inputFiltroIdSap").val('');
		$("#inputFiltroUUID").val('');
		$("#inputFiltroEstatus").val("");
		$("#registeredAfter").text("");
		$("#registeredBefore").text("");
		$("#inputFiltroFolioInicial").val('');
		$("#inputFiltroFolioFinal").val('');
		$("#inputFiltroImporteDesde").val('');
		$("#inputFiltroImporteHasta").val('');
		$("#checkGenerico").prop("checked", false);
		$("#checkRS").prop("checked", false);
		$("#checkComplemento").prop("checked", false);
		$("#reportrangeEmision span").text("");


	});


	// Funcion para monitorear si esta cerrado o abierto

	$('#facturas tbody').on('click', 'td.details-control', function() {
		var tr = $(this).closest('tr');
		var row = table.row(tr);
		if (row.child.isShown()) {
			// Esta abierto, se cierra
			row.child.hide();
			tr.removeClass('shown')
		}
		else {
			//Esta cerrado, se abre
			row.child(format(row.data())).show();
			tr.addClass('shown')
		}

	});


	// Funcion para modal COMPROBANTE FISCAL

	$('#facturas tbody').on('click', 'td.modal-control', function() {
		var tr = $(this).closest('tr');
		var data = table.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		if (jsonData.serie != null && jsonData.folio != null) {
			$('#headerValue').text('Factura ' + jsonData.serie + jsonData.folio)
		} else if (jsonData.serie == null) {
			$(' #headerValue').text('Factura ' + jsonData.folio)
		} else if (jsonData.folio == null) {
			$('#headerValue').text('Factura ' + jsonData.serie)
		};

		$('.detailsForm #uuid').val(jsonData.uuid)
		$('.detailsForm #idNumSap').val(jsonData.idNumSap)
		$('.detailsForm #empresa').val(jsonData.empresaNombre)
		$('.detailsForm #proveedor').val(jsonData.proveedorNombre)
		$('.detailsForm #serie').val(jsonData.serie)
		$('.detailsForm #folio').val(jsonData.folio)

		$('.detailsForm #rfcEmpresa').val(jsonData.rfcEmpresa)
		$('.detailsForm #rfcProveedor').val(jsonData.rfcProveedor)
		$('.detailsForm #fechaEmision').val(jsonData.fechaEmision)
		$('.detailsForm #fechaTimbre').val(jsonData.fechaTimbre)
		$('.detailsForm #noCertificadoEmpresa').val(jsonData.noCertificadoEmpresa)
		$('.detailsForm #noCertificadoSAT').val(jsonData.noCertificadoSat)
		$('.detailsForm #versionCFD').val(jsonData.versionCfd)
		$('.detailsForm #versionTimbre').val(jsonData.versionTimbre)
		$('.detailsForm #moneda').val(jsonData.moneda)
		$('.detailsForm #total').val(jsonData.total)
		$('.detailsForm #tipoDocumento').val(jsonData.tipoDocumento)
		$('.detailsForm #estatusPago').val(jsonData.estatusPago)
		$('.detailsForm #newSuppliers').val(jsonData.proveedorClaveProv)
		$('.detailsForm #estatusSAT').val(jsonData.estatusSAT + ' al ' + jsonData.fechaValidacionSat)
		$('.detailsForm #respuestaValidacion').val(jsonData.respuestaValidacion)
		$('.detailsForm #comentario').val(jsonData.comentario)
		$('.detailsForm #idComprobanteFiscal').val(jsonData.idComprobanteFiscal)
		$('.detailsForm #validacionFiscal').val(jsonData.validacionFiscal)

		$('.detailsForm #bitRSusuario').prop("checked", jsonData.bitRSusuario);
		$('.detailsForm #bitRS').prop("checked", jsonData.bitRS);
		$('.detailsForm #bitValidoSAT').prop("checked", jsonData.bitValidoSAT);
		$('.detailsForm #bitRSusuarioText').val(jsonData.bitRSusuario);
		$('.detailsForm').attr('action', "documentosFiscalesClient/update?rfc=" + $("#selectedCompany").text());
		$('.detailsForm #docsRelacionados').val(jsonData.uuidRelacionados)
		$('.detailsForm .pdfBtn').attr('href', '/documentosFiscalesClient/download/singlePDF/ComprobanteFiscal/' + jsonData.idComprobanteFiscal)
		$('.detailsForm .xmlBtn').attr('href', '/documentosFiscalesClient/download/singleXML/ComprobanteFiscal/' + jsonData.idComprobanteFiscal)
		if (jsonData.hasComplemento) {
			$('.detailsForm .pagoBtn').attr("href", "/documentosFiscalesClient/download/singleXML/ComprobanteFiscal/" + jsonData.idComplemento)
			document.getElementById("sinpago").hidden = true
		} else {
			document.getElementById("conpago").hidden = true
		}

		document.getElementById("newSuppliers").hidden = true;

		$.get("/catalogsAPI/getProveedores/" + jsonData.rfcProveedor + "/" + jsonData.rfcEmpresa, function(data) {
			$('#newSuppliers').empty()
			data.forEach(function(value) {
				$('#newSuppliers')
					.append($("<option></option>")
						.attr("value", value.idProveedor)
						.text(value.claveProv));
			});
			if (jsonData.proveedorNombre == 'PROVEEDOR GENÉRICO') {
				$('#newSuppliers').append($("<option selected/>").val(jsonData.proveedorIdProveedor).text('Proveedor Genérico - ' + jsonData.proveedorClaveProv));
			}

			$('#newSuppliers').val(jsonData.proveedorIdProveedor);
		});


		$(' .detailsForm #detailsModal').modal('show');

		$('.detailsForm .refreshBtn').on('click', function(event) {
			event.preventDefault();

			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {

					let today = new Date().toISOString().slice(0, 10)

					$('.detailsForm #estatusSAT').val(this.responseText + ' al ' + today);
					$('#toastSAT').toggleClass('ew-toast-show')

					$('#toastSAT').toast('show')

					setTimeout(function() {
						$('#toastSAT').toggleClass('ew-toast-show')
					}, 6000);
				}

			};
			xhttp.open("POST", '/documentosFiscalesApi/getVigencia/' + jsonData.idComprobanteFiscal, true);
			xhttp.send();

		});

		$('.detailsForm .refreshProv').on('click', function(event) {

			document.getElementById("newSuppliers").hidden = false;
		});

		$('.detailsForm #bitRSusuario').on('click', function() {
			if (document.getElementById("bitRSusuario").checked == true) {
				$('.detailsForm #bitRSusuarioText').val('true')
			} else {
				$('.detailsForm #bitRSusuarioText').val('false')
			}

		});

	});

	// Funcion para delete

	$('#facturas tbody').on('click', 'td.delete-control', 'tr', function(event) {

		event.preventDefault();

		var tr = $(this).closest('tr');
		var data = table.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		$('.deleteForm .delBtn').attr("href", "/documentosFiscalesClient/delete/" + jsonData.idComprobanteFiscal + "?rfc=" + $("#selectedCompany").text())
		$('#deleteModal').modal('show');
	});


	//Funcion para seleccionar

	$('#facturas tbody').on('click', 'td.select-control', function() {
		var tr = $(this).closest('tr');
		if ($(tr).hasClass('selected')) {
			tr.removeClass('selected')
		}
		else {
			tr.addClass('selected')

		}

	});


	// Handle click on "Expand All" button
	$('#btn-show-all-children').on('click', function() {
		// Enumerate all rows
		table.rows().every(function() {
			if (!this.child.isShown()) {
				// Open this row
				this.child(format(this.data())).show();
				$(this.node()).addClass('shown');
			}
		});
	});

	// Handle click on "Collapse All" button
	$('#btn-hide-all-children').on('click', function() {
		// Enumerate all rows
		table.rows().every(function() {
			if (this.child.isShown()) {
				// Collapse row details
				this.child.hide();
				$(this.node()).removeClass('shown');
			}
		});
	});


	// Boton de borrar varios
	$('#btn-del-mul').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('.deleteForm .delBtn').attr("href", "/documentosFiscalesClient/deleteMultipleFacturas?ids=" + ids + "&rfc=" + $("#selectedCompany").text())
			$('#deleteModal').modal('show');
		}
	});

	// Boton de descargar varios
	$('#xml').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('#xml').attr("href", "/documentosFiscalesClient/download/zipXML/ComprobanteFiscal/?ids=" + ids)
		}
	});

	$('#pdf').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('#pdf').attr("href", "/documentosFiscalesClient/download/zipPDF/ComprobanteFiscal?ids=" + ids)
		}
	});

	$('#csv').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('#csv').attr("href", "/documentosFiscalesClient/csv?ids=" + ids)
		}
	});

	$('#xls').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('#xls').attr("href", "/documentosFiscalesClient/excel?ids=" + ids)
		}
	});

	$('#xml').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('#xls').attr("href", "/documentosFiscalesClient/download/zipXML/ComprobanteFiscal?ids=" + ids)
		}
	});



	$('#comprimir').on('click', function() {
		if (table.rows('.selected').any()) {
			// Enumerate all rows
			var ids = [];
			table.rows().every(function() {
				if ($(this.node()).hasClass('selected')) {
					var data = this.data()
					var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
					ids.push(jsonData);
				}
			});
			$('#comprimir').attr("href", "/documentosFiscalesClient/download/zip/ComprobanteFiscal?ids=" + ids)
		}
	});

	$("#selectAll").on("click", function(e) {

		if ($(this).is(":checked")) {
			table.rows({ search: 'applied' }).select();
		} else {
			table.rows().deselect();
		}
	});


	//PASTEÑAS DEL SIDE NAV BAR

	$("#pestañaFacturas").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaFacturas").addClass("active")
		document.getElementById("divFacturas").hidden = false;
		document.getElementById("divAvisos").hidden = true;
		document.getElementById("divLog").hidden = true;
		$("#pestañaInicio, #pestañaAvisos, #pestañaLog, #pestañaNotas, #pestañaCompl").removeClass("active")
		table
		table
			.columns(10)
			.search('^I', true, false, true)
			.draw();

		table.ajax.reload(null, false);

	});

	$("#pestañaInicio").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaInicio").addClass("active")
		document.getElementById("divFacturas").hidden = false;
		document.getElementById("divAvisos").hidden = true;
		document.getElementById("divLog").hidden = true;
		$("#pestañaFacturas, #pestañaAvisos, #pestañaLog, #pestañaNotas, #pestañaCompl").removeClass("active")
		table
		table
			.columns(10)
			.search('')
			.draw();

		table.ajax.reload(null, false);
	});

	$("#pestañaCompl").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaCompl").addClass("active")
		document.getElementById("divFacturas").hidden = false;
		document.getElementById("divAvisos").hidden = true;
		document.getElementById("divLog").hidden = true;
		$("#pestañaInicio, #pestañaAvisos, #pestañaLog, #pestañaNotas, #pestañaFacturas").removeClass("active")
		table
		table
			.columns(10)
			.search('P')
			.draw();

		table.ajax.reload(null, false);
	});

	$("#pestañaNotas").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaNotas").addClass("active")
		document.getElementById("divFacturas").hidden = false;
		document.getElementById("divAvisos").hidden = true;
		document.getElementById("divLog").hidden = true;
		$("#pestañaFacturas, #pestañaAvisos, #pestañaLog, #pestañaInicio, #pestañaCompl").removeClass("active")
		table
		table
			.columns(10).search('^E', true, false, true)
			.draw();

		table.ajax.reload(null, false);
	});

	$("#pestañaAvisos").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaAvisos").addClass("active")
		$("#pestañaFacturas, #pestañaNotas, #pestañaLog, #pestañaInicio, #pestañaCompl").removeClass("active")
		document.getElementById("divFacturas").hidden = true;
		document.getElementById("divLog").hidden = true;
		table2.columns.adjust();
		document.getElementById("divAvisos").hidden = false;
		table2.columns.adjust();

		table2.ajax.reload(null, false);

	});

	$("#pestañaLog").on("click", function() {
		table.rows().every(function() { ($(this.node()).removeClass('selected')) });
		$("#pestañaLog").addClass("active")
		$("#pestañaFacturas, #pestañaAvisos, #pestañaNotas, #pestañaInicio, #pestañaCompl").removeClass("active")
		document.getElementById("divFacturas").hidden = true;
		document.getElementById("divAvisos").hidden = true;
		table3.draw();
		document.getElementById("divLog").hidden = false;
		table3.columns.adjust();

		table3.ajax.reload();

	});

	//ALERTS

	if ($("#upload").text() == 'true') {
		$('#alert-upload-true').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);
	}


	if ($("#upload").text() == 'false') {
		$('#alert-upload-false').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);

	}

	if ($("#deletePermission").text() == 'false') {
		$('#alert-delete-false').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);
	}

	if ($("#editSuccess").text() == 'true') {
		$('#alert-edit-true').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);
	}

	if ($("#editSuccess").text() == 'false') {
		$('#alert-edit-false').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);
	}

	if ($("#editpdfSuccess").text() == 'true') {
		$('#alert-pdfedit-true').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);
	}

	if ($("#editpdfSuccess").text() == 'false') {
		$('#alert-pdfedit-false').prop('hidden', false);
		setTimeout(function() {
			$('.alert').prop('hidden', true);
		}, 6000);
	}



});

