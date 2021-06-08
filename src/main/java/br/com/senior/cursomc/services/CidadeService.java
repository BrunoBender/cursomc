package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Cidade;
import br.com.senior.cursomc.repositories.CidadeRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository repo;
	
	public Optional<Cidade> buscar(Integer id) {
		Optional<Cidade> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ Cidade.class.getName());
		}
		return obj;
	}
	
	public List<Cidade> getAll(){
		List<Cidade> obj = repo.findAll();
		return obj;
	}

	public List<Cidade> getByEstadoId(Integer idEstado){
		return repo.findCidadesByEstadoId(idEstado);
	}

	
	public void saveAll(List<Cidade> obj) {
		repo.saveAll(obj);
	}
}
