package com.test.yjh;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.yjh.book.BookController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class FrontController {

	@GetMapping("/api")
	public ResourceSupport index(){
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(BookController.class).withRel("books"));
		return index;
	}
}
