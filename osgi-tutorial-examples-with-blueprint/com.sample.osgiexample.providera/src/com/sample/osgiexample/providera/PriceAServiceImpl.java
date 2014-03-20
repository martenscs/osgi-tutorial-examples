package com.sample.osgiexample.providera;

import com.sample.osgiexample.provider.PriceService;

public class PriceAServiceImpl implements PriceService {

	private static final int REFRESH_RATE = 3 * 1000;
	private int price;
	private long time = 0;

	public int getPrice() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - time > REFRESH_RATE) {
			price = (int) (Math.random() * 100);
			time = currentTime;
		}
		return price;
	}
}