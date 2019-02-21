/**
 * 转换undefined为空字符串
 * @param obj
 * @returns
 */
function convertUndefinedToEmptyString(obj){
	if(obj==undefined){
		return '';
	}
	return obj;
}
/**
 * 格式化json日期
 * @param format 支持yyyy-MM-dd, yyyy-MM, yyyy/MM/dd, yyyy/MM/dd HH:mm:ss, yyyy-MM-dd HH:mm:ss格式
 * @param jsondate json日期
 * @returns
 */
function formatJsonTime(format,jsondate){
	var year = jsondate.year+1900;
	var month = jsondate.month+1;
	var day = jsondate.date;
	month=(month>9)?(""+month):("0"+month);  //如果得到的数字小于9要在前面加'0'
	day=(day>9)?(""+day):("0"+day);
	var hour=jsondate.hours;
	var minute=jsondate.minutes;
	var seconds=jsondate.seconds;
	hour=(hour>9)?(""+hour):("0"+hour);
	minute=(minute>9)?(""+minute):("0"+minute);
	seconds=(seconds>9)?(""+seconds):("0"+seconds);
	if(format=='yyyy-MM-dd'){
		return year+"-"+month+"-"+day;
	}else if(format=='yyyy-MM'){
		return year+"-"+month;
	}else if(format=='yyyy/MM/dd'){
		return year+"/"+month+"/"+day;
	}else if(format=='yyyy/MM/dd HH:mm:ss'){
		return year+"/"+month+"/"+day+" "+hour+":"+minute+":"+seconds;
	}else if(format=='yyyy-MM-dd HH:mm:ss'){
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
	}else{
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
	}
}
/**
 * 搜索预约记录列表
 * @returns
 */
