package io.bada.springai_chatbot_qna.ctrl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.bada.springai_chatbot_qna.service.HotelEmbeddingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel")
public class DocumentUploadController {

	private final HotelEmbeddingService embeddingService;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadText(@RequestParam("file") MultipartFile file){
		try {
			embeddingService.processUploadText(file);
			return ResponseEntity.ok("Text 업로드 및 임베딩 완료");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
												.body("처리 중 오류 발생" + e.getMessage());
		}
	}
	
}














