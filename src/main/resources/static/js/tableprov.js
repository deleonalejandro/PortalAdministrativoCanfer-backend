        //Tabla en si
		
		$(document).ready(function () {
		         var table = $('#facturas').DataTable({
					ajax: {
		            url: "/proveedorApi?empresa=" + $("#selectedCompany").text()+"&clave=" + $("#selectedClave").text(),
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
						{ data : "pago",
							"render": function(data) {
		                        if(data != null) {
		                            return data.totalPago;
		                        } else{
		                        	return "";
		                        }
		                       }
		                    },
						{ data : "pago",
							"render": function(data) {
		                        if(data != null) {
		                            return data.fecMvto;
		                        } else{
		                        	return "";
		                        }
		                       }
		                    },
						{ data : "pago",
							"render": function(data) {
		                        if(data != null) {
		                            return data.idNumPago;
		                        } else{
		                        	return "";
		                        }
		                       }
		                    },
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
		 
		 //Tabla de Avisos
		  var table2 = $('#avisosDePago').DataTable({
					ajax: {
		            url: "/proveedorApi/avisos/" +$("#selectedCompany").text()+ "/" +$("#selectedClave").text(),
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
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark float-left" href="/documentosFiscalesClient/preview/avisoPago/'+data.idPago+'" target="_blank"><i class="fa fa-file-pdf fa-lg" style="color:red"></i></a>' 
							 },
		                 },
		                { data : "idNumPago" },
		                { data : "fecMvto" },
		                { data : "totalPago" },
		                { data : "totalFactura" }
		             ]
					
		 });
 			
			
			
			
				// Funcion para modal detalles
				//Detalles
		         $('#facturas tbody').on('click', 'td.modal-control', function () {
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					
					$(' #uuid').val(jsonData.uuid)
					$('#empresa').val(jsonData.empresa.nombre)
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
					$('#remitente').val(jsonData.empresa.correo)
					
					if(jsonData.pago != null){
						$('#numPago').val(jsonData.pago.idNumPago)
						$('#fechaPago').val(jsonData.pago.fecMvto)
						$('#totalPago').val(jsonData.pago.totalPago)
						$('#totalFactura').val(jsonData.pago.totalFactura)
						$('#pdfDetails').attr('href','/documentosFiscalesClient/preview/avisoPago/'+jsonData.pago.idPago)
					
					}
					
					if(jsonData.serie != null && jsonData.folio != null){
						$('#headerValue').text('Factura ' + jsonData.serie + jsonData.folio)
					} else if(jsonData.serie == null){
						$(' #headerValue').text('Factura ' + jsonData.folio)
					} else if(jsonData.folio == null){
						$('#headerValue').text('Factura ' + jsonData.serie)
					};
					         
					$('#detallesFactura').addClass('show');
					$('#detallesFactura').addClass('active');
					$('#detallesFacturaTab').addClass("active");
					$("#comentarioFactura, #pagoFactura").removeClass("show");
					$("#comentarioFacturaTab, #pagoFacturaTab").removeClass("active");
					$("#comentarioFactura, #pagoFactura").removeClass("active");
					
					if(jsonData.pago == null){
						$('#pagoFacturaTab').addClass("disabled");
					} else{
						$('#pagoFacturaTab').removeClass("disabled");
					}
					if (jsonData.comentario == ""){
						$('#comentarioFacturaTab').addClass("disabled");
					} else{
						$('#comentarioFacturaTab').removeClass("disabled");
					}
					
					$('#detailsModal').modal('show');
					
			
			});
			//Pago
			  $('#facturas tbody').on('click', 'td.pago-control', function () {
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					if (jsonData.pago != null){
					
						$(' #uuid').val(jsonData.uuid)
					$('#empresa').val(jsonData.empresa.nombre)
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
					$('#remitente').val(jsonData.empresa.correo)
						
						if(jsonData.pago != null){
							$('#numPago').val(jsonData.pago.idNumPago)
							$('#fechaPago').val(jsonData.pago.fecMvto)
							$('#totalPago').val(jsonData.pago.totalPago)
							$('#totalFactura').val(jsonData.pago.totalFactura)
							$('#pdfDetails').attr('href','/documentosFiscalesClient/preview/avisoPago/'+jsonData.pago.idPago)
						
						}
						
						if(jsonData.serie != null && jsonData.folio != null){
						$('#headerValue').text('Factura ' + jsonData.serie + jsonData.folio)
					} else if(jsonData.serie == null){
						$(' #headerValue').text('Factura ' + jsonData.folio)
					} else if(jsonData.folio == null){
						$('#headerValue').text('Factura ' + jsonData.serie)
					};
					      
						         
						$('#pagoFactura').addClass("show");
						$('#pagoFacturaTab').addClass("active");
						$('#pagoFactura').addClass("active");
						$('#comentarioFactura, #detallesFactura').removeClass("show");
						$("#comentarioFacturaTab, #detallesFacturaTab").removeClass("active");
						$("#comentarioFactura, #detallesFactura").removeClass("active");
						
						if(jsonData.pago == null){
						$('#pagoFacturaTab').addClass("disabled");
						} else{
							$('#pagoFacturaTab').removeClass("disabled");
						}
						if (jsonData.comentario == ""){
							$('#comentarioFacturaTab').addClass("disabled");
						} else{
							$('#comentarioFacturaTab').removeClass("disabled");
						}
					
						$(' #detailsModal').modal('show');
						
				}
			});
			
			//Comentario
			  $('#facturas tbody').on('click', 'td.comment-control', function () {
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					if(jsonData.comentario != ""){
						$(' #uuid').val(jsonData.uuid)
					$('#empresa').val(jsonData.empresa.nombre)
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
					$('#remitente').val(jsonData.empresa.correo)
						
						if(jsonData.pago != null){
							$('#numPago').val(jsonData.pago.idNumPago)
							$('#fechaPago').val(jsonData.pago.fecMvto)
							$('#parcialidad').val(jsonData.pago.totalParcialidad)
							$('#totalFactura').val(jsonData.pago.totalFactura)
							$('#pdfDetails').attr('href','/documentosFiscalesClient/preview/avisoPago/'+jsonData.pago.idPago)
						
						}
						
						if(jsonData.serie != null && jsonData.folio != null){
						$('#headerValue').text('Factura ' + jsonData.serie + jsonData.folio)
					} else if(jsonData.serie == null){
						$(' #headerValue').text('Factura ' + jsonData.folio)
					} else if(jsonData.folio == null){
						$('#headerValue').text('Factura ' + jsonData.serie)
					};
					      
						         
						$('#comentarioFactura').addClass("show");
						$('#comentarioFactura').addClass("active");
						$('#comentarioFacturaTab').addClass("active");
						$('#detallesFactura, #pagoFactura').removeClass("active");
						$('#detallesFactura, #pagoFactura').removeClass("show");
						$("#detallesFacturaTab, #pagoFacturaTab").removeClass("active");
						
						if(jsonData.pago == null){
						$('#pagoFacturaTab').addClass("disabled");
						} else{
							$('#pagoFacturaTab').removeClass("disabled");
						}
						if (jsonData.comentario == ""){
							$('#comentarioFacturaTab').addClass("disabled");
						} else{
							$('#comentarioFacturaTab').removeClass("disabled");
						}
						
						$('#detailsModal').modal('show');
					}
				
			});
						
						
				
				
				
				//PASTEÑAS DEL SIDE NAV BAR
			
			$("#pestañaInicio").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaInicio").addClass("active")
				document.getElementById("divFacturas").hidden = false;
				document.getElementById("divAvisos").hidden = true;
				document.getElementById("divPerfil").hidden = true;
				document.getElementById("divAyuda").hidden = true;
				$("#pestañaAvisos,  #pestañaAyuda, #pestañaPerfil").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( '' )
					    .draw();
			});
			
			 $("#pestañaAvisos").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaAvisos").addClass("active")
				$("#pestañaInicio, #pestañaPerfil, #pestañaAyuda").removeClass("active") 
				document.getElementById("divFacturas").hidden = true;
				document.getElementById("divAyuda").hidden = true;
				document.getElementById("divPerfil").hidden = true;
				table2.columns.adjust();
				document.getElementById("divAvisos").hidden = false;
				table2.columns.adjust();
				
			});
			
			 $("#pestañaPerfil").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaPerfil").addClass("active")
				$("#pestañaInicio, #pestañaAvisos, #pestañaAyuda").removeClass("active") 
				document.getElementById("divFacturas").hidden = true;
				document.getElementById("divAyuda").hidden = true;
				document.getElementById("divPerfil").hidden = false;
				document.getElementById("divAvisos").hidden = true;
				
			});
			
			 $("#pestañaAyuda").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaAyuda").addClass("active")
				$("#pestañaInicio, #pestañaPerfil, #pestañaAvisos").removeClass("active") 
				document.getElementById("divFacturas").hidden = true;
				document.getElementById("divAyuda").hidden = false;
				document.getElementById("divPerfil").hidden = true;
				document.getElementById("divAvisos").hidden = true;
				
			});
		      
				
		});