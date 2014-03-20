package com.sample.osgiexample.stockanalyser;

import com.sample.osgiexample.providera.PriceAService;
import com.sample.osgiexample.providerb.PriceBService;

public class StockAnalyserServiceImpl implements StockAnalyserService {

	private PriceAService serviceA;

	private PriceBService serviceB;

	public double getAverageValue() {

		int priceA = serviceA.getPrice();
		int priceB = serviceB.getPrice();
		return (double) (priceA + priceB) / 2;

	}

	public int getMinPrice() {

		int priceA = serviceA.getPrice();
		int priceB = serviceB.getPrice();
		return priceA <= priceB ? priceA : priceB;

	}

	public int getMaxPrice() {

		int priceA = serviceA.getPrice();
		int priceB = serviceB.getPrice();
		return priceA >= priceB ? priceA : priceB;

	}

	public synchronized void setServiceA(PriceAService service) {
		this.serviceA = service;

	}

	public synchronized void setServiceB(PriceBService service) {
		this.serviceB = service;

	}

}