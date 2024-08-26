$(function() {
	// 달력 띄워주는 js code
	$.ajax({
		url:'/delivery/deliveryCalender',
		success: function(result) {
			$('#here').html(result);
		}
	});
	
	// 페이지 넘버 클릭 시 넘버에 맞는 배송리스트를 띄워준다.
	var formObj = $("#f1");
	var selectDate = $("#select_date");
	
	$(document).on('click', '.pagination a', function(e){
		
		e.preventDefault(); //본래의 기능(link)을 취소
		formObj.find("[name='page']").val($(this).attr("href"));
		
		$.ajax({
			url:'/delivery/deliveryListSearch',
			data:{
				page:$(this).attr("href"),
				date:selectDate.val()
				},
			success: function(result) {
				$('#hoos1').html(result);
			}
		});	
	});
});