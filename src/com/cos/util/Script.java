package com.cos.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	
	public static void alert(HttpServletResponse response,String alertText) {
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert("+ alertText +")");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static  void back(HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('join fail')");
			out.println("history.back();");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
