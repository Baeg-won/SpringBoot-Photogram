package com.cos.photogram.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties({"user"})
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;
	
	private String caption;
	private String post_image_url;	//사진을 전달받아서 서버의 특정 폴더에 저장할 것이므로 사진이 저장된 경로를 저장
	
	private LocalDateTime create_date;
	
	@PrePersist
	public void createDate() {
		create_date = LocalDateTime.now();
	}
}
