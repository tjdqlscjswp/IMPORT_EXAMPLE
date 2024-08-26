	$(function() {
			var today = new Date();
			$("tr td:last-child div")
					.each(
							function(index, item) {

								var aa = new Date($(item).html());
								var time = today - aa;
								console.log(item);

								time = Math.ceil(time / 1000);
								if (time < 60) {
									$(item).html(time+'초 전');

								} else {
									time = Math.ceil(time / 60);
									if (time < 60) {
										$(item).html(time+'분 전');

									} else {
										time = Math.ceil(time / 60);
										if (time < 24) {
											$(item).html(time+'시간 전');

										} else {
											$(item).html((aa.getMonth() + 1 >= 10 ? aa.getMonth() + 1 : '0'
												+ (aa.getMonth() + 1))
												+ "-" + aa.getDate());

										}

									}
								}

							});
		});

		$(function() {

			var formObj = $("#f1");
			$(".pg a").click(function(e) {
				e.preventDefault(); //본래의 기능(link)을 취소
				formObj.find("[name='page']").val($(this).attr("href"));
				formObj.submit();
			});
			$("#sch_submit").click(function() {
				var typeStr = $("#searchType").find(":selected").val();
				var keywordStr = $("#sch_stx").val();
				formObj.find("[name='type']").val(typeStr);
				formObj.find("[name='keyword']").val(keywordStr);
				formObj.submit();
			});
		});
	
	function formatDate(t) {
				var date = new Date(t)
				return (date.getMonth() + 1 >= 10 ? date.getMonth() + 1 : '0'
						+ (date.getMonth() + 1))
						+ "-" + date.getDate()

			};