package pe.edu.upc.market.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.market.models.entities.Cliente;
import pe.edu.upc.market.models.repositories.ClienteRepository;

@Named
@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "MarketPU")
	private EntityManager em;

	@Override
	public Cliente save(Cliente entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Cliente update(Cliente entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		
		Optional<Cliente> optional = findById(id);
		if( optional.isPresent() ) {
			em.remove( optional.get() );
		}		
	}
	
	@Override
	public Optional<Cliente> findById(Integer id) throws Exception {
		Optional<Cliente> optional = Optional.empty();
		String qlString = "SELECT c FROM Cliente c WHERE c.id = ?1";
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, id);
		Cliente cliente = query.getResultList().stream().findFirst().orElse(null);
		if(cliente != null) {
			optional = Optional.of(cliente);
		}		
		return optional;
	}

	@Override
	public List<Cliente> findAll() throws Exception {
		// Declara la variable a retornar
		List<Cliente> clientes = new ArrayList<>();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Cliente c";
		// Crear la consulta
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		// Obtener el resultado de la consulta
		clientes = query.getResultList();
		return clientes;
	}

	@Override
	public List<Cliente> findByNombresApellidos(String nombresApellidos) throws Exception {
		
		List<Cliente> clientes = new ArrayList<>();
		
		String qlString = "SELECT c FROM Cliente c WHERE c.nombresApellidos LIKE ?1";
		
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		
		query.setParameter(1, "%" + nombresApellidos.toUpperCase() + "%");
		
		clientes = query.getResultList();
		return clientes;
	}

	@Override
	public Optional<Cliente> findByNumeroDocumento(String numeroDocumento) throws Exception {
	
		Optional<Cliente> optional = Optional.empty();
		String qlString = "SELECT c FROM Cliente c WHERE c.numeroDocumento = ?1";
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, numeroDocumento);
		// Obtener el resultado de la consulta
		//Cliente cliente = query.getSingleResult();
		Cliente cliente = query.getResultList().stream().findFirst().orElse(null);
		// Verificar la existencia del objeto
		if(cliente != null) {
			// Agregando el objeto cliente al Optional
			optional = Optional.of(cliente);
		}		
		return optional;
	}

}
