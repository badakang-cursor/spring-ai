package io.bada.springai_chatbot_rag.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.knuddels.jtokkit.api.EncodingType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbedingService {

	private final VectorStore vectorStore;
	
	public void porcessUploadPdf(MultipartFile file) throws IOException {
		//1. 사용자가 업로드한 PDF파일을 임시 파일로 생성
		File tmpFile = File.createTempFile("uploaded", ".pdf");
		file.transferTo(tmpFile);
		Resource fileResource = new FileSystemResource(tmpFile);
		
		try {
			PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
					.withPageTopMargin(0) // PDF 페이지 상단에 여백을 설정
					.withPageExtractedTextFormatter( //페이지에서 추출된 텍스를 포매칭 방식 지정
							ExtractedTextFormatter.builder()
								.withNumberOfBottomTextLinesToDelete(0) //페이지 상단에서 삭제할 텍스트 줄수
								.build())
					.withPagesPerDocument(1)// 한번에 처리할 수 있는 페이지를 지정
					.build();
			
			PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(fileResource, config);
			
			List<Document> documents = pdfReader.get();
			
			/*
			 * 문서를 분할하기 위한 TokenTextSpliter 
			 */
			// EncodingType DEFAULT_ENCODING_TYPE = EncodingType.CL100K_BASE;
			// List<Character> DEFAULT_PUNCTUATION_MARKS = List.of('.', '?', '!', '\n');
			// TokenTextSplitter splitter = new TokenTextSplitter(DEFAULT_ENCODING_TYPE
			// 												, 1000
			// 												, 400
			// 												, 10
			// 												, 5000
			// 												, true
			// 												, DEFAULT_PUNCTUATION_MARKS);
			TokenTextSplitter splitter = TokenTextSplitter.builder()
											.withChunkSize(1000)
											.withMinChunkSizeChars(400)
											.withMinChunkLengthToEmbed(10)
											.withMaxNumChunks(5000)
											.withKeepSeparator(true)
											.build();
			List<Document> splitDocumets = splitter.apply(documents);
			// List<Document> splitDocumets = new ArrayList<Document>();
			
			// PGvector Store에 저장
			vectorStore.accept(splitDocumets);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tmpFile.delete();
		}
		
	}
	
}













