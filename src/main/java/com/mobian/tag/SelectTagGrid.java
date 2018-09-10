package com.mobian.tag;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mobian.absx.F;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelectTagGrid extends TagSupport {


    /**
     *
     */
    private static final long serialVersionUID = -2709846727239749266L;
    private String name;
    private String dataType;
    private String value;
    private String disabled;
    private boolean mustSelect;
    private boolean multiple;
    private String onselect;
    private boolean required;
    private String params;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            String _disabled = "false";
            if (!F.empty(disabled)) {
                _disabled = disabled;
            }
            String url = getURI();
            StringBuffer sb = new StringBuffer();
            String gClass = "grid_" + new Date().getTime();
            sb.append("<select name=\"" + name + "\" id=\"" + name + "\" class=\"easyui-combogrid easyui-validatebox " + gClass + "\"  data-options=\"");
            sb.append("width:140,height:29,panelWidth: 400,");
            if (required) {
                sb.append("		required: " + required + ",");
            }
            sb.append("		disabled: " + _disabled + ",");
            sb.append("		idField: 'id',");
            sb.append("		textField: 'text',");
            //接受onselect标签传递过来的函数名,当该函数名有效时会在select的option被选中并且有值改变时会执行该onselect的函数
            if (!F.empty(onselect)) {
                //执行onselect函数体
                sb.append("		onSelect: function(selectIndex){" +
                        "           if(Object.prototype.toString.call(" + onselect + ") === '[object Function]' ){" +
                        "               console.log('" + name + "..selectIndex:' + selectIndex );" +
                        "               var selectValue = $('#" + name + "').combobox('getValue');" +
                        "               console.log('" + name + " selectValue:' +  selectValue);" +
                        "               "+onselect + "(selectValue);" +
                        "           }" +
                        "       },");
            }

            String showName = null;
            if (!F.empty(value)) {
                Map<String, Object> data = getData();
                showName = (String) data.get("text");
                String dataJson = JSON.toJSONString(data, SerializerFeature.UseSingleQuotes, SerializerFeature.QuoteFieldNames);
                sb.append("		mode: 'local',");
                //sb.append("		onShowPanel: function(){alert(1)},");
                sb.append("		onShowPanel: function(e){" +
                        "var select = $('." + gClass + "');" +
                        "if(!select.attr('enableRemote')){" +


                        "select.combogrid({ mode: 'remote',method: 'post',url: '" + url + "'});" +
                        //"setTimeout(function(){select.combogrid('setText','"+showName+"')},1000);" +
                        "select.next().find('input').val('" + showName + "');" +
                        "var grid = select.combogrid('grid');" +
                        "grid.datagrid({'queryParams':{id:'" + value + "',q: '" + showName + "'}});" +
                        "select.attr('enableRemote',true);}}," +
                        "onHidePanel:function(e){" +
                        "var select = $('." + gClass + "');" +
                        "var val = select.combogrid('getValue');" +
                        "if(val == '" + showName + "'){select.combogrid('setValue','" + value + "')}else if(isNaN(val)){select.combogrid('setValue','')}" +
                        "},");
                sb.append("		value:'" + value + "',data:[" + dataJson + "],");
            } else {
                sb.append("		url: '" + url + "',");
                sb.append("		method: 'post',");
                sb.append("		mode: 'remote',");

            }
            sb.append(getHeader());

            sb.append("\">");
            out.print(sb.toString());
            if (!F.empty(showName)) {
                /*out.print("<script type=\"text/javascript\">$(function() {" +
						"$('."+gClass+"').combogrid({keyHandler: {down:function(e){alert(1)}}});" +
						"});</script>");*/
            }

            out.print("</select>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
        //return TagSupport.EVAL_BODY_INCLUDE;//输出标签体内容  
        //return TagSupport.SKIP_BODY;//不输出标签体内容  
    }

    private Map getData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", value);
        if ("userId".equals(dataType)) {
//            FdMemberServiceI fdMemberService = Application.getBean(FdMemberServiceI.class);
//            FdMember member = fdMemberService.getSimple(Integer.parseInt(value));
//            data.put("text", member.getMobile());
//            data.put("parentName", member.getCustomer().getRealName());
        }
// else if ("shopId".equals(dataType)) {
//            MbShopServiceI mbShopService = Application.getBean(MbShopServiceI.class);
//            MbShop mbShop = mbShopService.getFromCache(Integer.parseInt(value));
//            if (mbShop != null) {
//                data.put("text", mbShop.getName());
//                data.put("parentName", mbShop.getRegionPath());
//            }
//        } else if ("warehouseId".equals(dataType)) {
//            MbWarehouseServiceI mbWarehouseService = Application.getBean(MbWarehouseServiceI.class);
//            MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(Integer.parseInt(value));
//            if (mbWarehouse != null) {
//                data.put("text", mbWarehouse.getName());
//                data.put("parentName", mbWarehouse.getRegionPath());
//            }
//        } else if ("supplierId".equals(dataType)) {
//
//            MbSupplierServiceI mbSupplierService = Application.getBean(MbSupplierServiceI.class);
//            MbSupplier mbSupplier = mbSupplierService.getFromCache(Integer.parseInt(value));
//            if (mbSupplier != null) {
//                data.put("text", mbSupplier.getName());
//                data.put("parentName", mbSupplier.getRegionPath());
//            }
//        } else if ("mbUserId".equals(dataType)) {
//            MbUserServiceI mbUserService = Application.getBean(MbUserServiceI.class);
//            MbUser mbUser = mbUserService.getFromCache(Integer.parseInt(value));
//            if (mbUser != null) {
//                data.put("text", mbUser.getUserName());
//                data.put("parentName", mbUser.getNickName());
//            }
//        } else if ("couponsId".equals(dataType)) {
//            MbCouponsServiceI mbCouponsService =Application.getBean(MbCouponsServiceI.class);
//            MbCoupons mbCoupons = mbCouponsService.getFromCache(Integer.parseInt(value));
//            if (mbCoupons != null) {
//                data.put("text", mbCoupons.getName());
//                data.put("parentName", mbCoupons.getCode());
//                data.put("pid", mbCoupons.getPrice());
//            }
//        }
//        else {
//
//            //行政区划
//            DiveRegionServiceI diveRegionService = Application.getBean(DiveRegionServiceI.class);
//            DiveRegion region = diveRegionService.getFromCache(value);
//            if (region != null) {
//                data.put("text", region.getRegionNameZh());
//                region = diveRegionService.getFromCache(region.getRegionParentId());
//                if (region != null)
//                    data.put("parentName", region.getRegionNameZh());
//            }
//        }
        return data;
    }

    private String getURI() {
        String path = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
        String url = "";
        if ("userId".equals(dataType)) {
            url = path + "/fdMemberController/selectQuery";
            if(!F.empty(params)) url += "?params=" + params.replace("{", "%7b").replace("}", "%7d").replaceAll("\"", "%22");
        }
        return url;
    }

    private String getHeader() {
        StringBuffer sb = new StringBuffer();
        if ("userId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'手机号',width:180},");
            sb.append("{field:'parentName',title:'姓名',width:180}");
            sb.append("]]");
        } else if ("couponsId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100,hidden:true},");
            sb.append("{field:'text',title:'券票名称',width:150},");
            sb.append("{field:'parentName',title:'券编码',width:150},");
            sb.append("{field:'pid',title:'价格',width:80,align:'right',formatter:function(value){ return $.formatMoney(value);}}");
            sb.append("]]");
        }
        else {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'名称',width:180},");
            sb.append("{field:'parentName',title:'上级',width:180}");
            sb.append("]]");
        }
        return sb.toString();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMustSelect() {
        return mustSelect;
    }

    public void setMustSelect(boolean mustSelect) {
        this.mustSelect = mustSelect;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getOnselect() {
        return onselect;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }
}
