package br.com.rr.ocorrencias.controller;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rr.ocorrencias.model.Ocorrencia;

@RestController
@RequestMapping("ocorrencias")
public class OcorrenciaController {

	@GetMapping("{fragment}")
	public ResponseEntity<Ocorrencia> getOcurrencies(@PathVariable("fragment") String fragment)
			throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(7);
		List<Future<Integer>> futures = new ArrayList<>();

		long now = System.currentTimeMillis();
		for (int i = 1; i <= 7; i++) {
			FindOcurrenciesInFileCallable c = new FindOcurrenciesInFileCallable(fragment, i);
			futures.add(executor.submit(c));
		}

		int sum = 0;
		for (Future<Integer> f : futures) {
			sum += f.get();
		}
		
		long duration = System.currentTimeMillis() - now;
		Ocorrencia occurrency = new Ocorrencia(sum, duration);		
		return new ResponseEntity<Ocorrencia>(occurrency, HttpStatus.OK);
	}

	private class FindOcurrenciesInFileCallable implements Callable<Integer> {

		private String fragment;
		private Integer index;

		public FindOcurrenciesInFileCallable(String fragment, Integer index) {
			this.fragment = fragment;
			this.index = index;
		}

		@Override
		public Integer call() throws Exception {
			ClassLoader classLoader = getClass().getClassLoader();
			Path filePath = Paths.get(classLoader.getResource(".").toURI().getPath(), "words_" + index + ".csv");

			Long count = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1)
					.parallelStream()
					.map(line -> line.split("\\s+"))
					.flatMap(Arrays::stream).parallel()
					.filter(w -> w.indexOf(fragment) >= 0).count();

			return count.intValue();
		}
	}
}
