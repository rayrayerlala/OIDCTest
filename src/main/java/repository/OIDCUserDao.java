package repository;

import java.util.Optional;

import entity.OIDCUser;

public interface OIDCUserDao {
	public int registerUser(OIDCUser oidcUser);
	
	public Optional<OIDCUser> findUserByUsername(String username);
	
	public int deleteUserByUsername(String username);
}
