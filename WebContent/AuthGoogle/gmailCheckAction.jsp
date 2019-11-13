<%@page import="com.cos.util.SHA256"%>
<%@page import="java.io.PrintWriter"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// code 값 받기
	String code = request.getParameter("code");
    
	//DB에서 id 값으로 email과 salt 가져오기(가정)
	
	// return code 값이랑 send code 값을 비교해서 동일하면 
	boolean rightCode = SHA256.getEncrypy("wjsaygk@naver.com", "cos").equals(code) ? true : false ;
	
	
	//DB에 칼럼 emailcheck (Number)1 = 인증, 0= 미인증 1을 update 해준다
	PrintWriter script = response.getWriter();
	if(rightCode == true){
		script.println("<script>");
		script.println("alert('이메일 인증에 성공하였습니다.')");
		script.println("location.href='/blog/user/login.jsp'");
		script.println("</script>");
	} else{
		script.println("<script>");
		script.println("alert('이메일 인증을 실패하였습니다.')");
		script.println("location.href='/blog/AuthGoogle/error.jsp'");
		script.println("</script>");
	}



		
	
			
	//DB 에 컬럼 emailCheck(Number) 1 = 인증, 0 = 미인증    1을 update 해준다
	
	//인증 완료 로그인 페이지 이동
	
	//미인증 error 페이지 이동
	
%>