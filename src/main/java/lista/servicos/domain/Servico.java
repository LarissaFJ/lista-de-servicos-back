package lista.servicos.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "servicos")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotBlank @Pattern(regexp = "\\d{8,15}")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ServicoCategoria categoria;

    @NotBlank @Size(max = 500)
    private String descricao;

    @Column(nullable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

}
