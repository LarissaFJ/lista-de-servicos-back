package lista.servicos.api.mapper;


import lista.servicos.api.dto.ServicoRequest;
import lista.servicos.api.dto.ServicoResponse;
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
        var out = new ServicoResponse();
        out.id = s.getId();
        out.nome = s.getNome();
        out.telefone = s.getTelefone();
        out.categoria = s.getCategoria();
        out.descricao = s.getDescricao();
        out.criadoEm = s.getCriadoEm();
        return out;
    }
}
