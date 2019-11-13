package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.UserDao;
import com.cos.model.User;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class UserJoinAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("username")==null||request.getParameter("username").equals("")||request.getParameter("password")==null||request.getParameter("password").equals("")|| request.getParameter("email")==null|| request.getParameter("email").equals("")) {
			return;
		}
		
		String rawPassword = request.getParameter("password");

		String username = request.getParameter("username");
		String password = SHA256.getEncrypy(rawPassword, "cos");
		String email = request.getParameter("email");
		String address = request.getParameter("address"); //주소추가
				
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);		
		user.setAddress(address);
		

		UserDao dao = new UserDao();
		int result = dao.save(user);

		if(result==1) {
			response.sendRedirect("/blog/AuthGoogle/gmailSendAction.jsp?email="+email);
		}else {
			Script.back(response);			
		}

	}
}
