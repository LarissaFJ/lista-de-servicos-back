package lista.servicos.repo;

import lista.servicos.domain.Servico;
import lista.servicos.domain.ServicoCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Page<Servico> findByCategoria(ServicoCategoria categoria, Pageable pageable);
    Page<Servico> findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String n, String d, Pageable pageable);
    Page<Servico> findByCategoriaAndNomeContainingIgnoreCaseOrCategoriaAndDescricaoContainingIgnoreCase(
            ServicoCategoria c1, String n, ServicoCategoria c2, String d, Pageable pageable);
}
