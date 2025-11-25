package lista.servicos.mapper;


import lista.servicos.controller.dto.ServicoRequest;
import lista.servicos.controller.dto.ServicoResponse;
import lista.servicos.domain.Servico;

public class ServicoMapper {
    public static Servico toEntity(ServicoRequest r) {
        var s = new Servico();
        s.setNome(r.nome);
        s.setTelefone(r.telefone);
        s.setCategoria(r.categoria);
        s.setDescricao(r.descricao);
        return s;
    }
    public static void copyToEntity(ServicoRequest r, Servico s) {
        s.setNome(r.nome);
        s.setTelefone(r.telefone);
        s.setCategoria(r.categoria);
        s.setDescricao(r.descricao);
    }
    public static ServicoResponse toResponse(Servico s) {
        return ServicoResponse.builder()
                .id(s.getId())
                .nome(s.getNome())
                .telefone(s.getTelefone())
                .categoria(s.getCategoria())
                .descricao(s.getDescricao())
                .criadoEm(s.getCriadoEm())
                .build();
    }
}
