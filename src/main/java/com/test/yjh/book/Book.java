package com.test.yjh.book;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Book {

	@Id @GeneratedValue
	private Integer id;
	private String title;
	private String description;
	private String author;
	
	@Enumerated(EnumType.STRING)
	private BookType type = BookType.NOVEL;
	
	private int price;
	
	
	
	
}
