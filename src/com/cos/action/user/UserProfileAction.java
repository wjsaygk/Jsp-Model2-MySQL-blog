package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.UserDao;
import com.cos.model.User;
import com.cos.util.Script;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UserProfileAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 세션에있는 유저 객체 받아오기
		HttpSession session = request.getSession();
		User user= (User)session.getAttribute("user");
		//파일업데이트구현 멀티파트 리퀘스트
		String path = request.getServletContext().getRealPath("media");
		
		 
		 try {
			 MultipartRequest multi = new MultipartRequest(
				request,			
				path,
				1024*1024*2, //2mb
				"UTF-8",
				new DefaultFileRenamePolicy() //동일한 파일명이 들어오면 파일명 뒤에 숫자를 붙임.
				);
			 
			 String filename = multi.getFilesystemName("userProfile");
			 String filepath = "/blog/media/" + filename;
			
			//DB에없데이트 해주기 (DAO 함수 만들어야함)
			UserDao dao = new UserDao();
			int result = dao.profileUpdate(user, filepath);

			if(result==1) {
				//세션업데이트
				//인덱스로 리다이렉트
				user.setUserProfile(filepath);
				HttpSession session1 = request.getSession();
				session1.setAttribute("user", user);
				response.sendRedirect("/blog/index.jsp");
			}else {
				Script.back(response);			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			
								
				 
		
		 

	}
}
