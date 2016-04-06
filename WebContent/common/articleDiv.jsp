<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<article>
	<div class="zhuti-bar <c:if test="${tms.isTop eq '0'}">totop</c:if>">
		<a href="/comments/queryComments.do?testimonialsId=${tms.id }" class="zhuti">
			<strong>标题:</strong>${tms.title }<br />
			<c:if test="${! empty tms.filePath }">
				<img class="testim_img" src="${tms.filePath }" />
			</c:if>
		</a>
		<c:if test="${tms.isTop ne '0'}">
		<div class="contents">
			<strong>内容:</strong>
			<c:set var="contentsLength" value="${fn:length(tms.contents)}" />
			<c:set var="hideWordsLength" value="60" />
			<c:if test="${contentsLength <= hideWordsLength}">
				<div id="contentsDiv">${tms.contents}</div>
			</c:if>
			<c:if test="${contentsLength > hideWordsLength}">
				<c:set var="shortWords" value="${fn:substring(tms.contents, 0, hideWordsLength) }" />
				<div class="shortWordsDiv">
					${shortWords}...
					<a href="javascript:void(0);" class="btn btn-blue" onclick="showallwords(this)">展开</a>
					<input type="hidden" value="${tms.contents }">
				</div>
			</c:if>
		</div>
		<p>
			<span>
				<fmt:parseDate var="testimonialsDateTemp" value="${tms.createDate }" pattern="yyyyMMddHHmmss" />
				<fmt:formatDate var="testimonialsDate" value="${testimonialsDateTemp }" type="both"/>
				<strong>发表时间：</strong>${testimonialsDate }<br/>
			</span>
			<a href="javascript:void(0);" class="btn btn-blue" onclick="openComments('${tms.id}');">
				留言
			</a>
			&nbsp;
			<a href="/comments/queryComments.do?testimonialsId=${tms.id }">
				查看所有评价(${tms.commentCounts })
			</a>
			<%-- <a href="javascript:void(0);" onclick="allCommentsAbout('${tms.id}');">
				查看所有评价
			</a> --%>
		</p>
		</c:if>
	</div>
</article>