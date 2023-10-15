package br.com.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.model.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
