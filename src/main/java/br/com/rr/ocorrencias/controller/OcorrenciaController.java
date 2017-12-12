package br.com.rr.ocorrencias.controller;

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

@RestController
@RequestMapping("ocorrencias")
public class OcorrenciaController {

	@GetMapping("{fragment}")
	public ResponseEntity<?> getOcurrencies(@PathVariable("fragment") String fragment)
			throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(7);
		List<Future<Integer>> futures = new ArrayList<>();

		for (int i = 1; i <= 7; i++) {
			FindInFileOcurrenciesCallable c = new FindInFileOcurrenciesCallable(fragment, i);
			futures.add(executor.submit(c));
		}

		int sum = 0;
		for (Future<Integer> f : futures) {
			sum += f.get();
		}

		return new ResponseEntity<Integer>(sum, HttpStatus.OK);
	}

	private class FindInFileOcurrenciesCallable implements Callable<Integer> {

		private String fragment;
		private Integer index;

		public FindInFileOcurrenciesCallable(String fragment, Integer index) {
			this.fragment = fragment;
			this.index = index;
		}

		@Override
		public Integer call() throws Exception {
			Path filePath = Paths.get(".", "words_" + index + ".csv");

			Long count = Files.readAllLines(filePath).parallelStream()
					.map(line -> line.split("\\s+"))
					.flatMap(Arrays::stream).parallel()
					.filter(w -> w.indexOf(fragment) >= 0).count();

			return count.intValue();
		}
	}
}
