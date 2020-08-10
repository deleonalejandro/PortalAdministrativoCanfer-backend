$(document).ready(function() {
    $('#catalogTable').DataTable({
		scrollX: true,
    });

	$('.table .mybtn').on('click', function(event){
		
		event.preventDefault();
		
		var href = $(this).attr('href');
		
		$.get(href, function(user, status){
			$('#userId').val(user.idUsuario);
			$('#inputUsername').val(user.username);
			$('#inputFirstName').val(user.nombre);
			$('#inputLastName').val(user.apellido);
			$('#inputEmailAddress').val(user.correo);
			$('#checkActivo').prop('checked', user.activo);
			$('#dropdownRoles').val(user.rol);
			$('#dropdownPermisos').val(user.permisos.split(','));
			
			
		});
		
		$('#editModal').modal();
		
	});
});
