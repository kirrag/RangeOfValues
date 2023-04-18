package ru.netology;

public class MaxCharSequence implements Runnable {
	//private Thread thread;
	private String text;

	MaxCharSequence(String text) {
		//thread = new Thread();
		this.text = text;
	}

	@Override
	public void run() {
		int maxSize = 0;
		for (int i = 0; i < text.length(); i++) {
			for (int j = 0; j < text.length(); j++) {
				if (i >= j) {
					continue;
				}
				boolean bFound = false;
				for (int k = i; k < j; k++) {
					if (text.charAt(k) == 'b') {
						bFound = true;
						break;
					}
				}
				if (!bFound && maxSize < j - i) {
					maxSize = j - i;
				}
			}
		}
		System.out.println(text.substring(0, 100) + " -> " + maxSize);
	}
}
