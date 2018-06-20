function addAsUpdate(pid,proId,option1,option2,option3,option4,option5,option6,editor) {
    var result = 0;
    $.post("/score/update",
        {
            "pid": pid,
            "proId": proId,
            "option1": option1,
            "option2": option2,
            "option3": option3,
            "option4": option4,
            "option5": option5,
            "option6": option6,
            "model": getCookie("model"),
            "editor": editor
        },
        function(data,status){
            //alert(data.result);
            if (data.result == "1") {
                result = 1;
                alert(data.message);
            } else {
                result = 0;
                alert(data.message);
            }
        });
    return result;
}
var MyModal = (function() {
	function modal(fn) {
		this.fn = fn; //点击确定后的回调函数
		this._addClickListen();
	}
	modal.prototype = {
		show: function() {
			$('.m-modal').fadeIn(100);
			$('.m-modal').children('.m-modal-dialog').animate({
				"margin-top": "30px"
			}, 250);
		},
		_addClickListen: function() {
			var that = this;
			$(".m-modal").find('*').on("click", function(event) {
				event.stopPropagation(); //阻止事件冒泡
			});
			$(".m-modal,.m-modal-close,.m-btn-cancel").on("click", function(event) {
				that.hide();
			});
			$(".m-btn-sure").on("click", function(event) {
				that.fn();
				that.hide();
			});

            //强制保存/就是更新后台数据
            $("#save-1").on("click", function(event) {
                var pid = $('#select-pingwei2').val();
                var proId = $('#select-works2').val();
                var option1 = $('#option31').val();
                var option2 = $('#option32').val();
                var option3 = $('#option33').val();
                var option4 = $('#option34').val();
                var option5 = $('#option35').val();
                var option6 = $('#option36').val();
                var editor = $('#editor2').text();
                var result = addAsUpdate(pid,proId,option1,option2,option3,option4,option5,option6,editor);
                if (result===1){
                    //alert("数据保存成功！");
                    //设置按钮为绿色
                    var myId="input"+proId+7;
                    $("#"+myId).attr("class","btn btn-success");
                    that.fn();
                    that.hide();
                }
            });
		},
		hide: function() {
			var $modal = $('.m-modal');
			$modal.children('.m-modal-dialog').animate({
				"margin-top": "-100%"
			}, 500);
			$modal.fadeOut(100);
		}

	};
	return {
		modal: modal
	}
})();