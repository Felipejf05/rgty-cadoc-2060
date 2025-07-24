package com.rgty.cadoc2060.common.helper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public final class Messages {
    public static final String FILE_NOT_FOUND = "Arquivo não encontrado na base de dados";
    public static final String FILE_ALREADY_EXISTS ="O arquivo enviado já existe. Considere modificar o nome do arquivo, mantendo o padrão de formatação";
}
