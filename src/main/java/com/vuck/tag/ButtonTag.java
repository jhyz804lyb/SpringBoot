package com.vuck.tag;
import com.vuck.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ButtonTag extends SimpleTagSupport {
    private String serach;

    private String add;

    private String edit;

    private String delete;

    private String showBtn;

    private String msg;

    private boolean[] showList;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSerach(String serach) {
        this.serach = serach;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setShowBtn(String showBtn) {
        this.showBtn = showBtn;
    }

    public void doTag() throws JspException, IOException {
        init();
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"row\">");
        html.append("  <div class=\"col-md-6\">" + msg + "</div>");
        html.append("  <div class=\"col-md-6 text-right\">" + createBtn() + "</div>");
        html.append(" </div>");
        getJspContext().getOut().println(html.toString());
    }

    public String createBtn() {
        StringBuilder html = new StringBuilder();
        if (showList[0])
            html.append(" <button type=\"button\" class=\"btn search btn-success\" "+createFunction(serach)+">搜索</button>");
        if (showList[1])
            html.append(" <button type=\"button\" class=\"btn add btn-info\""+createFunction(add)+">新增</button>");
        if (showList[2])
            html.append(" <button type=\"button\" class=\"btn edit btn-warning\""+createFunction(edit)+">修改</button>");
        if (showList[3])
            html.append(" <button type=\"button\" class=\"btn delete btn-danger\""+createFunction(delete)+">删除</button>");
        return html.toString();
    }

    public String createFunction(String funcationName) {
        if (StringUtils.isEmpty(funcationName)) return "";
        return "onclick='" + funcationName + "(this)';";
    }

    public void init() {
        if (StringUtils.isEmpty(msg)) {
            msg = "输入查询条件匹配数据";
        }
        if (StringUtils.isEmpty(showBtn)) {
            showList = new boolean[]{true, true, true, true};
        } else {
            String[] split = showBtn.split(",");
            if (split.length != 4) showList = new boolean[]{true, true, true, true};
            else {
                showList = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    if ("1 t T true TRUE".contains(split[i])) {
                        showList[i] = true;
                    } else {
                        showList[i] = false;
                    }
                }
            }
        }
    }
}
