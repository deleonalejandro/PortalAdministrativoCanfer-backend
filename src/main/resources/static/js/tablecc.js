// this function creates the url with parameters to initialize the table
		function getInitUrl() {
			
			var getUrl = window.location;
			var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
			var myUrlWithParams;
			var start = moment().subtract(3, "month").startOf("month");
    		var end = moment().endOf("day");
			
		  	myUrlWithParams = new URL("/cajaChicaApi", baseUrl);
	
			myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
			myUrlWithParams.searchParams.append("proveedor", $("#selectedClave").text());
			myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
			myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
			
			alert(myUrlWithParams.href);
			
			return myUrlWithParams.href;

		}
		  
        //Tabla en si
		
		$(document).ready(function () {
				
		         var table = $('#formularios').DataTable({
					"paging":   false,
			        "ordering": false,
			        "info": false,
			        "searching": false,
					ajax: {
					dataSrc:""
		        	},
					scrollX:true,
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        },
			        "columns": [
					        {
			                "className":      'details-control', 
			                "orderable":      false,
			                "data":           null,
			                "defaultContent": '',
							"render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-chevrons btn-transparent-dark m-0"><i data-feather="chevrons-down"></i><script> feather.replace()</script></a>' ;
		                     },
			            },
							{
		                     "className": 'modal-control',
		                     "orderable": false,
							"bSortable": false,
		                     "data": null,
		                     "defaultContent": '',
		                     "render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="list"></i><script> feather.replace()</script></a>'
							 },
		                 },
						{
		                     "className": 'delete-control',
		                     "orderable": false,
							 "bSortable": false,
		                     "data": null,
		                     "defaultContent": '',
		                     "render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="trash"></i><script> feather.replace()</script></a>' ;
		                     },
		                 },
		
		                { data : "fecha" },
		                { data : "folio" },
						{ data: "estatus",
		                    "render": function(data) {
		                        if(data.toUpperCase() == 'EN PROCESO') {
		                            return '<span class="badge badge-orange">En Proceso</span>';
		                        }
		                        if(data.toUpperCase() == 'PAGADO') {
		                            return '<span class="badge badge-green">Pagado</span>';
		                        }
								if(data.toUpperCase() == 'CANCELADO') {
								    return '<span class="badge badge-red">Cancelado</span>';
								}
								else{
									return '<span class="badge badge-blue">N/A</span>';
								}
						     }
						},
						{ data : "total" }
		             ],
			           
					"order": [[ 3, "desc" ]]
		 });
		 
 			
			// Filters
			$('#reloadTableBtn').on('click', function() { 
				

				var getUrl = window.location;
				var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
				var myUrlWithParams;
				
				var start = moment().subtract(3, "month").startOf("month");
    			var end = moment().endOf("day");
    			
				myUrlWithParams = new URL("/proveedorApi", baseUrl);
				myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
				myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
				myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
				myUrlWithParams.searchParams.append("proveedor", $("#selectedClave").text());
				myUrlWithParams.searchParams.append("estatusPago", $("#inputFiltroEstatus").val());
				myUrlWithParams.searchParams.append("registeredAfter", $("#registeredAfter").text());
				myUrlWithParams.searchParams.append("registeredBefore", $("#registeredBefore").text());
				myUrlWithParams.searchParams.append("sequenceAfter", $("#inputFiltroFolioInicial").val());
				myUrlWithParams.searchParams.append("sequenceBefore", $("#inputFiltroFolioFinal").val());
				myUrlWithParams.searchParams.append("serie", $("#inputFiltroSerie").val());
				  
				table.ajax.url(myUrlWithParams.href).load();
				
				 
				alert(myUrlWithParams.href);
		
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
				
			// Filters
			$('#btn-add-form').on('click', function(event) {
				event.preventdefault(); 
				
				//Llena los parametros del formulario 
				var href = $(this).attr('href');
				
				$.get(href, function(formulario, status){
					$("#folioFormularioNew").val(formulario.folio);
					$("#estatusNewForm").val(formulario.estatus);
					$("#idCajaChicaNew").val(formulario.proveedor.claveProv);
					$("#sucursalNew").val(formulario.proveedor.nombre);
					$("#fechaNew").val(formulario.fecha);
					$("#idFormNew").val(formulario.idFormularioCajaChica);
					$("#comentarioNew").val(formulario.comentario);
					$("#totalNew").val(formulario.total);
				});
				
				
				//Muestra el modal 
				$('#newFormModal').modal('show');	
				
				
				//Crea la tabla de los detalles
				 var table2 = $('#detallesNuevoCajaChica').DataTable({
					"paging":   false,
			        "ordering": false,
			        "info": false,
			        "searching":false,
					ajax: {
					dataSrc:""
		        	},
					scrollX:true,
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        },
			           
					"order": [[ 0, "asc" ]]
					
				 });
				 
				 table2.columns.adjust();
				 
				 // Darle Cancelar a New Formulario
				$('#cancelarNewForm').on('click', function() {
				 
				 	$('#newFormModal').modal('hide');
				 
				 });
				 
			});
			
			
		});