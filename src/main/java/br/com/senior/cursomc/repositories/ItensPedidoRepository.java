package br.com.senior.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.ItemPedido;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer>{

}
