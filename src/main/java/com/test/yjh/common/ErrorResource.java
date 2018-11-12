package com.test.yjh.common;

import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;

import com.test.yjh.FrontController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;

public class ErrorResource extends Resource<Errors>{

	public ErrorResource(Errors content, Link... links) {
		super(content, links);
		add(linkTo(FrontController.class).withRel("main"));
	}
	
}
