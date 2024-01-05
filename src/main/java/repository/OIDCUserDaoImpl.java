package repository;

import java.sql.PreparedStatement;
import java.util.Optional;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import entity.OIDCUser;

public class OIDCUserDaoImpl implements OIDCUserDao{

	private static OIDCUserDao _instance = new OIDCUserDaoImpl();
	
	private JdbcTemplate jdbcTemplate;
	
	private OIDCUserDaoImpl() {
		
		try {
	        // 透過 JNDI 來查找資源
	        InitialContext ctx = new InitialContext();
	        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/practice"); // 使用 JNDI 名稱

	        // 設定 JdbcTemplate 的數據源
	        this.jdbcTemplate = new JdbcTemplate(ds);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static OIDCUserDao getInstance() {
		return _instance;
	}
	
	@Override
	public int registerUser(OIDCUser oidcUser) {
		String sql = "insert into OIDCUser(username, email) values(?, ?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(conn -> {
	        PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"id"});
	        pstmt.setString(1, oidcUser.getUsername());
	        pstmt.setString(2, oidcUser.getEmail());
	        return pstmt;
	    }, keyHolder);

	    return keyHolder.getKey() != null ? keyHolder.getKey().intValue(): 0;
	}

	@Override
	public Optional<OIDCUser> findUserByUsername(String username) {
		String sql = "select id, username, email, create_date from oidcuser where username = ?";
		OIDCUser oidcUser = null;
		try {
			oidcUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(OIDCUser.class), username);
			return Optional.ofNullable(oidcUser);
		} catch (Exception e) {
		}
		return Optional.empty();
	}

	@Override
	public int deleteUserByUsername(String username) {
		String sql = "delete from oidcuser where username = ?";
		return jdbcTemplate.update(sql, username);
	}

}
