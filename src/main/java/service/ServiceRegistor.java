package service;

import java.io.IOException;
import java.util.Optional;

import com.nimbusds.jwt.JWTClaimsSet;

import controller.OIDCUtil;
import entity.OIDCUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.OIDCUserDao;
import repository.OIDCUserDaoImpl;
@WebServlet("/oidctest/service/registor")
public class ServiceRegistor extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		// 取得 idToken
		String idToken = req.getParameter("idToken");
		OIDCUserDao dao = OIDCUserDaoImpl.getInstance();
		try {
			// 驗證 idToken 並取得使用者資料
			JWTClaimsSet claimsSet = OIDCUtil.getClaimsSetAndVerifyIdToken(idToken);
			OIDCUser oidcUser = new OIDCUser();
			// 根據 emial 來得到使用者名字
			String email = claimsSet.getStringClaim("email");
			String userName = email.substring(0, email.indexOf('@'));
			oidcUser.setUsername(userName);
			oidcUser.setEmail(email);
			int rowcount = dao.registerUser(oidcUser);
			resp.getWriter().println("註冊成功 !");
			resp.getWriter().println("<a href='http://localhost:8080/OIDCTest/index.jsp'>重新登入</a>");
		} catch (Exception e) {
			resp.getWriter().println("Exception: " + e);
		}
	}

}
