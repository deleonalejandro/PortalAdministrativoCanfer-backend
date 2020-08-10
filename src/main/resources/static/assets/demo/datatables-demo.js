// Call the dataTables jQuery plugin


$(document).ready( function () {
			$('#facturas').DataTable({
            ajax: {
            url: "/facturas",
			dataSrc:""
        	},
			order: [1, "asc"],
            columns: [
				{
                     "className": 'details-control',
                     "orderable": false,
                     "data": null,
                     "defaultContent": '',
                     "render": function () {
                         return '<i class="fa fa-plus-square" aria-hidden="true"></i>';
                     },
                     width:"15px"
                 },
                { data : "idNumSap" },
                { data : "bitRS" },
                { data : "folio" },
				{ data : "proveedor" },
				{ data : "proveedor" },
				{ data : "tipoDocumento" },
				{ data : "total" },
				{ data : "estatus" },
				{ data : "fechaEmision" },
				{ data : "fechaEmision" },
				{ data : "total" },
				{ data : "fechaEmision" }
			
            ]
        });

/* Formatting function for row details*/
function format(d){
        
         // `d` is the original data object for the row
         return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
             '<tr>' +
                 '<td>Full name:</td>' +
                 '<td>' + d.uuid + '</td>' +
             '</tr>' +
             '<tr>' +
                 '<td>Extension number:</td>' +
                 '<td>' + d.uuid + '</td>' +
             '</tr>' +
             '<tr>' +
                 '<td>Extra info:</td>' +
                 '<td>And any further details here (images etc)...</td>' +
             '</tr>' +
         '</table>';  
    };

 
// Add event listener for opening and closing details
         $('#facturas tbody').on('click', 'td.details-control', function () {
             var tr = $(this).closest('tr');
             var tdi = tr.find("i.fa");
             var row = table.row(tr);

             if (row.child.isShown()) {
                 // This row is already open - close it
                 row.child.hide();
                 tr.removeClass('shown');
                 tdi.first().removeClass('fa-minus-square');
                 tdi.first().addClass('fa-plus-square');
             }
             else {
                 // Open this row
                 row.child(format(row.data())).show();
                 tr.addClass('shown');
                 tdi.first().removeClass('fa-plus-square');
                 tdi.first().addClass('fa-minus-square');
             }
         });

         table.on("user-select", function (e, dt, type, cell, originalEvent) {
             if ($(cell.node()).hasClass("details-control")) {
                 e.preventDefault();
             }
         });
     });

function format ( rowData ) {
    var div = $('<div/>')
        .addClass( 'loading' )
        .text( 'Loading...' );
 
    $.ajax( {
        url: '/facturas',
        data: {
            name: rowData.name
        },
        dataType: 'json',
        success: function ( json ) {
            div
                .html( json.html )
                .removeClass( 'loading' );
        }
    } );
 
    return div;
}

