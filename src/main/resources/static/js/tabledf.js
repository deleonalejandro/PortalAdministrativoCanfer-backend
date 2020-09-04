	//Formato para rows tipo child	  
  function format(d){
	        
	         // `d` is the original data object for the row
	         return '<table class="" style="text-align: left">' +
	             '<tr>' +
						'<i class="far fa-file-pdf float-left ml-3 fa-lg" style="color:red"></i>'+
						'<i class="far fa-file-code float-left ml-3 fa-lg" style="color:green"></i>'+
						'<i class="fas fa-file-invoice-dollar float-left ml-3 fa-lg" style="color:orange"></i>'+
	             '</tr>' +
	         '</table>';  
	    };


            //Tabla en si
		
		$(document).ready(function () {
			
		         var table = $('#facturas').DataTable({
					ajax: {
		            url: "/documentosFiscalesApi/facturas",
					dataSrc:""
		        	},
					scrollX:true,
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        },
			             "columns": [
			 		{
		                     "className": 'select-control',
		                     "orderable": false,
							"bSortable": false,
		                     "data": null,
		                     "defaultContent": '',
		                     "render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="far fa-circle fa-sm"></i></a>'
							 },
		                 },
					        {
			                "className":      'details-control', 
			                "orderable":      false,
			                "data":           null,
							"width": "2%",
			                "defaultContent": '',
							"render": function () {
								return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"></a>'
								},
			            },
							{
		                     "className": 'modal-control',
		                     "orderable": false,
							"bSortable": false,
		                     "data": null,
		                     "defaultContent": '',
		                     "render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="fa fa-ellipsis-h fa-sm" style="color:#0A61F2"></i></a>'
							 },
		                 },
						{
		                     "className": 'delete-control',
		                     "orderable": false,
							 "bSortable": false,
		                     "data": null,
		                     "defaultContent": '',
		                     "render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="fa fa-trash-alt fa-sm" style=""></i></a>' ;
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
						{ data : "proveedor.claveProv"},
						{ data : "proveedor.nombre" },
						{ data : "rfcProveedor" },
						{ data : "tipoDocumento" },
						{ data : "total" },
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
						{ data : "fechaEmision" },
						{ data : "fechaEmision" },
						{ data : "total" },
						{ data : "fechaEmision" }
		             ],
		             "order": [[4, 'asc']],
					
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
				
				// Funcion para modal
					
		         $('#facturas tbody').on('click', 'td.modal-control', function () {
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					
					$('.detailsForm #uuid').val(jsonData.uuid)
					$('.detailsForm #idNumSap').val(jsonData.idNumSap)
					$('.detailsForm #empresa').val(jsonData.empresa)
					$('.detailsForm #proveedor').val(jsonData.proveedor)
					$('.detailsForm #serie').val(jsonData.serie)
					$('.detailsForm #folio').val(jsonData.folio)
					
					$('.detailsForm #rfcEmpresa').val(jsonData.rfcEmpresa)
					$('.detailsForm #rfcProveedor').val(jsonData.rfcProveedor)
					$('.detailsForm #fechaEmision').val(jsonData.fechaEmision)
					$('.detailsForm #fechaTimbre').val(jsonData.fechaTimbre)
					$('.detailsForm #noCertificadoEmpresa').val(jsonData.noCertificadoEmpresa)
					$('.detailsForm #noCertificadoSAT').val(jsonData.noCertificadoSAT)
					$('.detailsForm #versionCFD').val(jsonData.versionCFD)
					$('.detailsForm #versionTimbre').val(jsonData.versionTimbre)
					$('.detailsForm #moneda').val(jsonData.moneda)
					$('.detailsForm #total').val(jsonData.total)
					$('.detailsForm #tipoDocumento').val(jsonData.tipoDocumento)
					$('.detailsForm #estatus').val(jsonData.estatus)
					$('.detailsForm #respuestaValidacion').val(jsonData.respuestaValidacion)
					$('.detailsForm #errorValidacion').val(jsonData.errorValidacion)
					         
			        $('.detailsForm #bitRSusuario').prop("checked", jsonData.bitRSusuario);
			        $('.detailsForm #bitRS').prop("checked", jsonData.bitRS);
					$('.detailsForm #bitValidoSAT').prop("checked", jsonData.bitValidoSAT);
					
					
					$('.detailsForm #facturaNotaComplemento').val(jsonData.facturaNotaComplemento)
					
					
					$(' .detailsForm #detailsModal').modal('show');
					
				});
				
				
						
				// Funcion para delete
					
		         $('#facturas tbody').on('click', 'td.delete-control', 'tr', function (event) {
			
					event.preventDefault();
					
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					$('.deleteForm .delBtn').attr("href","/contaduria-nacional/delete/"+jsonData.uuid)
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
						var uuids = [];
						table.rows().every(function(){
				            if ($(this.node()).hasClass('selected')) {
							var data = this.data()
							var jsonData = JSON.parse(JSON.stringify(data)).uuid;
									uuids.push(jsonData);
				 		  }
				       });
					$('.deleteForm .delBtn').attr("href","/contaduria-nacional/delete/"+uuids)
					$('#deleteModal').modal('show');
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
				$("#pestañaInicio, #pestañaAvisos, #pestañaNotas, #pestañaCompl").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( 'I' )
					    .draw();
				
			});
			
			$("#pestañaInicio").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaInicio").addClass("active")
				$("#pestañaFacturas, #pestañaAvisos, #pestañaNotas, #pestañaCompl").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( '' )
					    .draw();
			});
			
			$("#pestañaCompl").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaCompl").addClass("active")
				$("#pestañaInicio, #pestañaAvisos, #pestañaNotas, #pestañaFacturas").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( 'P' )
					    .draw();
			});
			
			$("#pestañaNotas").on( "click", function() {
				table.rows().every(function(){($(this.node()).removeClass('selected'))});
				$("#pestañaNotas").addClass("active")
				$("#pestañaFacturas, #pestañaAvisos, #pestañaInicio, #pestañaCompl").removeClass("active") 
				table
					table
					    .columns( 10 )
					    .search( 'E' )
					    .draw();
			});
			
			
				
		});
				
			
 
		