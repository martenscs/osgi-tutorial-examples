package com.sample.osgiexample.stockanalyser;

import com.sample.osgiexample.providera.PriceAService;
import com.sample.osgiexample.providerb.PriceBService;

public interface StockAnalyserService {

	void setServiceA(PriceAService serviceA);

	void setServiceB(PriceBService serviceB);

	double getAverageValue();

	int getMinPrice();

	int getMaxPrice();

}
