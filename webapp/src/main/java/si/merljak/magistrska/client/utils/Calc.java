package si.merljak.magistrska.client.utils;

import si.merljak.magistrska.common.enumeration.Unit;

public class Calc {

	public static Unit getMetricUnit(Unit unit) {
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
	}

	public static Number convertToMetric(Unit unit, double amount) {
		switch (unit) {
			case OZ:
				return amount * 28;
			case LB:
				return amount * 0.45359237;
			case F:
				return (amount - 32.0 ) * 5 / 9;
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
	}

	public static Unit getNonMetricUnit(Unit unit) {
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

	public static Number convertToNonMetric(Unit unit, double amount) {
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
