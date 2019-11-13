package com.cos.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.model.Comment;
import com.google.gson.Gson;

/**
 * Servlet implementation class AjaxTest
 */
@WebServlet("/test/reply")
public class ReplyTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReplyTest() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8"); // MIME 타입

		// 1.Json 데이터 받기 , getReader()
		BufferedReader in = request.getReader();

		// 2. Json 데이터 sysout으로 출력해보기
		String replyJsonString = in.readLine();
		System.out.println("요청 데이터 : " + replyJsonString);

		// 3.Josn 데이터를 java 오브젝트로 변환(Gson 라이브 러리, fromJson())
		Gson gson = new Gson();
		
		Comment reply = gson.fromJson(replyJsonString, Comment.class);
		
		//4. java 오브젝트를 sysout 으로 출력해보기
		System.out.println("id : "+reply.getId()); 
		System.out.println("boardId : " + reply.getBoardId());
		System.out.println("userId : " + reply.getUserId());
		System.out.println("content : " + reply.getContent());
		System.out.println("createDate : "+reply.getCreateDate());
		
		
		PrintWriter out = response.getWriter();
		out.print("ok");
		out.flush();

	}

}
