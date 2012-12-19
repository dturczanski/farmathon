function submitAcceptForm(accepted) {
	var input = $("<input>").attr("type", "hidden").attr("name", "accepted").val(accepted);
	$('#accept_form').append($(input));
	$("#accept_form").submit() 
}	

$(document).ready(function(){
	$("#acceptBtn").click(function(){submitAcceptForm(true)});
	$("#rejectBtn").click(function(){submitAcceptForm(false)});
});