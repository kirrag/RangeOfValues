package ru.netology;

import java.util.Random;
import java.lang.Runnable;
import java.util.List;
import java.util.ArrayList;

public class Main {

	public static String generateText(String letters, int length) {
		Random random = new Random();
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < length; i++) {
			text.append(letters.charAt(random.nextInt(letters.length())));
		}
		return text.toString();
	}

	public static void main(String[] args) throws InterruptedException {
		String[] texts = new String[25];
		for (int i = 0; i < texts.length; i++) {
			texts[i] = generateText("aab", 30_000);
		}

		List<Thread> threads = new ArrayList<>();

		long startTs = System.currentTimeMillis(); // start time

		for (String text : texts) {
			Runnable maxCharSequence = new MaxCharSequence(text);
			threads.add(new Thread(maxCharSequence));
		}

		threads.forEach(thread -> thread.start());
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		long endTs = System.currentTimeMillis(); // end time

		System.out.println("Time: " + (endTs - startTs) + "ms");
	}
}
