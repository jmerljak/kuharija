package si.merljak.magistrska.client.utils;

import org.junit.Assert;
import org.junit.Test;

import si.merljak.magistrska.common.enumeration.Unit;

/**
 * Test for unit conversion.
 *
 * @author Jakob Merljak
 */
public class UnitConversionTest {

	@Test
	public void translateToMetric() {
		boolean toMetric = true;
		String messageMetricToMetric = "Converting metric unit to metric should return the same unit.";
		String messageNeutral = "Converting 'neutral' unit should return the same unit.";

		Assert.assertEquals(messageMetricToMetric, Unit.G, Calc.getUnit(Unit.G, toMetric));
		Assert.assertEquals(messageMetricToMetric, Unit.KG, Calc.getUnit(Unit.KG, toMetric));
		Assert.assertEquals(Unit.G, Calc.getUnit(Unit.OZ, toMetric));
		Assert.assertEquals(Unit.KG, Calc.getUnit(Unit.LB, toMetric));
		Assert.assertEquals(messageMetricToMetric, Unit.L, Calc.getUnit(Unit.L, toMetric));
		Assert.assertEquals(messageMetricToMetric, Unit.DL, Calc.getUnit(Unit.DL, toMetric));
		Assert.assertEquals(messageMetricToMetric, Unit.ML, Calc.getUnit(Unit.ML, toMetric));
		Assert.assertEquals(Unit.L, Calc.getUnit(Unit.PT, toMetric));
		Assert.assertEquals(Unit.DL, Calc.getUnit(Unit.CUP, toMetric));
		Assert.assertEquals(Unit.ML, Calc.getUnit(Unit.TABLESPOON, toMetric));
		Assert.assertEquals(Unit.ML, Calc.getUnit(Unit.TEASPOON, toMetric));
		Assert.assertEquals(messageMetricToMetric, Unit.C, Calc.getUnit(Unit.C, toMetric));
		Assert.assertEquals(Unit.C, Calc.getUnit(Unit.F, toMetric));
		Assert.assertEquals(messageNeutral, Unit.TIMES, Calc.getUnit(Unit.TIMES, toMetric));
		Assert.assertEquals(messageNeutral, Unit.PAIR, Calc.getUnit(Unit.PAIR, toMetric));
		Assert.assertEquals(messageNeutral, Unit.PINCH, Calc.getUnit(Unit.PINCH, toMetric));
		Assert.assertEquals(messageNeutral, Unit.SOME, Calc.getUnit(Unit.SOME, toMetric));
	}

