package com.test.yjh.book;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BookResource extends Resource<Book> {

	public BookResource(Book content, Link... links) {
		super(content, links);
		add(linkTo(BookController.class).slash(content.getId()).withSelfRel());
	}
}
