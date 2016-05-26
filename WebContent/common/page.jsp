<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 做分页 -->

<style type="text/css">
.page_div {text-align: right; margin-top: 20px; }
.page_div a {text-decoration: none; text-align: center; border: 2px solid #dedede; margin: 3px; -moz-border-radius: 5px; -webkit-border-radius: 5px;}
.page_div .a_fixed {padding-left: 4px;}
.page_div a:hover {background-color: #ccc;}
.currentPage {background-color: grey;}
</style>

<div class="page_div">
<a href="?currentPage=1">首页</a>
<c:if test="${pager.currentPage gt 1 }"><a href="?currentPage=${pager.prevPage }">上一页</a></c:if>
<c:if test="${pager.begin gt 1 }">...</c:if>

<c:forEach begin="${pager.begin }" end="${pager.end}" var="page" varStatus="p" >
	<a class="a_fixed" href="?currentPage=${p.index }" <c:if test="${pager.currentPage eq p.index }"> class="currentPage" </c:if>>
		${p.index }
	</a>
</c:forEach>

<c:if test="${pager.end lt (pager.totalPages- 1) }">...</c:if>

<c:if test="${pager.currentPage lt pager.totalPages }"><a href="?currentPage=${pager.nextPage }">下一页</a></c:if>
<a href="?currentPage=${pager.totalPages }" <c:if test="${pager.currentPage eq pager.totalPages }"> class="currentPage" </c:if>>
${pager.totalPages }
</a>

<a href="?currentPage=${pager.lastPage }">
	尾页
</a>
&nbsp;共${pager.totalPages }页
</div>