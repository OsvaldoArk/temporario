package com.projeto.ecommerce.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.projeto.ecommerce.model.Produto;
import com.projeto.ecommerce.repository.ProdutoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
    
    @Autowired
    ProdutoRepository repositorio;

    @GetMapping
    public ResponseEntity<?> listarProdutos(){
        List<Produto> produtos = repositorio.findAll();

        return ! produtos.isEmpty()? ResponseEntity.ok(produtos) 
                            : ResponseEntity.noContent().build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> listarProduto(@PathVariable Long codigo){
        Optional<Produto> produto = repositorio.findById(codigo);
        
        return produto.isPresent() ? ResponseEntity.ok(produto)
                                    : ResponseEntity.noContent().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastrarProduto(@Valid @RequestBody Produto produto, HttpServletResponse resposta){

        Produto produtoSalvo = repositorio.save(produto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                                    .buildAndExpand(produtoSalvo.getCodigo()).toUri();
        resposta.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(produtoSalvo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long codigo,@Valid @RequestBody Produto produto){
        Produto produtoSalvo = repositorio.findById(codigo).get();

        BeanUtils.copyProperties(produto, produtoSalvo,"codigo");

        repositorio.save(produtoSalvo);

        return ResponseEntity.ok(produtoSalvo);
    }

    @DeleteMapping("/{codigo}")
    public void removerProduto(@PathVariable Long codigo){
        repositorio.deleteById(codigo);
    }
}
