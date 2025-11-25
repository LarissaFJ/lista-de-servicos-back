package lista.servicos.controller;


import jakarta.validation.Valid;
import lista.servicos.controller.dto.ServicoRequest;
import lista.servicos.controller.dto.ServicoResponse;
import lista.servicos.mapper.ServicoMapper;
import lista.servicos.domain.ServicoCategoria;
import lista.servicos.repository.ServicoRepository;
import lista.servicos.service.ServicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:4200") // liberação pro Angular
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoRepository repo;
    private final ServicoService service;

    @GetMapping
    public Page<ServicoResponse> list(
            @RequestParam(required = false) ServicoCategoria categoria,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.list(categoria, q, page, size);
    }

    @GetMapping("/{id}")
    public ServicoResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ServicoResponse create(@RequestBody @Valid ServicoRequest req) {
        log.info("Criando serviço: {}", req);
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ServicoResponse update(@PathVariable Long id, @RequestBody @Valid ServicoRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {service.delete(id);}
}
