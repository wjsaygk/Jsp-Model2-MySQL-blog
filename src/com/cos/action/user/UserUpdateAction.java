package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.UserDao;
import com.cos.model.User;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class UserUpdateAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("username")==null||request.getParameter("username").equals("")||request.getParameter("password")==null||request.getParameter("password").equals("")|| request.getParameter("email")==null|| request.getParameter("email").equals("")) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id")); //회원정보 수정 추가
		String rawPassword = request.getParameter("password");

		String username = request.getParameter("username");
		String password = SHA256.getEncrypy(rawPassword, "cos");
		String email = request.getParameter("email");
		String address = request.getParameter("address"); //주소추가
				
		
		User user = new User();
		user.setId(id); //회원정보 수정 추가
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);		
		user.setAddress(address);
		

		UserDao dao = new UserDao();
		int result = dao.update(user);

		if(result==1) {
			response.sendRedirect("/blog/index.jsp");
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}else {
			Script.back(response);			
		}

	}
}
