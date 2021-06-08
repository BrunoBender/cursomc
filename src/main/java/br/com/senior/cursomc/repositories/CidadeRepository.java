package br.com.senior.cursomc.repositories;

import br.com.senior.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Cidade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

    @Transactional(readOnly = true)
    @Query("SELECT cid FROM Cidade cid WHERE cid.estado.id = :id_estado ORDER BY cid.nome")
    List<Cidade> findCidadesByEstadoId(@Param("id_estado") Integer idEstado);

}
