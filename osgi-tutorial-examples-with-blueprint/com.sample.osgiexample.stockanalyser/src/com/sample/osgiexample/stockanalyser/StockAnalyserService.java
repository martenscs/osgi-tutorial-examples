package com.sample.osgiexample.stockanalyser;

import com.sample.osgiexample.provider.PriceService;

public interface StockAnalyserService {

	void setServiceA(PriceService serviceA);

	void setServiceB(PriceService serviceB);

	double getAverageValue();

	int getMinPrice();

	int getMaxPrice();

}
