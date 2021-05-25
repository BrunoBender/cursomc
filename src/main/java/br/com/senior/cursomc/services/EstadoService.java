package br.com.senior.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.Estado;
import br.com.senior.cursomc.repositories.EstadoRepository;
import br.com.senior.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public Optional<Estado> buscar(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: "+ Estado.class.getName());
		}
		return obj;
	}
	
	public List<Estado> getAll(){
		List<Estado> obj = repo.findAll();
		return obj;
	}
	
	public void saveAll(List<Estado> obj) {
		repo.saveAll(obj);
	}
}
