$(function() {
	menuinit();
})
function menuinit() {
	$
			.ajax({
				url : "/api/mainMenu",
				type : "get",
				dataType : 'text',
				success : function(data) {
				
				}
			});
}
