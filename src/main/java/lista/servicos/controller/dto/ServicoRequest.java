package lista.servicos.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lista.servicos.domain.ServicoCategoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServicoRequest {
    @NotBlank
    @Size(max = 120)
    public String nome;

    @NotBlank @Pattern(regexp = "\\d{8,15}")
    public String telefone;

    public ServicoCategoria categoria;

    @NotBlank @Size(max = 500)
    public String descricao;
}
