package com.cos.action.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.dao.BoardDao;
import com.cos.model.Board;
import com.cos.util.Utils;

public class BoardListAction implements Action {
	
	private final static String TAG = "BoardListAction : ";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("page")== null) return;
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		//page <= 0 혹은 page > maxNum 버튼 비활성화
				
		if(page <=0) {
			return;
		}
		
		BoardDao bDao = new BoardDao();
		List<Board> boards = null;
		List<Board> hotBoards = bDao.findOrderByReadCountDesc();
		
		// search와 목록을 분기
		if(request.getParameter("search") == null || request.getParameter("search").equals("")) {
			boards = bDao.findAll(page); //페이징하기
			request.setAttribute("search", null);
		}else {
			String search = request.getParameter("search");
			boards = bDao.findAll(page, search); //paging 하기
			request.setAttribute("search", search);
			System.out.println("서치탔음.");
		}
		
		Utils.setPreviewImg(boards); //이미지를 previewImg에 저장
		Utils.setPreview(boards); //이미지 태그 제거
		Utils.setPreviewImg(hotBoards);

		
		request.setAttribute("boards", boards);
		request.setAttribute("hotBoards", hotBoards);
		
		
		RequestDispatcher dis = 
				request.getRequestDispatcher("/board/list.jsp");
		dis.forward(request, response);
		
		
	}
}
