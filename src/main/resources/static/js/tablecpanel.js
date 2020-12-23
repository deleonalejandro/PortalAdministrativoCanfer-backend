$(document).ready(function () {
	
	
		  //Tabla de Log
		  var table3 = $('#logMov').DataTable({
					
					lengthChange:false,
					searching:false,
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
						width: '15%',
		                    "render": function(data) {
		                       var string = data.split(' ')
		                       return string[0]
						     }
						},
						{ data : "empresa" ,
							width: '20%',},
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
		                      
		                       return '<i style="color:green" data-feather="user-plus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_STORAGE"){
		                      
		                       return '<i style="color:red" data-feather="folder-minus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_DB"){
		                      
		                       return '<i style="color:red" data-feather="database"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "ERROR_FILE"){
		                      
		                       return '<i style="color:red" data-feather="file-minus"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "DELETE"){
		                      
		                       return '<i style="color:black" data-feather="trash-2"></i><script> feather.replace()</script>';
		                      
		                      } else if (data == "DELETE_USER"){
		                      
		                       return '<i style="color:red" data-feather="user-minus"></i><script> feather.replace()</script>';
		                      
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
	
	
	
});
	
	// Modal para modificar rutas
		 

		