package com.cos.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.action.user.UserFactory;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.들어오는 모든 문자를 UTF-8로 인코딩
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		
		





		//2. command 처리
		String cmd = request.getParameter("cmd");
		//3. 검증 
		if(cmd==null||cmd.equals("")) {
			cmd="list";
		}
		//4. Factory 연결
		Action action = UserFactory.getAction(cmd);
		//5. excute 실행
		if(action != null) {
			action.execute(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
