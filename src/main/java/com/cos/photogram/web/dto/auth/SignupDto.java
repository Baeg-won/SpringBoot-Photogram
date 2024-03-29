package com.cos.photogram.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.cos.photogram.domain.user.User;

import lombok.Data;

@Data
public class SignupDto {
	
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^[A-Za-z]{1}[A-Za-z0-9]{5,19}$", message = "아이디는 6~20자 영문 소문자, 숫자를 사용하세요.")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}