function searchOrderRecordList(){
  var html = "";
  $.post('/admin/order/list',{
	  startTime:$("#startTime").val(),
	  endTime:$("#endTime").val(),
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim(),
	  patientName:$("#patientName").val().trim(),
	  cardNo:$("#cardNo").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr onclick='javascript:detail(&quot;order&quot;,"+l.orderId+")'>";
			  html += "<td>"+l.name+"</td>";
			  html += "<td>"+l.doctorName+"</td>";
			  html += "<td>"+l.deptName+"</td>";
			  html += "<td>"+l.clinicDate+"</td>";
			  if(l.orderStatus==4){
				  html += "<td>成功</td>";
			  }else if(l.orderStatus==6){
				  html += "<td>已退款</td>";
			  }
			  html += "<td class='text-primary'>"+formatJsonTime('yyyy-MM-dd HH:mm:ss',l.createTime)+"</td>";
			  html += "</tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 跳转至预约记录详情页面
 * @param orderId
 * @returns
 */
function detail(type,orderId){
	if(type=='order'){
		window.location.href = "/admin/order/detail/"+orderId;
	}else if(type=='register'){
		window.location.href = "/admin/register/detail/"+orderId;
	}
}
/**
 * 搜索挂号记录列表
 * @returns
 */
function searchRegisterRecordList(){
  var html = "";
  $.post('/admin/register/list',{
	  startTime:$("#startTime").val(),
	  endTime:$("#endTime").val(),
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim(),
	  patientName:$("#patientName").val().trim(),
	  cardNo:$("#cardNo").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr onclick='javascript:detail(&quot;register&quot;,"+l.orderCurId+")'>";
			  html += "<td>"+l.name+"</td>";
			  html += "<td>"+l.doctorName+"</td>";
			  html += "<td>"+l.deptName+"</td>";
			  html += "<td>"+l.scheTime+"</td>";
			  if(l.orderStatus==1){
				  html += "<td>成功</td>";
			  }else{
				  html += "<td>未知</td>";
			  }
			  html += "<td class='text-primary'>"+formatJsonTime('yyyy-MM-dd HH:mm:ss',l.createTime)+"</td>";
			  html += "</tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 搜索门诊缴费记录列表
 * @returns
 */
function searchClinicRecordList(){
  var html = "";
  $.post('/admin/clinic/list',{
	  startTime:$("#startTime").val(),
	  endTime:$("#endTime").val(),
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim(),
	  patientName:$("#patientName").val().trim(),
	  cardNo:$("#cardNo").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr>";
			  html += "<td>"+l.patientName+"</td>";
			  html += "<td>"+l.card+"</td>";
			  html += "<td>"+l.registNo+"</td>";
			  html += "<td>"+l.sumFee+"&nbsp;元</td>";
			  if(l.status==1){
				  html += "<td>支付成功，未通知his</td>";
			  }else if(l.status==3){
				  html += "<td>缴费成功</td>";
			  }else if(l.status==5){
				  html += "<td>退费成功</td>";
			  }else{
				  html += "<td>未知状态</td>";
			  }
			  html += "<td class='text-primary'>"+formatJsonTime('yyyy-MM-dd HH:mm:ss',l.createTime)+"</td>";
			  html += "</tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 搜索住院预交记录列表
 * @returns
 */
function searchInhospitalRecordList(){
  var html = "";
  $.post('/admin/inhospital/list',{
	  startTime:$("#startTime").val(),
	  endTime:$("#endTime").val(),
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim(),
	  patientName:$("#patientName").val().trim(),
	  cardNo:$("#cardNo").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr>";
			  html += "<td>"+l.patientName+"</td>";
			  html += "<td>"+l.admissionNo+"</td>";
			  html += "<td>"+l.idCard+"</td>";
			  html += "<td>"+l.advancedFund+"&nbsp;元</td>";
			  if(l.tmp2==1){
				  html += "<td>缴费成功</td>";
			  }else if(l.tmp2==2){
				  html += "<td>退费成功</td>";
			  }else if(l.tmp2==2){
				  html += "<td>退费失败</td>";
			  }else{
				  html += "<td>未处理</td>";
			  }
			  html += "<td class='text-primary'>"+formatJsonTime('yyyy-MM-dd HH:mm:ss',l.createTime)+"</td>";
			  html += "</tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 搜索退费记录列表
 * @returns
 */
function searchRefundList(){
  var html = "";
  $.post('/admin/refund/list',{
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim(),
	  tradeNo:$("#tradeNo").val().trim(),
	  flowId:$("#flowId").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr>";
			  html += "<td>"+l.tmp1+"</td>";
			  html += "<td>"+l.tradeNo+"</td>";
			  html += "<td>"+l.flowId+"</td>";
			  html += "<td>"+l.refundFee+"&nbsp;元</td>";
			  if(l.status==1){
				  html += "<td>退费成功</td>";
			  }else if(l.status==2){
				  html += "<td>退费失败</td>";
			  }else if(l.status==3){
				  html += "<td>退费中</td>";
			  }else{
				  html += "<td>未处理</td>";
			  }
			  html += "<td class='text-primary'>"+ l.payTime +"</td>";
			  html += "<td class='text-primary'>"+formatJsonTime('yyyy-MM-dd HH:mm:ss',l.createTime)+"</td>";
			  html += "</tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 搜索短信配置列表
 * @returns
 */
function searchMsgConfigList(){
  var html = "";
  $.post('/admin/msg',{
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr id='configLine"+l.id+"'>";
			  html += "<td>"+l.note+"</td>";
			  html += "<td>"+l.configValue+"</td>";
			  html += "<td><a href='javascript:msgConfigEdit("+l.id+")' class='btn btn-primary'>编辑</a>";
			  html += "</td></tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 编辑短信配置值
 * @param id
 * @returns
 */
function msgConfigEdit(id){
	  var nodes = $("#configLine"+id).children();
	  var node1 = nodes[1];
	  var content = node1.innerHTML;
	  var html1 = "";
	  html1 += "<input id='configContent"+id+"' value='"+content+"' style='width:90%'/>";
	  var node2 = nodes[2];
	  var html2 = "";
	  html2 += "<a href='javascript:msgConfigSave("+id+")' class='btn btn-primary'>保存 </a>";
	  node1.innerHTML = html1;
	  node2.innerHTML = html2;
	  sessionStorage.setItem('msgOldContent'+id,content);
}
/**
 * 更新短信配置值
 * @param id
 * @returns
 */
function msgConfigSave(id){
	var content = $("#configContent"+id).val();
	var nodes = $("#configLine"+id).children();
	var node1 = nodes[1];
	var oldContent = sessionStorage.getItem('msgOldContent'+id);
	if(content.trim()==''||content==oldContent){
		// 和之前内容无变化，不进行操作
	}else{
		/**
		 * 传输新配置数据至后台执行更新操作
		 */
		$.post('/admin/msg/edit',{
			id:id,
			value:content
		},function(msg){
			var data = eval("("+msg+")");
			alert(data.ret_info);
		});
	}
	node1.innerHTML = content;
	var node2 = nodes[2];
	var html2 = "";
	html2 += "<a href='javascript:msgConfigEdit("+id+")' class='btn btn-primary'>编辑</a>";
	node2.innerHTML = html2;
}


/**
 * 搜索功能配置列表
 * @returns
 */
function searchConfigList(){
  var html = "";
  $.post('/admin/config',{
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr id='configLine"+l.id+"'>";
			  html += "<td>"+l.configName+"</td>";
			  html += "<td>"+l.configValue+"</td>";
			  html += "<td>"+l.note+"</td>";
			  html += "<td><a href='javascript:configEdit("+l.id+")' class='btn btn-primary'>编辑</a>";
			  html += "</td></tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 编辑配置值
 * @param id
 * @returns
 */
function configEdit(id){
	  var nodes = $("#configLine"+id).children();
	  var node1 = nodes[1];
	  var node2 = nodes[2];
	  var content = node1.innerHTML;
	  var note = node2.innerHTML;
	  var html1 = "";
	  html1 += "<input id='configContent"+id+"' value='"+content+"' style='width:85%'/>";
	  var html2 = "";
	  html2 += "<input id='configNote"+id+"' value='"+note+"' style='width:85%'/>";
	  node2.innerHTML = html2;
	  var node3 = nodes[3];
	  var html3 = "";
	  html3 += "<a href='javascript:configSave("+id+")' class='btn btn-primary'>保存 </a>";
	  node1.innerHTML = html1;
	  node3.innerHTML = html3;
	  sessionStorage.setItem('configOldContent'+id,content);
	  sessionStorage.setItem('configOldNote'+id,note);
}
/**
 * 更新配置值
 * @param id
 * @returns
 */
function configSave(id){
	var content = $("#configContent"+id).val();
	var note = $("#configNote"+id).val();
	var nodes = $("#configLine"+id).children();
	var node1 = nodes[1];
	var node2 = nodes[2];
	var oldContent = sessionStorage.getItem('configOldContent'+id);
	var oldNote = sessionStorage.getItem('configOldNote'+id);
	
	if((content.trim()==''||content==oldContent)&&(note.trim()==''||note==oldNote)){
		// 和之前内容无变化，不进行操作
	}else{
		/**
		 * 传输新配置数据至后台执行更新操作
		 */
		$.post('/admin/config/edit',{
			id:id,
			value:content,
			note:note
		},function(msg){
			var data = eval("("+msg+")");
			alert(data.ret_info);
		});
	}
	node1.innerHTML = content;
	node2.innerHTML = note;
	
	var node3 = nodes[3];
	var html3 = "";
	html3 += "<a href='javascript:configEdit("+id+")' class='btn btn-primary'>编辑</a>";
	node3.innerHTML = html3;
}

/**
 * 搜索功能配置列表
 * @returns
 */
function searchUserList(){
  var html = "";
  $.post('/admin/user',{
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim()
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.userList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr id='userLine"+l.userinfo.id+"'>";
			  html += "<td style='width:15%;'>"+l.userinfo.username+"</td>";
			  html += "<td style='width:15%;'>"+l.userinfo.realname+"</td>";
			  html += "<td style='width:15%;'>"+l.userinfo.mobile+"</td>";
			  var roles_str = "";
			  if(l.roles.length>0){ //遍历用户角色
				  for(var j = 0; j<l.roles.length; j++){
					  var r = l.roles[j];
					  roles_str += r.roleName;
					  if(j < l.roles.length && j != l.roles.length-1){
						  roles_str += "&nbsp;,&nbsp;";
					  }
				  }
			  }else{
				  roles_str = "未分配";
			  }
			  html += "<td style='width:30%;'>"+roles_str+"</td>";
			  html += "<td style='width:15%;'>"+l.userinfo.version+"</td>";
			  html += "<td style='width:10%;'><a href='./user/edit/"+l.userinfo.username+"' class='btn btn-primary'>编辑</a>";
			  html += "</td></tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}




/**
 * 获取反馈列表
 * @returns
 */
function searchFeedbackList(){
var html = "";
  $.post('/admin/feedback',{
	  pageIndex:$("#pageIndex").val().trim(),
	  pageSize:$("#pageSize").val().trim(),
  },function(msg){
	  var data = eval("("+msg+")");
	  var list = data.recordList;
	  var pageConfig = data.pageConfig;
	  if(list.length==0){
		  html += "未查到相关数据";
	  }else{
		  for(var i = 0;i < list.length; i++) {
			  var l = list[i];
			  html += "<tr onclick='javascript:feedbackDetail("+l.id+")'>";
			  html += "<td>"+l.clinicDate+"</td>";
			  html += "<td>"+l.clinicDept+"</td>";
			  html += "<td>"+l.clinicDoct+"</td>";
			  html += "<td>"+l.clinicEvaluation+"</td>";
			  html += "<td class='text-primary'>"+formatJsonTime('yyyy-MM-dd HH:mm:ss',l.createTime)+"</td>";
			  if(l.tmp1==1){
				  html += "<td>已读</td>";
			  }else{
				  html += "<td style='color:gray;'>未读</td>";
			  }
			  html += "</tr>";
		  }
	  }
	  $("#table_body").html(html);
	  $('#pagination').jqPaginator('option', {
		  totalPages:(pageConfig.totalPages<1)?(1):(pageConfig.totalPages)
	});
  });
}
/**
 * 跳转至反馈详细界面
 * @param id
 * @returns
 */
function feedbackDetail(id){
	window.location.href = "/admin/feedback/detail/"+id;
}