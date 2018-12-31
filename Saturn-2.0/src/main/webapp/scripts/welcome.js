$(document).ready(function(){
	$(function() {
		var wait = $("#seconds_remains").html();
		timeOut();
		/**
		 * Counting down.
		 */
		function timeOut() {
			if(wait != 0) {
				setTimeout(function() {
					$('#seconds_remains').html(--wait);
					timeOut();
				}, 1000);
			}
		}
	});
});

