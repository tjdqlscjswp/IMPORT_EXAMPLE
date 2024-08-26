/**
 * 
 */

// 상품이 없을 때 드래그 안내문 띄워주기
function rowFind() {
	if ($("#row_data").length > 0) {
		$("#basketdiv_list").show();
		$(".basketdiv_text").hide();
	} else {
		$("#basketdiv_list").hide();
		$(".basketdiv_text").show();
	}
}

let basket = {
    totalCount: 0, 
    totalPrice: 0,
    //체크한 장바구니 상품 비우기
    delCheckedItem: function(){
        document.querySelectorAll("input[name=buy]:checked").forEach(function (item) {
            item.parentElement.parentElement.parentElement.remove();
        });
        //AJAX 서버 업데이트 전송

        //전송 처리 결과가 성공이면
        this.reCalc();
        this.updateUI();
        rowFind();
    },
    //장바구니 전체 비우기
    delAllItem: function(){
        document.querySelectorAll('.row.data').forEach(function (item) {
            item.remove();
          });
          //AJAX 서버 업데이트 전송

          //전송 처리 결과가 성공이면
          this.totalCount = 0;
          this.totalPrice = 0;
          this.reCalc();
          this.updateUI();
          rowFind();
          resetIndex();
    },
    //재계산
    reCalc: function(){
        this.totalCount = 0;
        this.totalPrice = 0;
        document.querySelectorAll(".p_num").forEach(function (item) {
            if(item.parentElement.parentElement.parentElement.previousElementSibling.firstElementChild.firstElementChild.checked == true){
                var count = parseInt(item.getAttribute('value'));
                this.totalCount += count;
                var price = item.parentElement.parentElement.previousElementSibling.firstElementChild.getAttribute('value');
                this.totalPrice += count * price;
            }
        }, this); // forEach 2번째 파라메터로 객체를 넘겨서 this 가 객체리터럴을 가리키도록 함. - thisArg
    },
    //화면 업데이트
    updateUI: function () {

        document.querySelector('#sum_p_num').textContent = '상품갯수: ' + this.totalCount.formatNumber() + '개';
        document.querySelector('#sum_p_price').textContent = '합계금액: ' + this.totalPrice.formatNumber() + '원';
        document.querySelector('#package_price').setAttribute('value',salePrice(this.totalPrice));

        // 합계금액이 0원일 때 할인금액란을 없앤다. 
        if(this.totalPrice == 0) {
	        document.querySelector('#sale_p_price').textContent = '';	
		} else {
	        document.querySelector('#sale_p_price').textContent = '할인금액: ' + salePrice(this.totalPrice).formatNumber() + '원';			
		}

		// 0원 이상이면 합계 금액에 줄긋기
        textDeco();
    },
    //개별 수량 변경
    changePNum: function (pos) {
        //var item = document.querySelector('input[name=p_num'+pos+']');
        var item = document.querySelector('#p_num'+pos);
        var p_num = parseInt(item.getAttribute('value'));
        var newval = event.target.classList.contains('up') ? p_num+1 : event.target.classList.contains('down') ? p_num-1 : event.target.value;

        if (parseInt(newval) < 1 || parseInt(newval) > 99) { return false; }

        item.setAttribute('value', newval);
        item.value = newval;

        var price = item.parentElement.parentElement.previousElementSibling.firstElementChild.getAttribute('value');
        item.parentElement.parentElement.nextElementSibling.textContent = (newval * price).formatNumber()+"원";
        //AJAX 업데이트 전송

        //전송 처리 결과가 성공이면    
        this.reCalc();
        this.updateUI();
    },
    checkItem: function () {
        this.reCalc();
        this.updateUI();
    },
    delItem: function () {
        event.target.parentElement.parentElement.parentElement.remove();
        this.reCalc();
        this.updateUI();
        rowFind();
    }
}

// index counter
indexCounter = 1;
function addIndex() { // add count
	indexCounter += 1;
}
function resetIndex() { // count reset
	indexCounter = 1;
}

// 할인가 계산
function salePrice(totalPrice) {

	var result = 0;
	if(($('#auth').val()=="ADMIN") && ($('#auth_url').val()=="admin")) {
		result = totalPrice*0.85;
	} else {
		if(totalPrice >= 30000 && totalPrice < 50000) {
			result = totalPrice*0.90;
		}else if(totalPrice >= 50000) {
			result = totalPrice*0.85;		
		}else {
			result = totalPrice*0.95;
		}
	}

	return result;
}

// String -> Number 
function numberConverter(str) {
	return Number(str);
}

// 숫자 3자리 콤마찍기
Number.prototype.formatNumber = function(){
    if(this==0) return 0;
    let regex = /(^[+-]?\d+)(\d{3})/;
    let nstr = (this + '');
    while (regex.test(nstr)) nstr = nstr.replace(regex, '$1' + ',' + '$2');
    return nstr;
};

function textDeco() {
	// 합계금액이 0보다 커질 때 줄긋기
	if($("#row_data").length > 0) {
		$("#sum_p_price").css('text-decoration','line-through');
	} else {
		$("#sum_p_price").css('text-decoration','none');
	}
}

// 물품을 드래그 할 수 있게 만들어준다.
function drag() {
	$(".product").draggable({
		zIndex: 10000,
		helper: "clone"
	});
}


