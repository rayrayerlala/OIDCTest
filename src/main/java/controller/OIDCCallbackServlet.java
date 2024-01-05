package controller;

import java.io.IOException;
import java.util.Optional;

import com.nimbusds.jwt.JWTClaimsSet;

import entity.OIDCUser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.OIDCUserDao;
import repository.OIDCUserDaoImpl;
@WebServlet("/oidctest/oidc/callback")
public class OIDCCallbackServlet extends HttpServlet{
	OIDCUserDao dao = OIDCUserDaoImpl.getInstance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		Optional<OIDCUser> oidcUserOpt = null;
		// 取得授權碼
		String code = req.getParameter("code");
		String idToken = null;
		try {
			// 得到 idToken
			idToken = OIDCUtil.getIDToken(code);
			// 驗證 idToken 並取得使用者資料
			JWTClaimsSet claimsSet = OIDCUtil.getClaimsSetAndVerifyIdToken(idToken);
			// 根據 emial 來得到使用者名字
			String email = claimsSet.getStringClaim("email");
			String userName = email.substring(0, email.indexOf('@'));
			oidcUserOpt = dao.findUserByUsername(userName);
			
		} catch(Exception e) {
		}
		if(oidcUserOpt.isPresent()) {
		    String userName = oidcUserOpt.get().getUsername();
		    
		    // 存儲使用者名稱到 Session
		    HttpSession session = req.getSession(true);
		    session.setAttribute("username", userName);
			
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/menu.jsp");
			req.setAttribute("oidcUser", oidcUserOpt.get());
			rd.forward(req, resp);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/registor.jsp");
			req.setAttribute("idToken", idToken);
			rd.forward(req, resp);
		}
	}

	
}
