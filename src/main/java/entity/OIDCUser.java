package entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OIDCUser {

	private int id;
	private String username;
	private String email;
	private Timestamp createTime;
}
