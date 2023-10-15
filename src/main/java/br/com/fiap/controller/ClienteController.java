package br.com.fiap.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.model.Clientes;
import br.com.fiap.repository.ClientesRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "API de Clientes", description = "Api que mostra os clientes de produtos através de API")
@RequestMapping("api/clientes")
public class ClienteController {

	@Autowired
	private ClientesRepository repository;

	@GetMapping
	@Operation(summary = "Mostra todos os clientes")
	@ResponseStatus(HttpStatus.OK)
	public List<Clientes> index() {
		return repository.findAll();
	}

	@PostMapping
	@Operation(summary = "Cria um novo cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros insuficientes"),
			@ApiResponse(responseCode = "500", description = "Erro interno")
	})
	public ResponseEntity<String> create(@RequestBody Clientes clientesRequest) {
		try {
			if (clientesRequest.getNome() == null || clientesRequest.getEndereco() == null
					|| clientesRequest.getTelefone() == null) {
				System.out.println("====ERROR=====");
				System.out.println("TODOS OS PARÂMETROS SÃO OBRIGATÓRIOS");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			Clientes cliente = new Clientes();
			cliente.setNome(clientesRequest.getNome());
			cliente.setEndereco(clientesRequest.getEndereco());
			cliente.setTelefone(clientesRequest.getTelefone());

			repository.save(cliente);

			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("{id}")
	@Operation(summary = "Exibe cliente por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	public ResponseEntity<Clientes> show(@PathVariable("id") long id) {

		Optional<Clientes> cliente = repository.findById(id);

		if (cliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(cliente.get());
	}

	@PutMapping("{id}")
	@Operation(summary = "Atualiza cliente por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente aualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	})
	public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Clientes clientesRequest) {
		try {
			if (repository.findById(id).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			Clientes cliente = new Clientes();
			cliente.setId(id);
			cliente.setNome(clientesRequest.getNome());
			cliente.setEndereco(clientesRequest.getEndereco());
			cliente.setTelefone(clientesRequest.getTelefone());

			repository.save(cliente);

			return ResponseEntity.ok("Cliente atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("{id}")
	@Operation(summary = "Atualiza cliente parcialmente por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente atualizado")
	})
	public ResponseEntity<String> change(@PathVariable("id") long id, @RequestBody Clientes clientesRequest) {
		try {
			
			
			if (repository.findById(id).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			Clientes cliente = repository.findById(id).get();
			cliente.setId(id);
			

			if (clientesRequest.getNome() != null) {
				cliente.setNome(clientesRequest.getNome());
			}

			if (clientesRequest.getEndereco() != null) {
				cliente.setEndereco(clientesRequest.getEndereco());
			}
			if (clientesRequest.getTelefone() != null) {
				cliente.setTelefone(clientesRequest.getTelefone());
			}

			repository.save(cliente);

			return ResponseEntity.ok("Cliente atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Exclui cliente por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro interno")
	})
	public ResponseEntity<String> destroy(@PathVariable("id") long id) {
		try {
			if (repository.findById(id).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			Clientes cliente = repository.findById(id).get();
			
			repository.delete(cliente);

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
}
