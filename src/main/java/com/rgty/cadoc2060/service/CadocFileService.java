package com.rgty.cadoc2060.service;

import com.rgty.cadoc2060.domain.CadocFile;
import com.rgty.cadoc2060.exception.CadocFileNotFoundException;
import com.rgty.cadoc2060.repository.CadocFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CadocFileService {

    private final CadocFileRepository cadocFileRepository;

    public CadocFile addFile(CadocFile cadocFile){
        log.info("Iniciando a criação do arquivo: {}", cadocFile.getId());
        return cadocFileRepository.save(cadocFile);
    }

    public List<CadocFile> getFiles(){
        log.info("Listando os arquivos Cadoc2060");
        return cadocFileRepository.findAll();
    }

    public CadocFile getCadocById(Long id){
        log.info("Buscando CadocFile pelo Id: {}", id);
        return cadocFileRepository.findById(id).
                orElseThrow(() -> new CadocFileNotFoundException("Cadoc com o id: {}", id));
    }

}
