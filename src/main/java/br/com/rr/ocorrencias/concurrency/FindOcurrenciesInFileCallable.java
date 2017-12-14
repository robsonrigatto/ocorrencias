package br.com.rr.ocorrencias.concurrency;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class FindOcurrenciesInFileCallable implements Callable<Integer> {

	private String fragment;
	private Integer index;

	public FindOcurrenciesInFileCallable(String fragment, Integer index) {
		this.fragment = fragment;
		this.index = index;
	}

	@Override
	public Integer call() throws Exception {
		Path filePath = Paths.get("/tmp", "files", "words_" + index + ".csv");
		Long count = Files.readAllLines(filePath, StandardCharsets.UTF_8)
				.parallelStream()
				.map(line -> line.split("\\s+"))
				.flatMap(Arrays::stream).parallel()
				.filter(w -> w.indexOf(fragment) >= 0).count();

		return count.intValue();
	}
}
