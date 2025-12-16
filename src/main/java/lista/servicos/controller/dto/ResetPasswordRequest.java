package lista.servicos.controller.dto;

public record ResetPasswordRequest(
        String email,
        String newPassword
) {}