	@Test
	public void translateToImperial() {
		boolean toMetric = false;
		String messageImperialToImperial = "Converting imperial unit to imperial should return the same unit.";
		String messageNeutral = "Converting 'neutral' unit should return the same unit.";

		Assert.assertEquals(Unit.OZ, Calc.getUnit(Unit.G, toMetric));
		Assert.assertEquals(Unit.LB, Calc.getUnit(Unit.KG, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.OZ, Calc.getUnit(Unit.OZ, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.LB, Calc.getUnit(Unit.LB, toMetric));
		Assert.assertEquals(Unit.PT, Calc.getUnit(Unit.L, toMetric));
		Assert.assertEquals(Unit.OZ, Calc.getUnit(Unit.DL, toMetric));
		Assert.assertEquals(Unit.TEASPOON, Calc.getUnit(Unit.ML, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.PT, Calc.getUnit(Unit.PT, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.CUP, Calc.getUnit(Unit.CUP, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.TABLESPOON, Calc.getUnit(Unit.TABLESPOON, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.TEASPOON, Calc.getUnit(Unit.TEASPOON, toMetric));
		Assert.assertEquals(Unit.F, Calc.getUnit(Unit.C, toMetric));
		Assert.assertEquals(messageImperialToImperial, Unit.F, Calc.getUnit(Unit.F, toMetric));
		Assert.assertEquals(messageNeutral, Unit.TIMES, Calc.getUnit(Unit.TIMES, toMetric));
		Assert.assertEquals(messageNeutral, Unit.PAIR, Calc.getUnit(Unit.PAIR, toMetric));
		Assert.assertEquals(messageNeutral, Unit.PINCH, Calc.getUnit(Unit.PINCH, toMetric));
		Assert.assertEquals(messageNeutral, Unit.SOME, Calc.getUnit(Unit.SOME, toMetric));
	}

	/**
	 * Test for converting amount to metric units.
	 *
	 * Note: there might be some confusion due to multiple non-metric units with the same name
	 * e.g. UK teaspoon, US teaspoon, 'metric' teaspoon ...
	 */
	@Test
	public void convertToMetric() {
		boolean toMetric = true;
		String messageMetricToMetric = "Converting metric unit to metric should return the same value.";
		String messageNeutral = "Converting 'neutral' unit should return the same value.";

		Assert.assertEquals(messageMetricToMetric, 4.3, Calc.calculateAmount(Unit.G, 4.3, toMetric), 0);
		Assert.assertEquals(messageMetricToMetric, 4.3, Calc.calculateAmount(Unit.KG, 4.3, toMetric), 0);
		Assert.assertEquals(120.0, Calc.calculateAmount(Unit.OZ, 4.3, toMetric), 1); // TODO check this
		Assert.assertEquals(1.95, Calc.calculateAmount(Unit.LB, 4.3, toMetric), 0.01);
		Assert.assertEquals(messageMetricToMetric, 4.3, Calc.calculateAmount(Unit.L, 4.3, toMetric), 0);
		Assert.assertEquals(messageMetricToMetric, 4.3, Calc.calculateAmount(Unit.DL, 4.3, toMetric), 0);
		Assert.assertEquals(messageMetricToMetric, 4.3, Calc.calculateAmount(Unit.ML, 4.3, toMetric), 0);
		Assert.assertEquals(2.03, Calc.calculateAmount(Unit.PT, 4.3, toMetric), 0.01);
		Assert.assertEquals(10.3, Calc.calculateAmount(Unit.CUP, 4.3, toMetric), 0.1);
		Assert.assertEquals(64.5, Calc.calculateAmount(Unit.TABLESPOON, 4.3, toMetric), 0.01);
		Assert.assertEquals(21.5, Calc.calculateAmount(Unit.TEASPOON, 4.3, toMetric), 0.01);
		Assert.assertEquals(messageMetricToMetric, 4.3, Calc.calculateAmount(Unit.C, 4.3, toMetric), 0);
		Assert.assertEquals(-15.39, Calc.calculateAmount(Unit.F, 4.3, toMetric), 0.01);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.TIMES, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.PAIR, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.PINCH, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.SOME, 4.3, toMetric), 0);
	}

	/**
	 * Test for converting amount to imperial units.
	 *
	 * Note: there might be some confusion due to multiple non-metric units with the same name
	 * e.g. UK teaspoon, US teaspoon, 'metric' teaspoon ...
	 */
	@Test
	public void convertToImperial() {
		boolean toMetric = false;
		String messageImperialToImperial = "Converting imperial unit to imperial should return the same value.";
		String messageNeutral = "Converting 'neutral' unit should return the same value.";

		Assert.assertEquals(0.15, Calc.calculateAmount(Unit.G, 4.3, toMetric), 0.01);
		Assert.assertEquals(9.48, Calc.calculateAmount(Unit.KG, 4.3, toMetric), 0.01);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.OZ, 4.3, toMetric), 0);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.LB, 4.3, toMetric), 0);
		Assert.assertEquals(9.09, Calc.calculateAmount(Unit.L, 4.3, toMetric), 0.01);
		Assert.assertEquals(1.79, Calc.calculateAmount(Unit.DL, 4.3, toMetric), 0.1);
		Assert.assertEquals(0.86, Calc.calculateAmount(Unit.ML, 4.3, toMetric), 0.01);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.PT, 4.3, toMetric), 0);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.CUP, 4.3, toMetric), 0);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.TABLESPOON, 4.3, toMetric), 0);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.TEASPOON, 4.3, toMetric), 0);
		Assert.assertEquals(39.74, Calc.calculateAmount(Unit.C, 4.3, toMetric), 0.01);
		Assert.assertEquals(messageImperialToImperial, 4.3, Calc.calculateAmount(Unit.F, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.TIMES, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.PAIR, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.PINCH, 4.3, toMetric), 0);
		Assert.assertEquals(messageNeutral, 4.3, Calc.calculateAmount(Unit.SOME, 4.3, toMetric), 0);
	}
}
