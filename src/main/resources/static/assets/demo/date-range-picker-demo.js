$(function () {
	
    var start = moment().subtract(7, "days");
    var end = moment();

    function cb(start, end) {
        $("#reportrangeCarga span").html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
		
    }

    $("#reportrangeCarga").daterangepicker(
        {
			 "locale": {
		        "format": "DD/MM/YYYY",
		        "separator": " - ",
		        "applyLabel": "Aplicar",
		        "cancelLabel": "Cancelar",
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
                Hoy: [moment(), moment()],
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

    cb(start, end);
});

$(function () {
	
    var start = moment().subtract(7, "days");
    var end = moment();

    function cb(start, end) {
        $("#reportrangeEmision span").html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
		
    }

    $("#reportrangeEmision").daterangepicker(
        {
			"locale": {
		        "format": "DD/MM/YYYY",
		        "separator": " - ",
		        "applyLabel": "Aplicar",
		        "cancelLabel": "Cancelar",
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
                Hoy: [moment(), moment()],
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

    cb(start, end);
});
