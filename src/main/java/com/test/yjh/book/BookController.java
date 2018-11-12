package com.test.yjh.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.yjh.common.ErrorResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/books", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class BookController {

	@Autowired
	BookRepository bookRepository;
	
	@PostMapping
	public ResponseEntity create(@RequestBody Book book, Errors errors){
		if(errors.hasErrors()){
			return ResponseEntity.badRequest().body(new ErrorResource(errors));
		}
		
		Book savedBook = bookRepository.save(book);
		URI uri = linkTo(BookController.class).slash(savedBook.getId()).toUri();
		BookResource bookResource = new BookResource(savedBook);
		bookResource.add(linkTo(BookController.class).withRel("books"));
		
		return ResponseEntity.created(uri).body(bookResource);
	}
	
	@GetMapping
	public ResponseEntity getBooks(Pageable pageable,
									PagedResourcesAssembler<Book> assembler){
		Page<Book> page = bookRepository.findAll(pageable);
		PagedResources<BookResource> pagedResources = assembler.toResource(page, e -> new BookResource(e));
		return ResponseEntity.ok(pagedResources);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity getBook(@PathVariable Integer id){
		Optional<Book> byId = this.bookRepository.findById(id);
		if(!byId.isPresent()){
			return ResponseEntity.notFound().build();
		}
		
		Book book = byId.get();
		BookResource bookResource = new BookResource(book);
		
		return ResponseEntity.ok(bookResource);
	}
}
