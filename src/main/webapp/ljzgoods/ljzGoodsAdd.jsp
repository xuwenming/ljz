<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzGoods" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
    var imageStatus = true;
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzGoodsController/add',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');

                if (imageStatus) {
                    var completeLength = $('#imageQueue span[class="data complete"]').length;
                    if(completeLength == 0) {
                        alert('请上传商品图片');
                        isValid = false;
                    } else if(completeLength <= 9) {
                        var imageFileNames = '';
                        $('#imageQueue span[class="fileName"][fileName != ""]').each(function() {
                            var fn = $(this).attr('fileName');
                            if(imageFileNames != '') imageFileNames += ';';
                            imageFileNames += fn;
                        });
                        $('#imageFileNames').val(imageFileNames);
                    }
                } else  {
                    alert("当前文件仍然处于数据传输中，请稍后上传");
                    isValid = false;
                }

				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

        $("#imageFile").uploadify({
            'debug': false, //开启调试
            'auto': true, //是否自动上传
            'swf': '${pageContext.request.contextPath}/jslib/uploadify/uploadify.swf',  //引入uploadify.swf
            'uploader': '${pageContext.request.contextPath}/ljzGoodsController/upload',// 请求路径
            'queueID' : 'imageQueue',//队列id,用来展示上传进度的
            'width'     : '80',  //按钮宽度
            'height'    : '26',  //按钮高度
            'queueSizeLimit' : 9,  //同时上传文件的个数
            'fileTypeDesc':'支持的图片格式：gif、jpg、jpeg、png、bmp',
            'fileTypeExts'   : '*.gif;*.jpg;*.jpeg;*.png;*.bmp', // 控制可上传文件的扩展名
            'multi'          : true,  //允许多文件上传
            'preventCaching' : true,  // 设置随机参数，防止缓存
            'buttonText'     : '上传商品图片',//按钮上的文字
            'fileSizeLimit' : '5MB', //设置单个文件大小限制
            'fileObjName' : 'imageFile',  //<input type="file"/>的name
            'method' : 'post',
            'removeCompleted' : false,//上传完成后自动删除队列
            'successTimeout':99999,
            'onUploadStart' : function() {
                imageStatus = false;
            },
            'onFallback':function(){
                alert("您未安装FLASH控件，无法上传文件！请安装FLASH控件后再试。");
            },
            'onUploadSuccess' : function(file, data, response){ // 单个文件上传成功触发
                var data = jQuery.parseJSON(data);
                if (data.success) {
                    //data就是action中返回来的数据
                    $("#" + file.id).find('span[class="fileName"]').attr({'fileName' : data.obj});
                    $("#" + file.id).find('span[class="data"]').addClass("complete").html(' - 上传成功');
                    $("#" + file.id).find('div[class="uploadify-progress"]').remove();
                    // $('#imageFile').uploadify('cancel', 'SWFUpload_0_1')
                    $("#" + file.id).find('div[class="cancel"]').html('<a href="javascript:cancel(\''+file.id+'\')">X</a>');
                    $('.img-preview').append('<img src="'+data.obj+'" fileid="'+file.id+'" width="150"/>');
                } else {
                    $("#" + file.id).find('div[class="uploadify-progress"]').html("<font style='color: red;'>上传失败</font>");
                }
            },
            'onQueueComplete' : function(queueData) {
                imageStatus = true;
            }
        });
	});

	function cancel(fileid) {
        $('#imageFile').uploadify('cancel', fileid);
        $('.img-preview').find('img[fileid="'+fileid+'"]').remove();
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TljzGoods.ALIAS_TITLE%></th>
					<td colspan="3">
						<input class="easyui-validatebox span2" data-options="required:true" maxlength="100" name="title" type="text" style="width: 510px;"/>
					</td>
				</tr>
				<tr>
					<th width="10%">所属店铺</th>
					<td width="40%">
						<jb:selectSql dataType="SQL001" name="shopId" required="true"></jb:selectSql>
					</td>
					<th width="10%">状态</th>
					<td width="40%">
						<select name="isPutAway" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0">上架</option>
							<option value="1">下架</option>
						</select>
					</td>
				</tr>
				<%--<tr>--%>
					<%--<th>封面icon</th>--%>
					<%--<td colspan="3">--%>
						<%--<input type="file" id="iconFile" name="iconFile">--%>
					<%--</td>--%>
				<%--</tr>--%>
				<tr>
					<th><%=TljzGoods.ALIAS_PRICE%></th>	
					<td>
						<input class="easyui-validatebox span2" data-options="required:true" maxlength="10" name="price" type="text"/>
					</td>
					<th><%=TljzGoods.ALIAS_FREIGHT%></th>
					<td>
						<input class="span2" name="freight" maxlength="10" value="0" type="text"/>
					</td>

				</tr>
				<tr>
					<th>库存数量</th>
					<td>
						<input name="quantity" value="100"
							   class="easyui-numberspinner" style="width: 140px; height: 29px;"
							   required="required" data-options="editable:true" maxlength="10"/>
					</td>
					<th><%=TljzGoods.ALIAS_LIMIT_NUMBER%></th>	
					<td>
						<input name="limitNumber" value="0"
							   class="easyui-numberspinner" style="width: 140px; height: 29px;"
							   required="required" data-options="editable:true" maxlength="10"/>
					</td>
				</tr>	
				<tr>
					<th><%=TljzGoods.ALIAS_SHARE_AMOUNT%></th>	
					<td colspan="3">
						<input class="easyui-validatebox span2" maxlength="10" value="0" data-options="required:true" name="shareAmount" type="text"/>
					</td>
				</tr>
				<tr>
					<th><%=TljzGoods.ALIAS_PRIZE_PRE%></th>
					<td>
						<input name="prizePre" value="0"
							   class="easyui-numberspinner" style="width: 140px; height: 29px;"
							   required="required" data-options="editable:true" maxlength="10" />
					</td>
					<th><%=TljzGoods.ALIAS_PRIZE_NUMBER%></th>
					<td>
						<input name="prizeNumber" value="0"
							   class="easyui-numberspinner" style="width: 140px; height: 29px;"
							   required="required" data-options="editable:true" maxlength="10" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="hidden" name="imageUrl" id="imageFileNames" />
						<input type="file" id="imageFile" name="imageFile" /><font color="red">第一张图片默认为商品封面图片，支持gif、jpg、jpeg、png、bmp等格式！</font>
						<div id="imageQueue" style="margin-top:5px;"></div>
					</td>
				</tr>
				<tr>
					<td colspan="4" height="155" class="img-preview">
						<%--<img src="http://img.ethealth.cn/ljz/goods/2018/9/10/1536574260164.png" fileid="SWFUpload_0_1" width="160"/>--%>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>