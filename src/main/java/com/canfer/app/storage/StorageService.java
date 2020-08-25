package com.canfer.app.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.FacturaNotaComplemento;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	Path init(FacturaNotaComplemento factura);

	void store(MultipartFile file, Path filename);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
