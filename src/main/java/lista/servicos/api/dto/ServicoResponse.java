package lista.servicos.api.dto;


import lista.servicos.domain.ServicoCategoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServicoResponse {
    public Long id;
    public String nome;
    public String telefone;
    public ServicoCategoria categoria;
    public String descricao;
    public OffsetDateTime criadoEm;
}

