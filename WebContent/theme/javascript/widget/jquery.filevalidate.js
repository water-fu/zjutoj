(function($) {
	$.fn.fileExtendVal = function(opti) {
		var option = {
			extArr: ['.PNG', '.GIF', '.JPG', '.JPEG']
		};
		var $this = this, this_ = $this[0];
		var filepath = $this.val();
		var extStart = filepath.lastIndexOf(".");
		var ext = filepath.substring(extStart,filepath.length).toUpperCase();

		$.extend(option, opti);
		if(ext != '' && $.inArray(ext, option.extArr) == -1){
			if ($.browser.msie) {
				this_.select();
				document.execCommand('delete');
			}else{
				$this.val('');
			}
			return false;
		}
		return true;
	};

	$.fn.fileSizeVal = function(opti) {
		var $this = this, this_ = $this[0];
		var filepath = $this.val();
		var option = {
			size : 2
		};
		
		if(filepath == '') {
			return true;
		}
		
		$.extend(option, opti);
		if ($.browser.msie) {
			var img = new Image();
			img.src = filepath;
			if(img.fileSize > 0){
				var size = img.fileSize / 1024;
				if(size > option.size){
					document.execCommand("delete");
					return false;
				}
			}
		}
		else {
			var fileSize = this_.files[0].size;
			var size = fileSize / 1024 / 1024;
			if(size > option.size){
				$this.val("");
				return false ;
			}
		}
		return true;
	};
})(jQuery);