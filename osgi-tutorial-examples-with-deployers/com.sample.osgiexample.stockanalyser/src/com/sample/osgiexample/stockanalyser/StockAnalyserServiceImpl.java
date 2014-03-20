package com.sample.osgiexample.stockanalyser;

import org.osgi.framework.Bundle;

import com.sample.osgiexample.provider.PriceService;

public class StockAnalyserServiceImpl implements StockAnalyserService {

	private PriceService serviceA;

	private PriceService serviceB;

	private Bundle bundle;

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

	public synchronized void setServiceA(PriceService service) {
		this.serviceA = service;

	}

	public synchronized void setServiceB(PriceService service) {
		this.serviceB = service;

	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

}