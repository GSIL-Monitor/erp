package com.stosz.tms.model.aramex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Aramex clientInfo elementDto
 * @author xiepengcheng
 * @version [1.0,2017年12月15日]
 */
public class ClientInfo implements Serializable{

	public static final Logger logger = LoggerFactory.getLogger(ClientInfo.class);
	/**
	 * 以下字段均必填！！
	 */
	private String AccountContryCode;

	private String AccountEntity;

	private String AccountNumber;

	private String AccountPin;

	private String UserName;

	private String Password;

	private String Version;

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public void setAccountPin(String accountPin) {
		AccountPin = accountPin;
	}

	public void setAccountEntity(String accountEntity) {
		AccountEntity = accountEntity;
	}

	public void setAccountContryCode(String accountContryCode) {
		AccountContryCode = accountContryCode;
	}

	@JsonProperty(value="UserName")
	public String getUserName(){return UserName;}
	@JsonProperty(value="Password")
	public String getPassword(){return Password;}
	@JsonProperty(value="Version")
	public String getVersion(){return Version;}
	@JsonProperty(value="AccountNumber")
	public String getAccountNumber(){return AccountNumber;}
	@JsonProperty(value="AccountPin")
	public String getAccountPin(){return AccountPin;}
	@JsonProperty(value="AccountEntity")
	public String getAccountEntity(){return AccountEntity;}
	@JsonProperty(value="AccountContryCode")
	public String getAccountContryCode(){return AccountContryCode;}

	public static void main(String[] args) {
		Field[] fields = ClientInfo.class.getDeclaredFields();
		for (Field field : fields) {
			String typeName = field.getType().getSimpleName();
			String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
			logger.info(getMethod);
		}
	}
}
