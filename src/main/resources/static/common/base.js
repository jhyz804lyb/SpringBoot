$(function () {
})

function initButton() {
}

function serach() {
    if (requestType && "get" == requestType) {
        $('#form1').submit();
    } else {
        $.ajax({
            url: fromUrl,
            data: $('#form1').serialize(),
            type: 'post',
            success: function (result) {
                var $parent = $("ajaxBoot#ajaxBoot").parent();
                $("ajaxBoot#ajaxBoot").remove();
                $parent[0].appendChild($(getHtml(result))[0]);
            }
        });
    }
}

function openPage(param) {
    if (param.indexOf("?") != -1) {
        var url = param.split("?")[0];
        var pageData = param.split("?")[1];
        $.ajax({
            url: url,
            data: pageData,
            type: 'post',
            success: function (result) {
                var $parent = $("ajaxBoot#ajaxBoot").parent();
                $("ajaxBoot#ajaxBoot").remove();
                $parent[0].appendChild($(getHtml(result))[0]);
            }
        });
    }
}

function getHtml(htmlStr) {
    if (htmlStr.indexOf("ajaxBoot") != -1) {
        return htmlStr.substring(htmlStr.indexOf("<ajaxBoot"), 11 + htmlStr.indexOf("</ajaxBoot>"))
    } else {
        return htmlStr;
    }
}