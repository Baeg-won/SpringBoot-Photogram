package com.cos.photogram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.image.ImageRepository;
import com.cos.photogram.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	/* 이미지 업로드 폴더 */
	@Value("${file.path}")
	private String uploadFolder;
	
	/* 이미지 업로드 */
	@Transactional
	public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(principalDetails.getUser(), imageFileName);
		imageRepository.save(image);
	}
	
	/* 스토리 페이지 */
	@Transactional(readOnly = true)
	public Page<Image> story(Long principalId, Pageable pageable) {
		Page<Image> images = imageRepository.story(principalId, pageable);
		
		images.forEach((image) -> {
			image.setLikesCount(image.getLikes().size());
			image.getLikes().forEach((like) -> {
				if(like.getUser().getId() == principalId)
					image.setLikesState(true);
			});
			image.setHashtagList(Arrays.asList(image.getHashtag().split(",")));
		});
		
		return images;
	}
	
	/* 인기 페이지 */
	@Transactional(readOnly = true)
	public List<Image> popular() {
		return imageRepository.popular();
	}
}
