//������
function isJavaInteger(sText) {
	var reg = /^-?\d+$/;
	if(!reg.exec(sText)||sText<0||sText>2147483647){
      return false;
	}
	return true;
}

function CurentTime()
{   
    var now = new Date();    
    var year = now.getFullYear();       //��   
    var month = now.getMonth() + 1;     //��   
    var day = now.getDate();            //��

    var hh = now.getHours(); //ʱ
    var mm = (now.getMinutes()) % 60;  //��
    var ss = now.getSeconds();
     
    var clock = year + '-';   
     
    if(month < 10)   
        clock += '0';   
     
    clock += month + '-';   
     
    if(day < 10)   
        clock += '0';   
         
    clock += day + ' ';   
     
    if(hh < 10)   
        clock += '0';
         
    clock += hh + ':'; 
    
    if (mm < 10) 
    	clock += '0';
    
    clock += mm + ':';
    
    if(ss < 10) {
    	clock += '0';
    }
    
    clock += ss;
    
    return (clock);
}