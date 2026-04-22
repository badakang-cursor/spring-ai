package io.bada.springai_chatbot_qna.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

// Text를 전달받아 벡터 저장소에 임베딩 및 저장

@Service
@RequiredArgsConstructor
public class HotelEmbeddingService {

	private final VectorStore vectorStore;
	private final JdbcClient jdbcClient;
	
	public void processUploadText(MultipartFile file) throws IOException {
		File tmpFile = File.createTempFile("upload", ".txt");
		file.transferTo(tmpFile);
		Resource fileResource = new FileSystemResource(tmpFile);
		
		//1) 이전에 입력 레코드 갯수를 확인
		Integer count = jdbcClient.sql("select count(*) from hotel_vector")
								.query(Integer.class)
								.single();
		
		try {
			//2) 레코드가 없는 경우 파일을 읽어서 Embedding 작업을 진행
			if(count == 0) {
				List<Document> documents = Files.lines(fileResource.getFile().toPath())
															.map(Document::new)
															.collect(Collectors.toList());
			//3) Split  처리
				TokenTextSplitter splitter = TokenTextSplitter.builder().build();
				List<Document> splitDocuments = splitter.apply(documents);
				
			//4) 벡터 저장소에 임베딩 및 저장
				vectorStore.accept(splitDocuments);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			tmpFile.delete();
		}
		
	}
	
}









