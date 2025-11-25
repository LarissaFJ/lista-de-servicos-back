package lista.servicos.service;

import lista.servicos.controller.dto.ServicoRequest;
import lista.servicos.controller.dto.ServicoResponse;
import lista.servicos.domain.ServicoCategoria;
import lista.servicos.mapper.ServicoMapper;
import lista.servicos.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoResponse get(Long id) {
        log.info("Buscando serviço por id: {}", id);
        return servicoRepository.findById(id)
                .map(ServicoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id %s".formatted(id)));
    }

    public ServicoResponse create(ServicoRequest req) {
        log.info("Criando serviço: {}", req);
        var saved = servicoRepository.save(ServicoMapper.toEntity(req));
        log.info("Serviço criado: {}", saved);
        return ServicoMapper.toResponse(saved);
    }

    public Page<ServicoResponse> list(ServicoCategoria categoria, String q, int page, int size) {

        var pageable = PageRequest.of(page, size);

        boolean temCategoria = categoria != null;
        boolean temBusca = q != null && !q.isBlank();

        if (temCategoria && temBusca) {
            return servicoRepository
                    .findByCategoriaAndNomeContainingIgnoreCaseOrCategoriaAndDescricaoContainingIgnoreCase(
                            categoria, q, categoria, q, pageable
                    )
                    .map(ServicoMapper::toResponse);
        }

        if (temCategoria) {
            return servicoRepository.findByCategoria(categoria, pageable)
                    .map(ServicoMapper::toResponse);
        }

        if (temBusca) {
            return servicoRepository.findByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(
                    q, q, pageable
            ).map(ServicoMapper::toResponse);
        }

        return servicoRepository.findAll(pageable)
                .map(ServicoMapper::toResponse);
    }

    public ServicoResponse update(Long id, ServicoRequest req) {
        log.info("Atualizando serviço: {}", req);
        var saved = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id %s".formatted(id)));
        ServicoMapper.copyToEntity(req, saved);
        servicoRepository.save(saved);
        log.info("Serviço atualizado: {}", saved);
        return ServicoMapper.toResponse(saved);
    }

    public ServicoResponse delete(Long id) {
        log.info("Deletando serviço: {}", id);
        var saved = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id %s".formatted(id)));
        servicoRepository.delete(saved);
        log.info("Serviço deletado: {}", saved);
        return ServicoMapper.toResponse(saved);
    }
}
