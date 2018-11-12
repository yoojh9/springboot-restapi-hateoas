package com.test.yjh.book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void createBook() throws Exception {
		Book book = Book.builder()
					.title("Head First Design Patterns")
					.author("에릭 프리먼")
					.description("정말 쿨~ 하게 배우는 디자인 패턴 학습법")
					.price(28000)
					.type(BookType.NOVEL)
					.build();
		
		this.mockMvc.perform(post("/api/books")
				//.header(name, values)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(book)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("type").value(BookType.NOVEL.name()))
			.andExpect(jsonPath("_links").hasJsonPath())
			.andExpect(jsonPath("_links").hasJsonPath())
			.andExpect(jsonPath("_links.self").hasJsonPath());
		
	}
	
	@Test
	public void getBooks() throws Exception {
		IntStream.range(0, 30).forEach(this::saveBook);
		
		this.mockMvc.perform(get("/api/books")
				//	.header(HttpHeaders.AUTHORIZATION, "bearer " + "access_token")
				.param("size", "10")
				.param("page", "1"))
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("_links").hasJsonPath());
	}
	
	
	private Book saveBook(int id){
		Book book = Book.builder()
				.id(id)
				.title("test book " + id)
				.build();
		return this.bookRepository.save(book);
	}
	
	@Test
	public void getBook() throws Exception {
		Book book = this.saveBook(100);
		System.out.println(book.getId());
		this.mockMvc.perform(get("/api/books/"+book.getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(book.getId()))
			.andExpect(jsonPath("title").hasJsonPath());
	}
}
