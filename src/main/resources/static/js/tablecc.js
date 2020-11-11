// this function creates the url with parameters to initialize the table
		function getInitUrl() {
			
			var getUrl = window.location;
			var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
			var myUrlWithParams;
			var start = moment().subtract(3, "month").startOf("month");
    		var end = moment().endOf("day");
			
		  	myUrlWithParams = new URL("/proveedorApi", baseUrl);
	
			myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
			myUrlWithParams.searchParams.append("proveedor", $("#selectedClave").text());
			myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
			myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
			
			alert(myUrlWithParams.href);
			
			return myUrlWithParams.href;

		}
		  
        //Tabla en si
		
		$(document).ready(function () {
				
				//Initial Values
				$('#antiguedad').val(moment().subtract(3, "month").startOf("month"))
				
		         var table = $('#facturas').DataTable({
					ajax: {
		            url: getInitUrl(),
					dataSrc:""
		        	},
					scrollX:true,
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
		                     "render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i data-feather="list"></i><script> feather.replace()</script></a>'
							 },
		                 },
						
						{  "className": 'pago-control',
							data: "estatusPago",
						 	"orderable": false,
							"bSortable": false,
		                    "render": function(data) {
		                        if(data == 'PAGADO') {
		                            return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="active" data-feather="dollar-sign"></i><script> feather.replace()</script></a>';
		                        }else{
								    return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0 disabled"><i data-feather="dollar-sign"></i><script> feather.replace()</script></a>';
								}
						     }
						},
						{ "className": 'comment-control',
							data: "comentario",
						 	"orderable": false,
							"bSortable": false,
		                    "render": function(data) {
		                        if(data == "") {
		                            return '<a class="btn btn-datatable btn-icon btn-transparent-dark disabled  m-0"><i data-feather="message-square"></i><script> feather.replace()</script></a>';
		                        }else{
								    return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="active" data-feather="message-square"></i><script> feather.replace()</script></a>';
								}
						     }
						},
		                
		                { data : "serie" },
						{ data : "folio" },
						{ data : "total" },
						{ data : "pagoTotalPago"},
						{ data : "pagoFecmvto"},
						{ data : "pagoIdNumPago"},
						{ data : "fechaCarga" },
						{ data : "fechaEmision" },
						{ data : "tipoDocumento",
		                    "render": function(data) {
		                        if(data == 'I') {
		                            return '<button class="btn btn-pink btn-icon btn-xs" type="button">I</button>';
		                        }
		                        if(data == 'E') {
		                            return '<button class="btn btn-indigo btn-icon btn-xs" type="button">E</button>';
		                        }
								if(data == 'P') {
								    return '<button class="btn btn-teal btn-icon btn-xs" type="button">P</button>';
								}
						     }
						},
						{ data: "estatusPago",
		                    "render": function(data) {
		                        if(data == 'EN PROCESO') {
		                            return '<span class="badge badge-orange">Pendiente Pago</span>';
		                        }
		                        if(data == 'PAGADO') {
		                            return '<span class="badge badge-green">Pagado</span>';
		                        }
								if(data == 'CANCELADO') {
								    return '<span class="badge badge-red">Cancelado</span>';
								}
								if(data == 'RECHAZADO') {
								    return '<span class="badge badge-red">Rechazado</span>';
								}
						     }
						},
		             ],
					"order": [[ 10, "desc" ]]
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
			
			
				
				//PASTEÑAS DEL SIDE NAV BAR
			
			$("#pestañaFormulario").on( "click", function() {
				$("#pestañFormulario").addClass("active")
				document.getElementById("divFormulario").hidden = false;
				document.getElementById("divEntradas").hidden = true;
				$("#pestañaEntradas").removeClass("active") 
			});
			
			 $("#pestañaEntradas").on( "click", function() {
				$("#pestañaEntradas").addClass("active")
				document.getElementById("divEntradas").hidden = false;
				document.getElementById("divFormulario").hidden = true;
				$("#pestañaFormulario").removeClass("active") 
			});
			
				
		});