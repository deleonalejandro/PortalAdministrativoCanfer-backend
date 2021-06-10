$(function () {

    var start = moment().startOf('day');
    var end = moment().endOf('day');

    function cb(start, end) {
	
        $("#reportrangeCarga span").html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
		$("#uploadAfter").text(start.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
		$("#uploadBefore").text(end.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
    }

    $("#reportrangeCarga").daterangepicker(
        {
			 "locale": {
		        "format": "DD/MM/YYYY",
		        "separator": " - ",
		        "applyLabel": "Aplicar",
		        "cancelLabel": "Limpiar",
		        "fromLabel": "Desde",
		        "toLabel": "Hasta",
		        "customRangeLabel": "Personalizado",
		        "weekLabel": "W",
		        "daysOfWeek": [
		            "Do",
		            "Lu",
		            "Ma",
		            "Mie",
		            "Ju",
		            "Vie",
		            "Sa"
		        ],
		        "monthNames": [
		            "Enero",
		            "Febrero",
		            "Marzo",
		            "Abril",
		            "Mayo",
		            "Junio",
		            "Julio",
		            "Agosto",
		            "Septiembre",
		            "Octubre",
		            "Noviembre",
		            "Diciembre"
		        ],
		        "firstDay": 1
		    },
            startDate: start,
            endDate: end,
            ranges: {
                Hoy: [moment().startOf('day'), moment().endOf('day')],
                Ayer: [
                    moment().subtract(1, "days"),
                    moment().subtract(1, "days"),
                ],
                "Últimos 7 días": [moment().subtract(6, "days"), moment()],
                "Últimos 30 días": [moment().subtract(29, "days"), moment()],
                "Este mes": [
                    moment().startOf("month"),
                    moment().endOf("month"),
                ],
                "Último mes": [
                    moment().subtract(1, "month").startOf("month"),
                    moment().subtract(1, "month").endOf("month"),
                ],
            },
			
			
        },
        cb
    );

  $('#reportrangeCarga').on('apply.daterangepicker', function(ev, picker) {
	  $("#reportrangeCarga span").html(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
	  $("#uploadAfter").text(picker.startDate.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
	  $("#uploadBefore").text(picker.endDate.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
  });

  $("#reportrangeCarga").on('cancel.daterangepicker', function(ev, picker) {
	  picker.setStartDate(moment().startOf('day'));
      picker.setEndDate(moment().endOf('day'));
      $("#reportrangeCarga span").html('');
	  $("#uploadAfter").text('');
	  $("#uploadBefore").text('');
  });

});


$(function () {
	
    var start = moment().startOf("day");
    var end = moment().endOf("day");

    $("#reportrangeEmision").daterangepicker({
	
			autoUpdateInput: false,
			"locale": {	
		        "format": "DD/MM/YYYY",
		        "separator": " - ",
		        "applyLabel": "Aplicar",
		        "cancelLabel": "Limpiar",
		        "fromLabel": "Desde",
		        "toLabel": "Hasta",
		        "customRangeLabel": "Personalizado",
		        "weekLabel": "W",
		        "daysOfWeek": [
		            "Do",
		            "Lu",
		            "Ma",
		            "Mie",
		            "Ju",
		            "Vie",
		            "Sa"
		        ],
		        "monthNames": [
		            "Enero",
		            "Febrero",
		            "Marzo",
		            "Abril",
		            "Mayo",
		            "Junio",
		            "Julio",
		            "Agosto",
		            "Septiembre",
		            "Octubre",
		            "Noviembre",
		            "Diciembre"
		        ],
		        "firstDay": 1
		    },
            startDate: start,
            endDate: end,
            ranges: {
                Hoy: [moment().startOf("day"), moment().endOf("day")],
                Ayer: [
                    moment().subtract(1, "days"),
                    moment().subtract(1, "days"),
                ],
                "Últimos 7 días": [moment().subtract(6, "days"), moment()],
                "Últimos 30 días": [moment().subtract(29, "days"), moment()],
                "Este mes": [
                    moment().startOf("month"),
                    moment().endOf("month"),
                ],
                "Último mes": [
                    moment().subtract(1, "month").startOf("month"),
                    moment().subtract(1, "month").endOf("month"),
                ],
            },
        }, function (start, end) {
        $("#reportrangeEmision span").html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
		$("#registeredAfter").text(start.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
		$("#registeredBefore").text(end.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
		
    });

$('#reportrangeEmision').on('apply.daterangepicker', function(ev, picker) {
	  $("#reportrangeEmision span").html(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
	  $("#registeredAfter").text(picker.startDate.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
	  $("#registeredBefore").text(picker.endDate.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
  });

$("#reportrangeEmision").on('cancel.daterangepicker', function(ev, picker) { 
	
  	$("#reportrangeEmision span").html('');
  	$("#registeredAfter").text('');
  	$("#registeredBefore").text('');

  });

	
});

	$(function () {
	    $('input[name="fechaDet"]').daterangepicker(
	        {
			 autoUpdateInput: false,
	         locale: {
		      format: "DD/MM/YYYY"
		    },
	            singleDatePicker: true,
	            showDropdowns: true,
	            minYear: 2000,
	            maxYear: parseInt(moment().format("YYYY"), 10)+1,
	        },
		);

		$('#fechaDet').on('apply.daterangepicker', function(ev, picker) {
			
		  	$("#fechaDet").val(picker.startDate.format('DD/MM/YYYY'));
		  	$("#realDate").val(picker.startDate.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
	
  		})
  		
  		
	});
	
	$(function () {
		  	
	    $('input[name="detail-fechaDet"]').daterangepicker(
	        {
			 autoUpdateInput: false,
	         locale: {
		      format: "DD/MM/YYYY"
		    },
	            singleDatePicker: true,
	            showDropdowns: true,
	            minYear: 2000,
	            maxYear: parseInt(moment().format("YYYY"), 10)+1,
	        },
	        
		);

		$('#detail-fechaDet').on('apply.daterangepicker', function(ev, picker) {
			
		  	$("#detail-fechaDet").val(picker.startDate.format('DD/MM/YYYY'));
		  	$("#detail-realDate").val(picker.startDate.format('YYYY-MM-DD'+'T'+'HH:mm:ss'));
	
  		})
  		
  		
	});
	