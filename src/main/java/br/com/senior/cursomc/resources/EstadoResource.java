package br.com.senior.cursomc.resources;

import br.com.senior.cursomc.domain.Cidade;
import br.com.senior.cursomc.domain.Estado;
import br.com.senior.cursomc.dto.CidadeDto;
import br.com.senior.cursomc.dto.EstadoDto;
import br.com.senior.cursomc.services.CidadeService;
import br.com.senior.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;
    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDto>> getAllEstado(){
        return ResponseEntity.ok().body(estadoService.getAll().stream().map(obj -> new EstadoDto(obj)).collect(Collectors.toList()));
    }


    @RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
    public ResponseEntity<List<CidadeDto>> getCity(@PathVariable Integer estadoId){
        List<Cidade> cidades = cidadeService.getByEstadoId(estadoId);
        List<CidadeDto> cidadesDto = cidades.stream().map(obj -> new CidadeDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadesDto);
    }

}
