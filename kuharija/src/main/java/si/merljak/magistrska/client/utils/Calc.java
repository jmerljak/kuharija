package si.merljak.magistrska.client.utils;

import si.merljak.magistrska.common.enumeration.Unit;

/**
 * Utility class for conversion between units (e.g. imperial to metric and vice versa).
 * 
 * Note: This is not very smart solution. Functionality is limited and 
 * we should find another (better) way to do conversions.
 * 
 * @author Jakob Merljak
 *
 */
public class Calc {

	public static Unit getUnit(Unit unit, boolean toMetric) {
		if (toMetric) {
			switch (unit) {
			case OZ:
				return Unit.G;
			case LB:
				return Unit.KG;
			case F:
				return Unit.C;
			case PT:
				return Unit.L;
			case CUP:
				return Unit.DL;
			case TABLESPOON:
			case TEASPOON:
				return Unit.ML;
			default:
				return unit;
			}
		} else {
			switch (unit) {
			case KG:
				return Unit.LB;
			case G:
				return Unit.OZ;
			case L:
				return Unit.PT;
			case DL:
				return Unit.OZ;
			case ML:
				return Unit.TEASPOON;
			case C:
				return Unit.F;
			default:
				return unit;
			}
		}
	}

	public static Double calculateAmount(Unit unit, Double amount, boolean toMetric) {
		if (amount == null) {
			return null;
		} else if (toMetric) {
			switch (unit) {
			case OZ:
				return amount * 28;
			case LB:
				return amount * 0.45359237;
			case F:
				return (amount - 32.0) * 5 / 9;
			case PT:
				return amount * 0.473;
			case CUP:
				return amount * 2.4;
			case TABLESPOON:
				return amount * 15;
			case TEASPOON:
				return amount * 5;
			default:
				return amount;
			}
		} else {
			switch (unit) {
			case KG:
				return amount / 0.45359237;
			case G:
				return amount / 28;
			case L:
				return amount / 0.473;
			case DL:
				return amount / 2.4;
			case ML:
				return amount / 15;
			case C:
				return amount * 9.0 / 5 + 32;
			default:
				return amount;
			}
		}
	}
}
