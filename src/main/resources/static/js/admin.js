$(document).ready(function() {
	// Tabla de proveedores
	var tableProveedor = $('#proveedorTable').DataTable({
		select: true,
		ajax: {
			url: "/catalogsAPI/getSuppliers",
			dataSrc: ""
		},
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		},
		columns: [
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
			{ data: "serie" },
			{ data: "moneda" },
			{ data: "bitActivo" },
			{ data: "contacto" },
			{ data: "correo" },
			{ data: "telefono" },
			{ data: "paginaWeb" },
			{ data: "localidad" },
		],
		"order": [[3, 'asc']],
	});
	
	
 
 	//Funcion para seleccionar
				
	$('#proveedorTable tbody').on('click', 'td.select-control', function () {
	
	 	var tr = $(this).closest('tr');
         if ($(tr).hasClass('selected')) {
             tr.removeClass('selected')
         }
         else {
         	tableProveedor.$('tr.selected').removeClass('selected');
             tr.addClass('selected')
			
         }	
  

	});
    $('#provToSuc').click( function () {
        jsonData = tableProveedor.row('.selected').data();
        
        var addSuc = $.ajax({
								  url: "/admin/addsucursal?idProveedor="+jsonData.idProveedor,
								  cache: false,
								  contentType: false,
								  processData: false,
								  type: 'GET',
								});
				
				addSuc.done(function(upload) {
		
			if (upload == true) {
				$('#alert-addsuc').prop('hidden', false);
			} else {
				$('#alert-error-addsuc').prop('hidden', false);
			} 

			setTimeout(function() {
				$('.alert').prop('hidden', true);

			}, 6000);


		});
    } );
    

	// Tabla de usuarios	
	var tableUsuario = $('#userTable').DataTable({
		ajax: {
			url: "/catalogsAPI/getUsersPA",
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
			{ data: "empresasNombre"},
			{ data: "type"},
			{ data: "nombre" },
			{ data: "apellido" },
			{ data: "username" },
			{ data: "correo" },
			{ data: "rol" },
			{ data: "permisos" },
			{ data : "activo" ,
	            "render": function(data) {
	                if(data == false) {
	                    return '<i class="far fa-square" ></i>';
	                }
	                if(data == true) {
	                    return '<i class="far fa-check-square" ></i>';
	                }
				}
			 },
		],
		"order": [[3, 'asc']],
	});
	
	// Tabla de usuario proveedor
	var tableUsuarioProveedor = $('#userProveedorTable').DataTable({
		ajax: {
			url: "/catalogsAPI/getUsersPP",
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
			{ data: "empresasNombre"},
			{ data: "rfcProveedor"},
			{ data: "type"},
			{ data: "nombre" },
			{ data: "apellido" },
			{ data: "username" },
			{ data: "correo" },
			{ data: "rol" },
			{ data: "permisos" },
			{ data : "activo" ,
	            "render": function(data) {
	                if(data == false) {
	                    return '<i class="far fa-square" ></i>';
	                }
	                if(data == true) {
	                    return '<i class="far fa-check-square" ></i>';
	                }
				}
			 },
		],
		"order": [[3, 'asc']],
	});
	
	// Tabla de empresa
	var tableEmpresa = $('#empresaTable').DataTable({
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		}
		
	});
	
	// Tabla de sucursal
	var tableSuc = $('#sucursalTable').DataTable({
		scrollX: true,
		"language": {
			"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
		}
		
	});

	/****** Funciones para las tablas: elimar, editar *******/

	// Funciona para editar tabla usuarios
	$('#userTable tbody').on('click', '.editBtn', function() {
		var tr = $(this).closest('tr');
		var data = tableUsuario.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var user = JSON.parse(modData);

		$("#userId").val(user.idUsuario);
		$("#inputUsername").val(user.username);
		$("#inputFirstName").val(user.nombre);
		$("#inputLastName").val(user.apellido);
		$("#dropdownRoles").val(user.rol);
		$("#inputEmailAddress").val(user.correo);
		$("#checkActivo").prop('checked', user.activo);

		$("#dropdownPermisos").val(user.permisos.split(','));
		$("#dropdownEmpresas").val(user.empresasId);
		
		$('#editModal').modal('show');

	});

	// Funcion para delete usuarios
	$('#userTable tbody').on('click', 'td.delete-control', 'tr', function(event) {

		event.preventDefault();

		var tr = $(this).closest('tr');
		var data = tableUsuario.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		$('.deleteForm .delBtn').attr("href", "user/delete/" + jsonData.idUsuario)
		$('#deleteModal').modal("show");
	});
	
		// Funciona para editar tabla usuarios proveedor
	$('#userProveedorTable tbody').on('click', '.editBtn', function() {
		var tr = $(this).closest('tr');
		var data = tableUsuarioProveedor.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var user = JSON.parse(modData);

		$("#userId").val(user.idUsuario);
		$("#inputUsername").val(user.username);
		$("#inputFirstName").val(user.nombre);
		$("#inputLastName").val(user.apellido);
		$("#dropdownRoles").val(user.rol);
		$("#dropdownPermisos").val(user.permisos.split(','));
		$("#inputEmailAddress").val(user.correo);
		$("#dropdownEmpresas").val(user.empresasId.join());
		$("#checkActivo").prop('checked', user.activo);
		$("#checkActivo").prop('value', user.activo);

		$('#editModal').modal('show');
		
	
		$("#checkActivo").on('change', function() {
		    $(this).attr('value', $(this).is(':checked'));		  
		});

	});

	// Funcion para delete usuarios proveedor
	$('#userProveedorTable tbody').on('click', 'td.delete-control', 'tr', function(event) {

		event.preventDefault();

		var tr = $(this).closest('tr');
		var data = tableUsuarioProveedor.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		$('.deleteForm .delBtn').attr("href", "user/delete/" + jsonData.idUsuario)
		$('#deleteModal').modal("show");
	});


	// Funcion para editar proveedores

	$('#proveedorTable tbody').on('click', '.editBtn', function() {
		
		var tr = $(this).closest('tr');
		var data = tableProveedor.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

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
	
	// Funcion para info sucursales

	$('#sucursalTable tbody').on('click', '.editBtn', function() {
		
		event.preventDefault();
		var href = $(this).attr('href');
		
		
		$.get(href, function(jsonData, status){
			
			$("#inputNombre").val(jsonData.nombreSucursal);
			$("#inputclaveProv").val(jsonData.claveProv);
			$("#inputEmpresa").val(jsonData.empresaNombre);
			$("#inputRfcEmpresa").val(jsonData.empresaRfc);
			$("#infoUseridSuc").val(jsonData.idSucursal);
			
			var list=jsonData.usersAndId;
			var select = document.getElementById("listUsuarios");
				
			for (var i = 0; i < list.length; i++) {
				var string = list[i].split('-')
		        option = document.createElement( 'option' );
		        option.value = string[0];
		        option.text = string[1];
		        select.add( option );
	
		     
		   
		    }
		    
			
		})


		$('#infoModal').modal('show');

	});
	
	$("#listUsuarios").on('change', function(){
    	$('#removeUser').attr('href','/admin/sucursales/removeuser?suc='+ $("#infoUseridSuc").val() +'&user='+ $("#listUsuarios").val())
  	});
  	
	$("#infoModal").on('hide.bs.modal', function(){
    	clearOptions('listUsuarios')
    	$('#removeUser').attr('href','')
  	});
  	
  	// Funcion para añadir user a  sucursales
  	
	var clearOptions = function(id) {
	    var select = document.getElementById(id);
	    for (var i = select.options.length - 1 ; i >= 0 ; i--)
	        select.remove(i);
	}
	
	$('#sucursalTable tbody').on('click', '.addUserBtn', function() {
		
		event.preventDefault();
		var href = $(this).attr('href');
		
		
		$.get(href, function(jsonData, status){
			
			$("#newUserNombre").val(jsonData.nombreSucursal);
			$("#newUseridSuc").val(jsonData.idSucursal);
		 
			
		})
		
		$.get('/admin/sucursal/users', function(jsonData, status){
			
				var list=jsonData;
				var select = document.getElementById("usuariosSelect");
				
				for (var i = 0; i < list.length; i++) {
					var string = jsonData[i].split('-')
			        option = document.createElement( 'option' );
			        option.value = string[0];
			        option.text = string[1];
			        select.add( option );
		
			     
			   
			    }
			
		})
			
	    $('#addUserSucform').attr('action', '/admin/sucursales/adduser');
		$('#addUserSuc').modal('show');

	});
	
	$("#submitadduser").on('click', function(){
    	document.getElementById("addUserSucform").submit();
  	});
  	
  	$("#addUserSuc").on('hide.bs.modal', function(){
    	clearOptions('usuariosSelect');
  	});
		
	// Funcion para delete en proveedores

	$('#proveedorTable tbody').on('click', 'td.delete-control', 'tr', function(event) {

		event.preventDefault();

		var tr = $(this).closest('tr');
		var data = tableProveedor.row($(this).parents(tr)).data();
		var modData = JSON.stringify(data);
		var jsonData = JSON.parse(modData);

		$('.deleteForm .delBtn').attr("href", "supplier/delete/" + jsonData.idProveedor)
		$('#deleteModal').modal("show");
	});
	
	//Borrar sucursal
	$('#delBtn-suc').on('click', function(){
	
		event.preventDefault();
		var href = $(this).attr('href');
		
			var borrarSuc = $.ajax({
								  url: href,
								  cache: false,
								  contentType: false,
								  processData: false,
								  type: 'GET',
								});
				
				borrarSuc.done(function() {
					sucursalTable.ajax.reload(null, false);
					$('.modal').modal('hide');
				})
	
	});

	
	// Funcion para editar empresas

	$('#empresaTable .editBtn').on('click', function(event) {
		
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href, function(empresa, status){
			
			$("#idEmpresa").val(empresa.idEmpresa);
			$("#inputNombre").val(empresa.nombre);
			$("#inputRfc").val(empresa.rfc);
			$("#inputContacto").val(empresa.contacto);
			$("#inputCorreo").val(empresa.correo);
			$("#inputCalle").val(empresa.calle);
			$("#inputExterior").val(empresa.numExt);
			$("#inputColonia").val(empresa.colonia);
			$("#inputLocalidad").val(empresa.localidad);
			$("#inputReferencia").val(empresa.referencia);
			$("#inputCp").val(empresa.cp);
			$("#inputPaginaWeb").val(empresa.paginaWeb);
			$("#inputColor").val(empresa.color);
			
			
		})



		$('#editModal').modal('show');

	});
	
	// Clear cookies before when changing company
	$("#dashboardButton").on( "click", function() {
		
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




	//FILTROS
	
		// usuarios portal
	
	 // Setup - add a text input to each footer cell
    $('#userTable thead tr').clone(true).appendTo( '#userTable thead' );
    $('#userTable thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if(title !=''){
	        
	        $(this).html( '<input type="text" placeholder="Buscar '+title+'" />' );
	 
	        $( 'input', this ).on( 'keyup change', function () {
	            if ( tableUsuario.column(i).search() !== this.value ) {
	                tableUsuario
	                    .column(i)
	                    .search( this.value )
	                    .draw();
	            }
	        } );
        }
    } );
    
    	// usuarios proveedores
	
	 // Setup - add a text input to each footer cell
    $('#userProveedorTable thead tr').clone(true).appendTo( '#userProveedorTable thead' );
    $('#userProveedorTable thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if(title !=''){
	        
	        $(this).html( '<input type="text" placeholder="Buscar '+title+'" />' );
	 
	        $( 'input', this ).on( 'keyup change', function () {
	            if ( tableUsuarioProveedor.column(i).search() !== this.value ) {
	                tableUsuarioProveedor
	                    .column(i)
	                    .search( this.value )
	                    .draw();
	            }
	        } );
        }
    } );
    
    	// empresas
	
	 // Setup - add a text input to each footer cell
    $('#empresaTable thead tr').clone(true).appendTo( '#empresaTable thead' );
    $('#empresaTable thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if(title !=''){
	        
	        $(this).html( '<input type="text" placeholder="Buscar '+title+'" />' );
	 
	        $( 'input', this ).on( 'keyup change', function () {
	            if ( tableEmpresa.column(i).search() !== this.value ) {
	                tableEmpresa
	                    .column(i)
	                    .search( this.value )
	                    .draw();
	            }
	        } );
        }
    } );
    
    	// proveedores
	
	 // Setup - add a text input to each footer cell
    $('#proveedorTable thead tr').clone(true).appendTo( '#proveedorTable thead' );
    $('#proveedorTable thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if(title !=''){
	        
	        $(this).html( '<input type="text" placeholder="Buscar '+title+'" />' );
	 
	        $( 'input', this ).on( 'keyup change', function () {
	            if ( tableProveedor.column(i).search() !== this.value ) {
	                tableProveedor
	                    .column(i)
	                    .search( this.value )
	                    .draw();
	            }
	        } );
        }
    } );
    
    // sucursales
	
	 // Setup - add a text input to each footer cell
    $('#sucursalTable thead tr').clone(true).appendTo( '#sucursalTable thead' );
    $('#sucursalTable thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if(title !=''){
	        
	        $(this).html( '<input type="text" placeholder="Buscar '+title+'" />' );
	 
	        $( 'input', this ).on( 'keyup change', function () {
	            if ( tableSuc.column(i).search() !== this.value ) {
	                tableSuc
	                    .column(i)
	                    .search( this.value )
	                    .draw();
	            }
	        } );
        }
    } );
	


});
