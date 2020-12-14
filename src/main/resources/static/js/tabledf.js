//Formato para rows tipo child	  
  function format(d){
	        if (!d.hasComplemento) {
	         // `d` is the original data object for the row
	         return '<table class="" style="text-align: left">' +
	             '<tr>' +
	            	'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singlePDF/ComprobanteFiscal/'+d.idComprobanteFiscal+'" target="_blank"><i class="fa fa-file-pdf fa-2x" style="color:red"></i></a>' +
					'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singleXML/ComprobanteFiscal/'+d.idComprobanteFiscal+'" target="_blank"><i class="fas fa-file-code fa-2x" style="color:green"></i></a>'+
					'<span class="ml-0 float-left fa-stack fa-2x"><i class="fas fa-file-invoice-dollar fa-stack-1x " style="color:teal"></i><i class="fas fa-slash fa-stack-1x" style="color:red"></i></span>'+
	             '</tr>' +
	         '</table>';  
	         } else{
	         return '<table class="" style="text-align: left">' +
	             '<tr>' +
	            	'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singlePDF/ComprobanteFiscal/'+d.idComprobanteFiscal+'" target="_blank"><i class="fa fa-file-pdf fa-2x" style="color:red"></i></a>' +
					'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singleXML/ComprobanteFiscal/'+d.idComprobanteFiscal+'" target="_blank"><i class="fas fa-file-code fa-2x" style="color:green"></i></a>'+
					'<a class="btn btn-datatable btn-icon btn-transparent-dark float-left btn-xl" href="/documentosFiscalesClient/preview/singleComplemento/ComprobanteFiscal/'+d.idComplemento+'" target="_blank"><i class="fas fa-file-invoice-dollar fa-2x" style="color:teal"></i></a>'+	
	             '</tr>' +
	         '</table>';
	         
	         }
	    };
	    
		// this function creates the url with parameters to initialize the table
		function getInitUrl() {
			
			var getUrl = window.location;
			var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
			var myUrlWithParams;
			var start = moment().startOf("day");
    		var end = moment().endOf("day");
			
		  	myUrlWithParams = new URL("/documentosFiscalesApi", baseUrl);
	
			myUrlWithParams.searchParams.append("empresa", $("#selectedCompany").text());
			myUrlWithParams.searchParams.append("uploadAfter", start.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
			myUrlWithParams.searchParams.append("uploadBefore", end.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
			
			alert(myUrlWithParams.href);
			
			return myUrlWithParams.href;

		}
        //Tabla en si
		
		$(document).ready(function () {
			
			//Checar si necesita el Toast de Upload
			if($("#upload").text() == true) {
		            $('#toastSAT').toast('show')
		     }
		
			//Crear unica variable de http request
			var xhttp = new XMLHttpRequest();
			
		         var table = $('#facturas').DataTable({
					stateSave: true,
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
			                "className":  'select-control', 
			                "orderable":  false,
			                "data": null,
			                "defaultContent": '',
							"render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark"><i data-feather="check-circle"></i><script> feather.replace()</script></a>' ;
		                     },
			            },
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
		
		                { data : "idNumSap" },
		                { data : "bitRS" ,
		                    "render": function(data) {
		                        if(data == false) {
		                            return '<i class="far fa-square" ></i>';
		                        }
		                        if(data == true) {
		                            return '<i class="far fa-check-square" ></i>';
		                        }
							}
						 },
		                { data : "folio" },
						{ data : "proveedorClaveProv"},
						{ data : "proveedorNombre" },
						{ data : "rfcProveedor" },
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
						{ data : "total" },
						{ data : "moneda" },
						{ data: "estatusPago",
		                    "render": function(data) {
		                        if(data == 'EN PROCESO') {
		                            return '<span class="badge badge-orange">En Proceso</span>';
		                        }
		                        if(data == 'PAGADO') {
		                            return '<span class="badge badge-green">Pagado</span>';
		                        }
								if(data == 'CANCELADO') {
								    return '<span class="badge badge-red">Cancelado</span>';
								}
						     }
						},
						{ data : "fechaCarga" },
						{ data : "fechaEmision" }
		             ],
		             "order": [[14, 'desc']],
					
		 });
		 
		 //Tabla de Avisos
		  var table2 = $('#avisosDePago').DataTable({
					stateSave: true,
					ajax: {
		            url: "/documentosFiscalesApi/avisos/"+$("#selectedCompany").text(),
					dataSrc:""
		        	},
					scrollX:true,
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
		                     "render": function (data) {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark float-left" href="/documentosFiscalesClient/preview/singlePDF/Pago/'+data.idPago+'" target="_blank"><i class="fa fa-file-pdf fa-lg" style="color:red"></i></a>' 
							 },
		                 },

						{ data : "idNumPago" },
		                { data : "totalPago" },
		                { data : "moneda" },
						{ data: "nuevoEstatusFactura",
		                    "render": function(data) {
		                        if(data == 'EN PROCESO') {
		                            return '<span class="badge badge-orange">En Proceso</span>';
		                        }
		                        if(data == 'PAGADO') {
		                            return '<span class="badge badge-green">Pagado</span>';
		                        }
								if(data == 'CANCELADO') {
								    return '<span class="badge badge-red">Cancelado</span>';
								}
						     }
						},
						{ data : "fecMvto" },
						{ data : "claveProveedor" },
						{ data : "rfcProveedor" }
		             ],
					 "order": [[5, 'desc']],
		 });
		 
		  //Tabla de Log
		  var table3 = $('#logMov').DataTable({
					
					ajax: {
		            url: "/catalogsAPI/log",
					dataSrc:""
		        	},
					scrollX:true,
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        },
			        "columns": [
			          { data: "fecha",
						width: '10%',
		                    "render": function(data) {
		                       var string = data.split(' ')
		                       return string[0]
						     }
						},
						{ data : "empresa" ,
							width: '25%',},
		                { data : "concepto",
							width: '5%',
		                "render": function(data) {
		                      if(data=="ERROR"){
		                      
		                       return '<i style="color:red" data-feather="x-circle"></i><script> feather.replace()</script>';
		                      
		                      } if (data == "UPDATE"){
		                      
		                       return '<i style="color:purple" data-feather="edit"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "PAYMENT"){
		                      
		                       return '<i style="color:green" data-feather="dollar-sign"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "NEW_DOC"){
		                      
		                       return '<i style="color:green" data-feather="file-plus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_CONNECTION"){
		                      
		                       return '<i style="color:orange" data-feather="zap-off"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "NEW_USER"){
		                      
		                       return '<i style="color:purple" data-feather="user-plus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_STORAGE"){
		                      
		                       return '<i style="color:red" data-feather="folder-minus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_DB"){
		                      
		                       return '<i style="color:red" data-feather="database"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_FILE"){
		                      
		                       return '<i style="color:red" data-feather="file-minus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "DELETE"){
		                      
		                       return '<i style="color:black" data-feather="trash-2"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_UPDATE"){
		                      
		                       return '<i style="color:red" data-feather="edit"></i><script> feather.replace()</script>';
		                      
		                      }else {
		                      
		                       return '<i style="color:blue" data-feather="activity"></i><script> feather.replace()</script>';
		                      
		                      }
		                      
						     } 
					 },
		                
		                { data : "mensaje",
							width: '50%',},
		                { data: "fecha",
							width: '10%',
							type: 'time-uni',
		                    "render": function(data) {
		                       var string = data.split(' ')
								string = string[1].split('.')
		                       return string[0] 
						     }
						}
		             ],
					 "order": [[ 0, "desc" ], [ 4, "desc" ]]
		 });
 			
			// Filters
			$('#reloadTableBtn').on('click', function() { 
				

				var getUrl = window.location;
				var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
				var myUrlWithParams;
				
				if($("#checkComplemento").is(":checked")){
					
					myUrlWithParams = new URL("/documentosFiscalesApi/facturas", baseUrl);
					
				} else {
					
					myUrlWithParams = new URL("/documentosFiscalesApi", baseUrl);
				}

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
				
				if($("#checkGenerico").is(":checked")){
					myUrlWithParams.searchParams.append("generico", "PROVEEDOR GENÉRICO"); 
				}
				if($("#checkRS").is(":checked")){
					myUrlWithParams.searchParams.append("checkSap", true); 
				}
				if($("#checkComplemento").is(":checked")){
					myUrlWithParams.searchParams.append("hasComplemento", true);  
					table.ajax.url(myUrlWithParams.href).load();
				}else {
					table.ajax.url(myUrlWithParams.href).load();
				}
				
				 
				alert(myUrlWithParams.href);
		
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
					
		         $('#facturas tbody').on('click', 'td.details-control', function () {
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
					
		         $('#facturas tbody').on('click', 'td.modal-control', function () {
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					if(jsonData.serie != null && jsonData.folio != null){
						$('#headerValue').text('Factura ' + jsonData.serie + jsonData.folio)
					} else if(jsonData.serie == null){
						$(' #headerValue').text('Factura ' + jsonData.folio)
					} else if(jsonData.folio == null){
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
					$('.detailsForm #estatusSAT').val(jsonData.estatusSAT)
					$('.detailsForm #respuestaValidacion').val(jsonData.respuestaValidacion)
					$('.detailsForm #errorValidacion').val(jsonData.errorValidacion)
					$('.detailsForm #comentario').val(jsonData.comentario)
					$('.detailsForm #idComprobanteFiscal').val(jsonData.idComprobanteFiscal)
					         
			        $('.detailsForm #bitRSusuario').prop("checked", jsonData.bitRSusuario);
			        $('.detailsForm #bitRS').prop("checked", jsonData.bitRS);
					$('.detailsForm #bitValidoSAT').prop("checked", jsonData.bitValidoSAT);
					$('.detailsForm #bitRSusuarioText').val(jsonData.bitRSusuario);
					$('.detailsForm').attr('action',"documentosFiscalesClient/update?rfc="+ $("#selectedCompany").text());
					$('.detailsForm #docsRelacionados').val(jsonData.uuidRelacionados)
					$('.detailsForm .pdfBtn').attr('href','/documentosFiscalesClient/download/singlePDF/ComprobanteFiscal/'+jsonData.idComprobanteFiscal)
					$('.detailsForm .xmlBtn').attr('href','/documentosFiscalesClient/download/singleXML/ComprobanteFiscal/'+jsonData.idComprobanteFiscal)
					if (jsonData.hasComplemento){
						$('.detailsForm .pagoBtn').attr("href","/documentosFiscalesClient/download/singleComplemento/ComprobanteFiscal/"+jsonData.idComplemento)
						document.getElementById("sinpago").hidden = true
					}else {
						document.getElementById("conpago").hidden = true
					}
					
					document.getElementById("newSuppliers").hidden = true;
					
					$.get( "/catalogsAPI/getProveedores/"+jsonData.rfcProveedor+"/"+jsonData.rfcEmpresa, function( data ) {
						$('#newSuppliers').empty()
						data.forEach(function(value) { 
						     $('#newSuppliers')
						         .append($("<option></option>")
						         .attr("value",value.idProveedor)
						         .text(value.claveProv)); 
						});
							if(jsonData.proveedorNombre =='PROVEEDOR GENÉRICO') {
								$('#newSuppliers').append($("<option selected/>").val(jsonData.proveedorIdProveedor).text(jsonData.proveedorClaveProv));
							}
							
							$('#newSuppliers').val(jsonData.proveedorIdProveedor);
						});
						
						
					$(' .detailsForm #detailsModal').modal('show');
					
					$('.detailsForm .refreshBtn').on('click', function(event){
						event.preventDefault();
					
					  xhttp.onreadystatechange = function() {
					    if (this.readyState == 4 && this.status == 200) {
					    
					      $('.detailsForm #estatusSAT').val(this.responseText);
					    }
					    
						$('#toastSAT').toast('show')
						xhr.onreadystatechange=null
					  };
					  xhttp.open("POST", '/documentosFiscalesApi/getVigencia/'+jsonData.idComprobanteFiscal, true);
					  xhttp.send();
					  
					});
  
					$('.detailsForm .refreshProv').on('click', function(event){
					
						document.getElementById("newSuppliers").hidden = false;
					});
					
					 $('.detailsForm #bitRSusuario').on('click', function() {
					 		if (document.getElementById("bitRSusuario").checked == true){
					 			$('.detailsForm #bitRSusuarioText').val('true')
					 		} else{
					 			$('.detailsForm #bitRSusuarioText').val('false')
					 		}
					 
					 });
				
			});
						
				// Funcion para delete
					
		         $('#facturas tbody').on('click', 'td.delete-control', 'tr', function (event) {
			
					event.preventDefault();
					
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					$('.deleteForm .delBtn').attr("href","/documentosFiscalesClient/delete/"+jsonData.idComprobanteFiscal+"?rfc=" + $("#selectedCompany").text())
					$('#deleteModal').modal('show');
				});
				
				//Funcion para seleccionar
				
				$('#facturas tbody').on('click', 'td.select-control', function () {
		             var tr = $(this).closest('tr');
		             if ($(tr).hasClass('selected')) {
		                 tr.removeClass('selected')
		             }
		             else {
		                 tr.addClass('selected')
						
		             }	
		
				});
			 
					
				    // Handle click on "Expand All" button
				    $('#btn-show-all-children').on('click', function(){
				        // Enumerate all rows
				        table.rows().every(function(){
				            if(!this.child.isShown()){
				                // Open this row
				                this.child(format(this.data())).show();
				                $(this.node()).addClass('shown');
				            }
				        });
				    });
				
				    // Handle click on "Collapse All" button
				    $('#btn-hide-all-children').on('click', function(){
				        // Enumerate all rows
				        table.rows().every(function(){
				            if(this.child.isShown()){
				                // Collapse row details
				                this.child.hide();
				                $(this.node()).removeClass('shown');
				            }
				        });
				    });
				    

					// Boton de borrar varios
				    $('#btn-del-mul').on('click', function(){
					if ( table.rows( '.selected' ).any() ) {
				        // Enumerate all rows
						var ids = [];
						table.rows().every(function(){
				            if ($(this.node()).hasClass('selected')) {
							var data = this.data()
							var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
									ids.push(jsonData);
				 		  }
				       });
					$('.deleteForm .delBtn').attr("href","/documentosFiscalesClient/deleteMultipleFacturas?ids="+ids)
					$('#deleteModal').modal('show');
					}
				});
				
				
				// Boton de descargar varios
				    $('#xml').on('click', function(){
					if ( table.rows( '.selected' ).any() ) {
				        // Enumerate all rows
						var ids = [];
						table.rows().every(function(){
				            if ($(this.node()).hasClass('selected')) {
							var data = this.data()
							var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
									ids.push(jsonData);
				 		  }
				       });
					$('#xml').attr("href","/documentosFiscalesClient/download/zipXML/ComprobanteFiscal/?ids="+ids)
					}
				});
				
				$('#pdf').on('click', function(){
					if ( table.rows( '.selected' ).any() ) {
				        // Enumerate all rows
						var ids = [];
						table.rows().every(function(){
				            if ($(this.node()).hasClass('selected')) {
							var data = this.data()
							var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
									ids.push(jsonData);
				 		  }
				       });
					$('#pdf').attr("href","/documentosFiscalesClient/download/zipPDF/ComprobanteFiscal?ids="+ids)
					}
				});
				
				$('#csv').on('click', function(){
					if ( table.rows( '.selected' ).any() ) {
				        // Enumerate all rows
						var ids = [];
						table.rows().every(function(){
				            if ($(this.node()).hasClass('selected')) {
							var data = this.data()
							var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
									ids.push(jsonData);
				 		  }
				       });
					$('#csv').attr("href","/documentosFiscalesClient/csv/ComprobanteFiscal?ids="+ids)
					}
				});
				
				
				
				$('#comprimir').on('click', function(){
					if ( table.rows( '.selected' ).any() ) {
				        // Enumerate all rows
						var ids = [];
						table.rows().every(function(){
				            if ($(this.node()).hasClass('selected')) {
							var data = this.data()
							var jsonData = JSON.parse(JSON.stringify(data)).idComprobanteFiscal;
									ids.push(jsonData);
				 		  }
				       });
					$('#comprimir').attr("href","/documentosFiscalesClient/download/zip/ComprobanteFiscal?ids="+ids)
					}
				});
				
				$("#selectAll").on( "click", function(e) {
				
			    if ($(this).is( ":checked" )) {
			        table.rows( { search: 'applied' } ).select();        
			    } else {
			        table.rows(  ).deselect(); 
			    }
				});
				
				
				//PASTEÑAS DEL SIDE NAV BAR
				
				$("#pestañaFacturas").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaFacturas").addClass("active")
				document.getElementById("divFacturas").hidden = false;
				document.getElementById("divAvisos").hidden = true;
				document.getElementById("divLog").hidden = true;
				$("#pestañaInicio, #pestañaAvisos, #pestañaLog, #pestañaNotas, #pestañaCompl").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( 'I' )
					    .draw();
				
			});
			
			$("#pestañaInicio").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaInicio").addClass("active")
				document.getElementById("divFacturas").hidden = false;
				document.getElementById("divAvisos").hidden = true;
				document.getElementById("divLog").hidden = true;
				$("#pestañaFacturas, #pestañaAvisos, #pestañaLog, #pestañaNotas, #pestañaCompl").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( '' )
					    .draw();
			});
			
			$("#pestañaCompl").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaCompl").addClass("active")
				document.getElementById("divFacturas").hidden = false;
				document.getElementById("divAvisos").hidden = true;
				document.getElementById("divLog").hidden = true;
				$("#pestañaInicio, #pestañaAvisos, #pestañaLog, #pestañaNotas, #pestañaFacturas").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( 'P' )
					    .draw();
			});

		       $("#pestañaNotas").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaNotas").addClass("active")
				document.getElementById("divFacturas").hidden = false;
				document.getElementById("divAvisos").hidden = true;
				document.getElementById("divLog").hidden = true;
				$("#pestañaFacturas, #pestañaAvisos, #pestañaLog, #pestañaInicio, #pestañaCompl").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( 'E' )
					    .draw();
			});
			
			 $("#pestañaAvisos").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaAvisos").addClass("active")
				$("#pestañaFacturas, #pestañaNotas, #pestañaLog, #pestañaInicio, #pestañaCompl").removeClass("active") 
				document.getElementById("divFacturas").hidden = true;
				document.getElementById("divLog").hidden = true;
				table2.columns.adjust();
				document.getElementById("divAvisos").hidden = false;
				table2.columns.adjust();
				
				
			});
			
			 $("#pestañaLog").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaLog").addClass("active")
				$("#pestañaFacturas, #pestañaAvisos, #pestañaNotas, #pestañaInicio, #pestañaCompl").removeClass("active") 
				document.getElementById("divFacturas").hidden = true;
				document.getElementById("divAvisos").hidden = true;
				table3.draw();
				document.getElementById("divLog").hidden = false;
				table3.columns.adjust();
				
				
			});
			 
			
				
		});