
	$(document).ready(function () {
				
		         var table = $('#formularios').DataTable({
					"paging":   false,
			        "ordering": false,
			        "info": false,
			        "searching": false,
					ajax: {
					dataSrc:""
		        	},
					drawCallback: function () {
					        var sum = $('#formularios').DataTable().column(6).data().sum();
					        $('#total').html(sum);
					      }	,
					scrollX:true,
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        },
			        "columns": [
					        {
			                "className":      'xls-control', 
			                "orderable":      false,
			                "data":           null,
			                "defaultContent": '',
							"render": function () {
		                        return '<a class="btn btn-datatable btn-icon btn-transparent-dark m-0"><i class="fas fa-file-excel"></i></a>'
		                     },
			            },
							{
		                     "className": 'details-control',
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
		 
 			
		//Nueva Form
			$('#btn-add-form').on('click', function(event) {
				
				event.preventDefault(); 
				
				//Llena los parametros del formulario 
				var href = $(this).attr('href');
				
				$.get(href, function(formulario, status){
					$("#folioFormularioNew").text(formulario.folio);
					$("#estatusNewForm").val(formulario.estatus);
					$("#idCajaChicaNew").val(formulario.proveedor.claveProv);
					$("#sucursalNew").val(formulario.proveedor.nombre);
					$("#fechaNew").val(formulario.fecha.split("T")[0]);
					$("#idFormNew").val(formulario.idFormularioCajaChica);
					$("#comentarioNew").val(formulario.comentario);
					$("#totalNew").val(formulario.total);
					$("#idFormulario").val(formulario.idFormularioCajaChica);
					
					//prepare cancel button too
					$("#cancelarNewForm").attr("href", "/cajachicaclient/deleteformcc?id=" + formulario.idFormularioCajaChica);
				});
				
					document.getElementById("divTabla").hidden= true;
					document.getElementById("divNuevo").hidden= false;
				
				
				//Crea la tabla de los detalles
				 var table2 = $('#detallesNuevoCajaChica').DataTable({
					"drawCallback": function( settings ) {
				        table2.columns.adjust();
				    },
					"paging":   false,
			        "ordering": false,
			        "info": false,
			        "searching":false,
					ajax: {
					dataSrc:"/cajachicaclient/loadformdetails?id="+formulario.idFormularioCajaChica
		        	},
					scrollX:true,
					"language": {
			            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
			        },
			           
					"order": [[ 0, "asc" ]]
					
				 });
				 
			 table2.columns.adjust();

			});
			
				 
			 // Darle submit a New Det
			$('#submitNewDet').on('click', function(event) {
			 
			 	$('#newDetModal').modal('hide');
			 	$( '#wizard2-tab' ).removeClass( 'active' );
			 	$( '#wizard1-tab' ).addClass( 'active' );
			 	document.getElementById("wizard2").hidden= true;
			 	document.getElementById("btns-next").hidden= false;
			 	document.getElementById("btns-prev").hidden= true;
			 	$('#siguienteNewDet').prop("disabled",true);
			 	document.getElementById("div-add-xml").hidden= true;
			 	document.getElementById("div-btn-add-xml").hidden= false;
			  	document.getElementById("div-add-pdf").hidden= true;
			 	document.getElementById("div-btn-add-pdf").hidden= false;
			 	
			  	document.getElementById("dateNewDiv").hidden = false;
			 	document.getElementById("montoNewDiv").hidden = false;
			  	document.getElementById("folioNewDiv").hidden = false;
				table2.ajax.reload();
				
				
			 });
			 // Darle Siguiente a New Det
			$('#siguienteNewDet').on('click', function() {
			 	
			 	$( '#wizard1-tab' ).removeClass( 'active' );
			 	$( '#wizard2-tab' ).addClass( 'active' );
			 	document.getElementById("wizard2").hidden= false;
			 	document.getElementById("btns-next").hidden= true;
			 	document.getElementById("btns-prev").hidden= false;
			 	
			 	
			 });
			 // Darle Anterior a New Det
			 $('#anteriorNewDet').on('click', function() {
			 	$( '#wizard2-tab' ).removeClass( 'active' );
			 	$( '#wizard1-tab' ).addClass( 'active' );
			 	$('#siguienteNewDet').prop("disabled",true);
			 	document.getElementById("wizard2").hidden= true;
			 	document.getElementById("btns-next").hidden= false;
			 	document.getElementById("btns-prev").hidden= true;
			 	
			 	document.getElementById("div-add-xml").hidden= true;
			 	document.getElementById("div-btn-add-xml").hidden= false;
			  	document.getElementById("div-add-pdf").hidden= true;
			 	document.getElementById("div-btn-add-pdf").hidden= false;
			 	
			  	document.getElementById("dateNewDiv").hidden = false;
			 	document.getElementById("montoNewDiv").hidden = false;
			  	document.getElementById("folioNewDiv").hidden = false;
				
				document.getElementById("formNewDet").reset();
			 });
			 // Darle Cancelar a New Det
			$('#cancelarNewDet').on('click', function() {
			 	
			 	$('#newDetModal').modal('hide');
			 	$('#siguienteNewDet').prop("disabled",true);
			 	$( '#wizard2-tab' ).removeClass( 'active' );
			 	$( '#wizard1-tab' ).addClass( 'active' );
			 	document.getElementById("wizard2").hidden= true;
			 	document.getElementById("btns-next").hidden= false;
			 	document.getElementById("btns-prev").hidden= true;
			 	
			 	document.getElementById("div-add-xml").hidden= true;
			 	document.getElementById("div-btn-add-xml").hidden= false;
			  	document.getElementById("div-add-pdf").hidden= true;
			 	document.getElementById("div-btn-add-pdf").hidden= false;
			 	
			  	document.getElementById("dateNewDiv").hidden = false;
			 	document.getElementById("montoNewDiv").hidden = false;
			  	document.getElementById("folioNewDiv").hidden = false;
			 
			 });
			 
			 // Darle Submit a New Formulario
			$('#cancelarNewForm').on('click', function(event) {
				
				
				var href = $(this).attr('href');
				
				$.get(href);
				
				document.getElementById("divTabla").hidden= false;
				document.getElementById("divNuevo").hidden= true;
				
			 	
			 
			 });
			 // Darle Cancelar a New Formulario
			$('#submitNewForm').on('click', function() {
			 	
			 	document.getElementById("divTabla").hidden= false;
				document.getElementById("divNuevo").hidden= true;
			
			 
			 });
			 
			  // Adjuntar un pdf al new details
			$('#btn-add-pdf').on('click', function() {
			 document.getElementById("div-add-pdf").hidden= false;
			 document.getElementById("div-btn-add-pdf").hidden= true;
			 
			 $('#siguienteNewDet').prop("disabled",false);
			 });
			  // adjuntar un xml al new details
			$('#btn-add-xml').on('click', function() {
			 document.getElementById("div-add-xml").hidden= false;
			 document.getElementById("div-btn-add-xml").hidden= true;
			 
			  document.getElementById("dateNewDiv").hidden = true
			  document.getElementById("montoNewDiv").hidden = true
			  document.getElementById("folioNewDiv").hidden = true 
			  $('#siguienteNewDet').prop("disabled",false);
			 });
				 
			
			// Funcion para delete formulario
					
		         $('#formularios tbody').on('click', 'td.delete-control', 'tr', function (event) {
			
					event.preventDefault();
					
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					$('.deleteForm .delBtn').attr("href","/cajachicaclient/deleteformcc?id="+jsonData.idFormularioCajaChica);
					$('#deleteModal').modal('show');
				});
				
			// Funcion para detalles del Formulario
					
		         $('#formularios tbody').on('click', 'td.details-control', function () {
					var tr = $(this).closest('tr');
		            var data = table.row($(this).parents(tr)).data();
					var modData = JSON.stringify(data);
					var jsonData = JSON.parse(modData);
					
					
					
			});
				
		
			
	});