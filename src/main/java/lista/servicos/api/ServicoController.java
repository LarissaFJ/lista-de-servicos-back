package lista.servicos.api;


import jakarta.validation.Valid;
import lista.servicos.api.dto.ServicoRequest;
import lista.servicos.api.dto.ServicoResponse;
import lista.servicos.api.mapper.ServicoMapper;
import lista.servicos.domain.ServicoCategoria;
import lista.servicos.repo.ServicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:4200") // liberação pro Angular
public class ServicoController {

    private final ServicoRepository repo;

    public ServicoController(ServicoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<ServicoResponse> list(
            @RequestParam(required = false) ServicoCategoria categoria,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pageable = PageRequest.of(page, size);

        if (categoria != null && q != null && !q.isBlank()) {
            return repo.findByCategoriaAndNomeContainingIgnoreCaseOrCategoriaAndDescricaoContainingIgnoreCase(
                    categoria, q, categoria, q, pageable
            ).map(ServicoMapper::toResponse);
        }
        if (categoria != null) {
            return repo.findByCategoria(categoria, pageable)
                    .map(ServicoMapper::toResponse);
        }
        if (q != null && !q.isBlank()) {
            return repo.findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(q, q, pageable)
                    .map(ServicoMapper::toResponse);
        }
        return repo.findAll(pageable).map(ServicoMapper::toResponse);
    }

    @GetMapping("/{id}")
    public ServicoResponse get(@PathVariable Long id) {
        return repo.findById(id).map(ServicoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    @PostMapping
    public ServicoResponse create(@RequestBody @Valid ServicoRequest req) {
        log.info("Criando serviço: {}", req);
        var saved = repo.save(ServicoMapper.toEntity(req));
        log.info("Serviço criado: {}", saved);
        return ServicoMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public ServicoResponse update(@PathVariable Long id, @RequestBody @Valid ServicoRequest req) {
        var s = repo.findById(id).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        ServicoMapper.copyToEntity(req, s);
        return ServicoMapper.toResponse(repo.save(s));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
