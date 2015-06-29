<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script src="<c:url value="/resources/js/Chart.js" />"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<title>Spring MVC Tutorial Series by Crunchify.com</title>
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
</style>
</head>
<body>
	<canvas id="myChart" width="600" height="400"></canvas>
	<script>
		$(document).ready(function() {
			$("button").click(function() {
				$.get("<c:url value="/metric/search?metricName=metric2&applicationName=TestApp" />", function(retdata, status) {
					var newData = [];
					var dataLabels = []
					for	(index = 0; index < retdata.length; index++) {
						newData.push(retdata[index].sum);
						dataLabels.push(index);
					}
					data.datasets[0].data = newData;
					data.labels = dataLabels;
					var newctx = document.getElementById("myChart").getContext("2d");
					var newLineChart = new Chart(newctx).Line(data);
				});
			});
			
			$("form").on("submit",function(event) {
				event.preventDefault();
				var queryparams = $( this ).serialize();
				
				$.get("/MetricsService/metric/search?".concat(queryparams), function(retdata, status) {
					var newData = [];
					var dataLabels = []
					for	(index = 0; index < retdata.length; index++) {
						newData.push(retdata[index].sum);
						dataLabels.push(index);
					}
					data.datasets[0].data = newData;
					data.labels = dataLabels;
					var newctx = document.getElementById("myChart").getContext("2d");
					var newLineChart = new Chart(newctx).Line(data);
				});
			});
		});
		var data = {
			labels: [],
			datasets : [ {
				label : "My dataset",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : []
			} ]
		};

		var ctx = document.getElementById("myChart").getContext("2d");
		var myLineChart = new Chart(ctx).Line(data);
	</script>
	<br>
	<div style="text-align: center">
		<button>Get actual data</button>
		<form>
			<input type="text" name="applicationName">App Name</input> <input
				type="text" name="operation">Operation</input> <input type="text"
				name="marketplace">Marketplace</input> <input type="text"
				name="hostName">Host Name</input> <input type="text"
				name="metricName">Metric Name</input> <input type="submit"
				name="submit">Submit</input>
		</form>
	</div>
</body>
</html>