package com.test.yjh.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;
	
	@Test
	public void crud(){
		Book book = Book.builder()
				.title("자바스크립트 완벽 가이드")
				.description("자바스크립트 전문 개발자라면 반드시 소장해야 할, 세심하게 잘 구성한 책이다.")
				.author("데이빗 플래너건")
				.price(54000)
				.build();
		
		bookRepository.save(book);
		
		List<Book> all = bookRepository.findAll();
		assertThat(all.size()).isEqualTo(1);
	}
}
