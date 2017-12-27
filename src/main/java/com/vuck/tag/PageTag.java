package com.vuck.tag;
import com.vuck.common.Cost;
import com.vuck.common.PageInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PageTag extends SimpleTagSupport {

    private Object data;

    private String requestType;

    private String formId;

    public void setData(Object data) {
        this.data = data;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void init() {
        if (data == null) {
            data = this.getJspContext().findAttribute(Cost.PAGE_INFO);
        }
        if (requestType == null) {
            requestType = "get";
        }
    }

    public void doTag() throws JspException, IOException {
        init();
        if (data == null || !(data instanceof PageInfo)) return;//如果没有分页信息
/*        if ("post".toUpperCase().equals(requestType.toUpperCase()))
            return;//如果post请求里面没有表单id*/
        if ("get".toUpperCase().equals(requestType.toUpperCase()) ||"post".toUpperCase().equals(requestType.toUpperCase()) ) {
            StringBuilder html = new StringBuilder();
            html.append("<nav class='text-center' aria-label=\"...\">\n" +
                    "         <ul class=\"pagination\">");
            html.append(createHtml((PageInfo) data));
            html.append("</ul>\n </nav>");
            getJspContext().getOut().println(html.toString());
        }
    }

    public String createHtml(PageInfo pageInfo) {
        StringBuilder html = new StringBuilder();
        int pageNo = pageInfo.getPageNo();
        int maxCount = pageInfo.getMaxPage();
        if (pageNo == 1 || pageNo == 0)
            html.append("<li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
        else
            html.append("<li><a href=\"" + createUrl(pageNo - 1, pageInfo.getUrl()) + "\" "+createFunction(pageNo - 1,pageInfo)+" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
        int count = 1;
        for (int i = 1; i <= maxCount; i++) {
            if (i + 5 < pageNo) continue;
            if (count > 10) break;
            count++;
            if (i == pageNo)
                html.append("<li class=\"active\"><a class='disabled' href=\"#\">" + i + " <span class=\"sr-only\">(current)</span></a></li>");
            else {
                html.append("<li><a  href=\"" + createUrl(i, pageInfo.getUrl()) + "\" "+createFunction(i,pageInfo)+" >" + i + " <span class=\"sr-only\">(current)</span></a></li>");
            }
        }
        if (pageNo == maxCount)
            html.append("<li class='disabled'><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li>");
        else
            html.append("<li><a href=\"" + createUrl(pageNo + 1, pageInfo.getUrl()) + "\" " + createFunction(pageNo + 1,pageInfo) + " aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li>");
        return html.toString();
    }

    private String createFunction(Integer pageNo, PageInfo pageInfo) {
        if ("get".toUpperCase().equals(requestType.toUpperCase())) {
            return "";
        } else {
            StringBuilder html = new StringBuilder();
            String url =pageInfo.getUrl();
            html.append("onclick =openPage('");
            if (url.contains("pageNo")) {
                int index = url.indexOf("pageNo");
                String temp = url.substring(index);
                int fastLen = temp.indexOf("&");
                if (fastLen == -1) {
                    url= url.substring(0, index) + "pageNo=" + pageNo;
                } else {
                    url= url.substring(0, index) + "pageNo=" + pageNo + temp.substring(fastLen);
                }
            } else {
                if (url.contains("?")) {
                    url = url.replace("?", "?pageNo=" + pageNo + "&");
                } else {
                    url =  url + "?pageNo=" + pageNo;
                }
            }
            html.append(url);
            html.append("')");
            return html.toString();
        }
    }

    public String createUrl(int pageNo, String url) {
        if ("post".toUpperCase().equals(requestType.toUpperCase())) {
            return "javascript:void(0);";
        }
        if (url.contains("pageNo")) {
            int index = url.indexOf("pageNo");
            String temp = url.substring(index);
            int fastLen = temp.indexOf("&");
            if (fastLen == -1) {
                return url.substring(0, index) + "pageNo=" + pageNo;
            } else {
                return url.substring(0, index) + "pageNo=" + pageNo + temp.substring(fastLen);
            }
        } else {
            if (url.contains("?")) {
                return url.replace("?", "?pageNo=" + pageNo + "&");
            } else {
                return url + "?pageNo=" + pageNo;
            }
        }
    }

}
