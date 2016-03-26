(function() {
	$.fn.select = function(opti) {
		var _this = $(this);
		var option = {
			targetId: 'targetId'
		};
		$.extend(option, opti);
		var typeFlag = true;
        $('body').on('click', function() {
        	if($('#'+option.targetId+':visible').length > 0) {
	        	if(typeFlag) {
		        	var downType = $('#'+option.targetId);
	    	    	downType.hide();
	        	}else {
	        		typeFlag = true;
	        	}
        	}
        });
        
        _this.on('click', function() {
        	var downType = $('#'+option.targetId), _position = _this.position();
        	downType.css({'top': _position.top+_this.height()+3, 'left': _position.left});
        	downType.css({'width': _this.width()+6, 'position': 'absolute'});
        	$('#'+option.targetId+' li:odd').css('background-color', '#E8F2FE');
        	downType.show();
        	typeFlag = false;
        });
        
        $('#'+option.targetId+' li').each(function(i) {
        	$(this).on('click', function() {
        		_this.val($(this).html());
	        	typeFlag =  true;
	        });
        });
        
        _this.on('keyup', function(e) {
        	var val = _this.val();
        	if(e.keyCode == 13 || val == '') {
        		$('#'+option.targetId+' li').hide();
        		$('#'+option.targetId+' li').each(function(i) {
        			var html = $(this).html();
        			if(val == '' || html.indexOf(val) > -1) {
        				$(this).show();
        			}
        		});
        	}
        });
	};
})(jQuery);