// 드래그 후 드롭할 때 가져갈 데이터와 html코드를 추가해준다.
function drop() {
	$(".basketdiv").droppable({
		drop: function(e, ui) {
			var product_id_duplicate_check = $(ui.draggable).find(".product_id").html();
			if($("."+product_id_duplicate_check).length == 0){
				$('.basketdiv_list').html($('.basketdiv_list').html()
					+"<div id='row_data' class='row data'>"
					+"<div class='subdiv'>"
					+"<div class='check'>"
					+"<input type='checkbox' name='buy' value='260' checked=''"
					+"onclick='javascript:basket.checkItem();'>&nbsp;"
					+"</div>"
					+"<div id='product_img' class='img'>"
					+"<img src='"+$(ui.draggable).find(".product_img").attr("src")+"' width='60'>"
					+"</div>"
					+"<div class='pname'>"
					+"<span class='"+product_id_duplicate_check+"'>"+$(ui.draggable).find(".product_name").html()+"</span>"
					+"</div>"
					+"</div>"
					+"<div class='subdiv'>"
					+"<div id='row_basketprice' class='basketprice'>"
					+"<input type='hidden' name='p_price' id='p_price1' class='p_price'"
					+"value='"+$(ui.draggable).find(".product_price").text()+"'>"+$(ui.draggable).find(".product_price").text()+"원"
					+"</div>"
					+"<div id='qty_num' class='num'>"
					+"<div class='updown'>"
					+"<input type='text' name='productQty' id='p_num"+indexCounter+"' size='2'"
					+"maxlength='4' class='p_num' value='1'"
					+"onkeyup='javascript:basket.changePNum("+indexCounter+");'>" 
					+"<span onclick='javascript:basket.changePNum("+indexCounter+");'>"
					+"<i class='fas fa-arrow-alt-circle-up up'></i>"
					+"</span>"
					+"<span onclick='javascript:basket.changePNum("+indexCounter+");'>"
					+"<i class='fas fa-arrow-alt-circle-down down'></i>"
					+"</span>"
					+"</div>"
					+"</div>"
					+"<div id='row_sum' class='sum'>"+numberConverter($(ui.draggable).find(".product_price").text()).formatNumber()+"원</div>"
					+"</div>"
					+"<div class='subdiv'>"
					+"<div class='basketcmd'>"
					+"<a href='javascript:void(0)' id='abutton' class='abutton'"
					+"onclick='javascript:basket.delItem();'>삭제</a>"
					+"</div>"
					+"</div>"
					+"<input type='hidden' name='productId' value='"+$(ui.draggable).find(".product_id").text()+"'>"
					+"<div class='check_div' style='display:none'>"
				+"</div>");
				rowFind();
				basket.reCalc(); // 패키지 리스트에 담았을 때 가격을 다시 계산 
				basket.updateUI(); // 패키지 리스트에 담을 때마다 화면 업데이트
				addIndex(); // 패키지 리스트에 담을 때 index + 1
			} else {
				alert("이미 담긴 상품입니다.");
			}
		}
	});
}

// 목록 삭제
function productRowDelete(row) {
	$(row).parent().remove();
}

// 클릭 시 패키지 정보를 form으로 전송 후 db에 저장하는 버튼
function packageAdd() {
	alert($("#package_price").val());
	if($("#package_price").val() > 0) {
		alert("submit");
		$('#orderform').submit();

	}else {
		alert("상품을 선택해주세요.");			
	}
}

// 어드민 패키지/제품 페이지에서 클릭 시 각 기능의 위치로 이동시키는 기능
function fnMove(seq){
    var offset = $("." + seq).offset();
    $('html, body').animate({scrollTop : offset.top-215}, 1000);
}

// 관리자 페이지 제품 삭제
function adminProductDelete(productId) {
	//alert("dd");
	//window.confirm("제품을 삭제하시겠습니까?");

	var product_id = $(productId).parent().children(".product_id").text();

	$.ajax({
		url:"/product/adminProductDelete",
		data: {
			productId: product_id
		},
		success: function() {
			location.reload();
		}
	});
}

$(function() {
 	// 카테고리 클릭/검색 기능
 	$(".category_name, #search_product").on("click",function() {
		var typeStr = $('.category_name').attr("name");
		var keywordStr;
		if($(this).hasClass('category_name')) {

			keywordStr = $(this).val();

		} else {

			/*typeStr = $('#search_type option:selected').val();*/
			typeStr = 'product';
			keywordStr = $('#search_keyword').val();
		}
		$.ajax({
			type:"GET",
			url:"/product/productList",
			data:{
				type:typeStr,
				keyword:keywordStr
			},
			success : function(list){
				$("#area1").html(list);
			},
			complete: function(data) {
				drag();
		    }
		})

	});
 	drag();
 	drop();

 	// 커스텀 패키지 추가
 	$('#package_buy_btn').click(function() {
		packageAdd();
	});	

	// 제품 insert
 	$('#product_add').click(function() {
		alert("submit");
		$('#add_product_form').submit();
	});

	// 권한에 따라 show, hide 할 html 태그들
	if(($('#auth').val()=="ADMIN") && ($('#auth_url').val()=="admin")) {
		// 패키지 상세설명 숨기기
		$('#custom_poster').hide();
		$('#package_buy_btn').hide();
		$('#package_make').show();
		$("#orderform").attr("action", "/product/customInsert");
		$("#package_type").attr("value", "0");
		$('#package_name').attr("disabled", true);
		$('#package_price').attr("disabled", true);

	} else {

		$('.product_delete_btn').hide();
		$('#custom_poster').show();
		$('#add_product').hide();
		$('#package_make').hide();
		$('.package_price_name_list').hide();
		$('#make_package_name').attr("disabled", true);
		$('#make_package_price').attr("disabled", true);
		$('#make_package_img').attr("disabled", true);
		$('#move_btn').hide();
		$('.add_package_h1').hide();
	}
	$(".add_product_div_body").hide();

	// 관리자 패키지 추가
	$('#package_make').click(function() {
		packageAdd();
	});

	// 목록 추가
	$('#add_list').click(function() {
		$('#add_product_div_body').append($(".add_product_div_body").html());
	});
});
