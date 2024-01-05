package service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.OIDCUserDao;
import repository.OIDCUserDaoImpl;
@WebServlet("/oidctest/service/delete")
public class ServiceDelete extends HttpServlet{
	
	OIDCUserDao dao = OIDCUserDaoImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = req.getSession();
		String username = session.getAttribute("username") + "";
		try {
			int rowcount = dao.deleteUserByUsername(username);
			resp.getWriter().print("帳戶刪除成功 !");
			resp.getWriter().println("<a href='http://localhost:8080/OIDCTest/index.jsp'>重新登入</a>");
		} catch (Exception e) {
			resp.getWriter().print("Exception: " + e);
		}
	}
	
	
}
