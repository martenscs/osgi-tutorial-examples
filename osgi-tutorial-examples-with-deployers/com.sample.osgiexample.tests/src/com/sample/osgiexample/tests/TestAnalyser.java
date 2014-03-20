package com.sample.osgiexample.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.sample.osgiexample.provider.PriceService;
import com.sample.osgiexample.stockanalyser.StockAnalyserService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestAnalyser {

	private BundleContext bundleContext = null;

	private StockAnalyserService analyzerService = null;

	private PriceService serviceA = null;

	private PriceService serviceB = null;

	@Before
	public void init() {

		bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		startBundles();

		analyzerService = (StockAnalyserService) lookupNoThow(bundleContext,
				StockAnalyserService.class.getName(), String.format("(%s=%s)",
						"osgi.jndi.service.name",
						"priceServices/stockanalyserService"));

		serviceA = (PriceService) lookupNoThow(bundleContext,
				PriceService.class.getName(), String.format("(%s=%s)",
						"osgi.jndi.service.name", "priceServices/A"));

		serviceB = (PriceService) lookupNoThow(bundleContext,
				PriceService.class.getName(), String.format("(%s=%s)",
						"osgi.jndi.service.name", "priceServices/B"));

		if (analyzerService == null)
			throw new RuntimeException();

	}

	private void startBundles() {
		Bundle[] rr = bundleContext.getBundles();

		List<Bundle> providerBundles = new ArrayList<Bundle>();
		List<Bundle> blueprintBundles = new ArrayList<Bundle>();

		for (int i = 0; i < rr.length; i++) {
			if (rr[i].getSymbolicName().startsWith("org.apache.aries")) {
				blueprintBundles.add(rr[i]);
			} else if (rr[i].getSymbolicName().startsWith(
					"com.sample.osgiexample.provider")) {
				providerBundles.add(rr[i]);
			}
		}

		boolean isStarted = true;
		for (Bundle bundle : blueprintBundles) {
			try {
				int state = bundle.getState();
				bundle.start();
				if (isStarted)
					isStarted = (bundle.getState() == state);
				System.out.println(isStarted);
			} catch (BundleException e) {

			}
		}
		if (!isStarted) {
			for (Bundle bundle : providerBundles) {
				try {
					bundle.start();
				} catch (BundleException e) {
				}
			}
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static <T> T lookupNoThow(final BundleContext ctx,
			final String clazz, final String filter) {
		T inst = null;
		try {
			ServiceReference[] refs = ctx.getServiceReferences(clazz, filter);
			if (refs == null || refs.length == 0) {

			} else if (refs.length > 1) {

			}
			if (refs != null)
				inst = (T) ctx.getService(refs[0]);
		} catch (InvalidSyntaxException x) {

		}

		return inst;
	}

	@Test
	public void testCheckAverageValue() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			Thread.sleep(3500);

			int priceA = serviceA.getPrice();
			int priceB = serviceB.getPrice();
			double avg = analyzerService.getAverageValue();
			double expectedAvgVal = (double) (priceA + priceB) / 2;
			assertEquals(expectedAvgVal, avg, 0);
			System.out.println("Price A = " + priceA + " Price B = " + priceB);
			System.out.println("Stock average value: " + avg + " OK");

		}

	}

	@Test
	public void testCheckMinPrice() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			Thread.sleep(3500);

			int priceA = serviceA.getPrice();
			int priceB = serviceB.getPrice();
			int min = analyzerService.getMinPrice();
			int expectedMin = Math.min(priceA, priceB);
			assertEquals(expectedMin, min);
			System.out.println("Price A = " + priceA + " Price B = " + priceB);
			System.out.println("Stock min price: " + min + " OK");

		}

	}

	@Test
	public void testCheckMaxPrice() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			Thread.sleep(3500);
			int priceA = serviceA.getPrice();
			int priceB = serviceB.getPrice();
			int max = analyzerService.getMaxPrice();
			int expectedMax = Math.max(priceA, priceB);
			assertEquals(expectedMax, max);
			System.out.println("Price A = " + priceA + " Price B = " + priceB);
			System.out.println("Stock max price: " + max + " OK");

		}

	}

}