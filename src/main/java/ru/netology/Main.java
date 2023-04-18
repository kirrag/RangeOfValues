package ru.netology;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.sun.jdi.request.WatchpointRequest;

public class Main {

	public static String generateText(String letters, int length) {
		Random random = new Random();
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < length; i++) {
			text.append(letters.charAt(random.nextInt(letters.length())));
		}
		return text.toString();
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		String[] texts = new String[25];
		for (int i = 0; i < texts.length; i++) {
			texts[i] = generateText("aab", 30_000);
		}

		final ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);

		final List<Future<Integer>> tasks = new ArrayList<>();

		int max = 0;


		long startTs = System.currentTimeMillis(); // start time
		
		for (String text: texts) {
			final Callable<Integer> maxCharSequence = new MaxCharSequence(text);
			tasks.add(threadPool.submit(maxCharSequence));
		}

		for (Future<Integer> task: tasks) {
			final int resultOfTask = task.get();
			if (resultOfTask > max ) max = resultOfTask;
		}

		System.out.println("Максимальный интервал значений: " + max);

		long endTs = System.currentTimeMillis(); // end time

		System.out.println("Time: " + (endTs - startTs) + "ms");

		threadPool.shutdown();
	}
}
