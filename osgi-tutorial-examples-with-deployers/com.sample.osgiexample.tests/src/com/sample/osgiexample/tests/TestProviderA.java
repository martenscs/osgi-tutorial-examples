package com.sample.osgiexample.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.sample.osgiexample.provider.PriceService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestProviderA {

	private BundleContext bundleContext = null;

	private PriceService serviceA = null;

	@Before
	public void init() {

		bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		serviceA = (PriceService) lookupNoThow(bundleContext,
				PriceService.class.getName(), String.format("(%s=%s)",
						"osgi.jndi.service.name", "priceServices/A"));

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
	public void testPriceLimit() {

		System.out.println("***************");

		int priceA = serviceA.getPrice();
		assertTrue("Price must be more than 0", priceA >= 0);
		assertTrue("Price must be less than 100", priceA <= 100);
		System.out.println("Price for company A: $" + priceA + " OK");

	}

	@Test
	public void testPriceChanging() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			int price1 = serviceA.getPrice();
			Thread.sleep(3500);
			int price2 = serviceA.getPrice();
			int price3 = serviceA.getPrice();

			// implementation of the Price A provider allowed price to be
			// unchanged,
			// but let the test define such situation as incorrect

			assertNotSame("Price must change", price1, price2);
			assertEquals("Price should be the same", price2, price3);
			System.out.println("Price for company A was changed from $"
					+ price1 + " to $" + price2 + " OK");

		}

	}

}
