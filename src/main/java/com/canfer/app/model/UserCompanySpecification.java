package com.canfer.app.model;

import org.springframework.data.jpa.domain.Specification;


import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;




@Join(path = "empresa", alias = "e")
@Spec(path="e.nombre", spec = Equal.class)
public interface UserCompanySpecification extends Specification<ComprobanteFiscal> {

}
