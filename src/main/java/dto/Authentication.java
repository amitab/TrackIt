package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import helper.Encryption;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="auth")
final public class Authentication {
	
	@Id @GeneratedValue
	@Column(name="auth_id")
	private int authId;
	
	@Column(name="pwd", nullable = false)
	private String pwd = null;
	
	@Column(name="secret_question")
	private String secretQuestion = null;
	
	@Column(name="secret_answer")
	private String secretAnswer = null;

	public int getAuthId() {
		return authId;
	}

	public void setAuthId(int authId) {
		this.authId = authId;
	}

	public String getPwd() {
		//return Encryption.getEncryptor().decrypt(pwd); Once we get to production, use this
		return pwd;
	}

	public void setPwd(String pwd) {
		//this.pwd = Encryption.getEncryptor().encrypt(pwd); Once we get to production, use this
		this.pwd = pwd;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	
}
