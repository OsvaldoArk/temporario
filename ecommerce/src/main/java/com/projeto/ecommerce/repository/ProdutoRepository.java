package com.projeto.ecommerce.repository;

import com.projeto.ecommerce.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    
}
