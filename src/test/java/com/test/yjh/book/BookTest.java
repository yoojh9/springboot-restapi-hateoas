package com.test.yjh.book;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BookTest {

	@Test
	public void builder(){
		String title = "책 이름";
		Book book = Book.builder()
				.title(title)
				.description("책 설명")
				.build();
		
		assertThat(book.getTitle()).isEqualTo(title);
		
	}
}
