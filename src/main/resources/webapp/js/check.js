	//验证值是否为空
	function isEmpty(value){
		if($.trim(value)=="" || $.trim(value)==null){
			return true;
		}
		return false;
	}
	
	//验证值是否相同
	function isSame(value1,value2){
		if($.trim(value1)==$.trim(value2)){
			return true;
		}
		return false;
	}
	
	//判断手机格式
	function isPhone(phoneNO){
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		if($.trim(phoneNO)==""||$.trim(phoneNO).length!=11||!myreg.test(phoneNO)){
			return false;
		}
		return true;
	}
	
	//判断邮箱格式
	function isEmail(email){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(email));
	}
	
	//验证邮政编码  
	function isPostcode(postcode) {  
	    if ( postcode == ""||! /^[0-9]{6}$/.test(postcode)) {  
	        return false;  
	    } 
	    return true;  
	}  
	
	//判断电话号码
	function isMobile(mobileNO){
		if(mobileNO==""||! /^d{3,4}-?d{7,9}$/.test(mobileNO)){
			return false;
		}
		return true;
	}
	function endsWith (suffix) {
		 return  String.prototype.endsWith(suffix, this.length - suffix.length) !== -1;
	}
	
	
	
	
	
	//验证身份证号格式
	function isIdCard(person_id) {
        var person_id = person_id;
            //身份证的地区代码对照  
            var aCity = {
                11 : "北京",
                12 : "天津",
                13 : "河北",
                14 : "山西",
                15 : "内蒙古",
                21 : "辽宁",
                22 : "吉林",
                23 : "黑龙江",
                31 : "上海",
                32 : "江苏",
                33 : "浙江",
                34 : "安徽",
                35 : "福建",
                36 : "江西",
                37 : "山东",
                41 : "河南",
                42 : "湖北",
                43 : "湖南",
                44 : "广东",
                45 : "广西",
                46 : "海南",
                50 : "重庆",
                51 : "四川",
                52 : "贵州",
                53 : "云南",
                54 : "西藏",
                61 : "陕西",
                62 : "甘肃",
                63 : "青海",
                64 : "宁夏",
                65 : "新疆",
                71 : "台湾",
                81 : "香港",
                82 : "澳门",
                91 : "国外"
            };
            //合法性验证  
            var sum = 0;
            //出生日期  
            var birthday;
            //验证长度与格式规范性的正则  
            var pattern = new RegExp(
                    /(^\d{15}$)|(^\d{17}(\d|x|X)$)/i);
            if (pattern.exec(person_id)) {
                //验证身份证的合法性的正则  
                pattern = new RegExp(
                        /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/);
                if (pattern.exec(person_id)) {
                    //获取15位证件号中的出生日期并转位正常日期       
                    birthday = "19" + person_id.substring(6, 8)
                            + "-" + person_id.substring(8, 10)
                            + "-" + person_id.substring(10, 12);
                } else {
                    person_id = person_id.replace(/x|X$/i, "a");
                    //获取18位证件号中的出生日期  
                    birthday = person_id.substring(6, 10) + "-"
                            + person_id.substring(10, 12) + "-"
                            + person_id.substring(12, 14);

                    //校验18位身份证号码的合法性  
                    for ( var i = 17; i >= 0; i--) {
                        sum += (Math.pow(2, i) % 11)
                                * parseInt(
                                        person_id.charAt(17 - i),
                                        11);
                    }
                    if (sum % 11 != 1) {
                        return false;
                    }
                }
                //检测证件地区的合法性                                  
                if (aCity[parseInt(person_id.substring(0, 2))] == null) {
                    return false;
                }
                var dateStr = new Date(birthday.replace(/-/g, "/"));
                if (birthday != (dateStr.getFullYear() + "-"
                        + Append_zore(dateStr.getMonth() + 1) + "-" + Append_zore(dateStr
                        .getDate()))) {
                    return false;
                }
               
            } else {
                return false;
            }
            return true;
    }
	 function Append_zore(temp) {
         if (temp < 10) {
             return "0" + temp;
         } else {
             return temp;
         }
     }
	//上传图片并预览
	jQuery.fn.extend({
	    uploadPreview: function (opts) {
	        var _self = this,
	            _this = $(this);
	        opts = jQuery.extend({
	            Img: opts.Img,
	            Width: 100,
	            Height: 100,
	            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
	            Callback: function () {}
	        }, opts || {});
	        _self.getObjectURL = function (file) {
	            var url = null;
	            if (window.createObjectURL != undefined) {
	                url = window.createObjectURL(file)
	            } else if (window.URL != undefined) {
	                url = window.URL.createObjectURL(file)
	            } else if (window.webkitURL != undefined) {
	                url = window.webkitURL.createObjectURL(file)
	            }
	            return url
	        };
	        _this.change(function () {
	            if (this.value) {
	                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
	                    layer.msg("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
	                    this.value = "";
	                    return false
	                }
	                //if ($.browser.msie) {
	                    try {
	                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
	                        $("#" + opts.Img).attr('style','');
	                    } catch (e) {
	                        var src = "";
	                        var obj = $("#" + opts.Img);
	                        var div = obj.parent("div")[0];
	                        _self.select();
	                        if (top != self) {
	                            window.parent.document.body.focus()
	                        } else {
	                            _self.blur()
	                        }
	                        src = document.selection.createRange().text;
	                        document.selection.empty();
	                        obj.hide();
	                        obj.parent("div").css({
	                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
	                            'width': opts.Width + 'px',
	                            'height': opts.Height + 'px'
	                        });
	                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
	                    }
	                } else {
	                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
	                }
	                opts.Callback()
	           // }
	        })
	    }
	});
	
	/**       
	 * 对Date的扩展，将 Date 转化为指定格式的String       
	 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符       
	 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)       
	 * eg:       
	 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423       
	 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04       
	 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04       
	 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04       
	 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18       
	 */          
	Date.prototype.pattern=function(fmt) {           
	    var o = {           
	    "M+" : this.getMonth()+1, //月份           
	    "d+" : this.getDate(), //日           
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时           
	    "H+" : this.getHours(), //小时           
	    "m+" : this.getMinutes(), //分           
	    "s+" : this.getSeconds(), //秒           
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度           
	    "S" : this.getMilliseconds() //毫秒           
	    };           
	    var week = {           
	    "0" : "/u65e5",           
	    "1" : "/u4e00",           
	    "2" : "/u4e8c",           
	    "3" : "/u4e09",           
	    "4" : "/u56db",           
	    "5" : "/u4e94",           
	    "6" : "/u516d"          
	    };           
	    if(/(y+)/.test(fmt)){           
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
	    }           
	    if(/(E+)/.test(fmt)){           
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);           
	    }           
	    for(var k in o){           
	        if(new RegExp("("+ k +")").test(fmt)){           
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
	        }           
	    }           
	    return fmt;           
	}         