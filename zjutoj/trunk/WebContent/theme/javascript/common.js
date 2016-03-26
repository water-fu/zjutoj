var flag = true;
$().ready(function() {
	$('body').on('click', function() {
		if($('.nav_down:visible').length > 0) {
			if(flag) {
				$('.nav_down').hide();
			}else {
				flag = true;
			}
		}
	});
	
	$('.nav_select').each(function(i) {
		$(this).on('click', function() {
			var className = $(this).attr('val');
			if($('.'+className+':hidden').length > 0) {
				flag = false;
				//先全部隐藏
				$('.nav_down').hide();
				
				//展示下拉框
				$('.'+className).show();
				
				//设置宽度
				$('.'+className).css('width', $(this).parent().width()+30);
			}else {
				$('.'+className).hide();
			}
		});
	});
	$('.nav_down').on('click', function() {
		flag = false;
	});
	$('.nav_down').hide();
});
var inter = null;
function showTip(el, content) {
	var obj = null;
	if(typeof el === 'string') {
		obj = $('#'+el);
	}else if(typeof el === 'object') {
		obj = el;
	}
	obj.poshytip({
		className: 'tip-yellowsimple',
		content: content,
		showOn: 'none',
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 0,
		offsetY: 5,
		hideTimeout:0,
		showTimeout:0
	});
	obj.poshytip('show');
	
	if(inter == null) {
		inter = setInterval(hideTip, 5000);
	}
}

function hideTip() {
	clearInterval(inter);
	inter = null;
	$(".tip-yellowsimple").remove();
}

function errorTip(el, content, isHide) {
	$('#'+el).poshytip({
		className: 'tip-yellowsimple',
		content: content,
		showOn: 'none',
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 0,
		offsetY: 5,
		hideTimeout:0,
		showTimeout:0
	});
	$("#" + el).poshytip('show');
	
	if(inter == null && isHide) {
		inter = setInterval(hideTip, 5000);
	}
}

function ajaxFormSubmit(formName, successFun) {
	var options = {
    	success: successFun
    };
    $('#'+formName).submit(function() {
    	$(this).ajaxSubmit(options);
		return false;
	});
    
    $('#'+formName).submit();
}

function showMask() {
	var div = $('<div>');
	div.attr('id', 'mask');
	div.css('width', $(document).width());
	div.css('height', $(document).height());
	var scollTop = $(window).scrollTop();
	div.css('background-position', $(window).width()/2+'px '+($(window).height()/2+scollTop)+'px');
	$('body').append(div);
}

function hideMask() {
	$('#mask').remove();
}