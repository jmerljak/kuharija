package si.merljak.magistrska.common.enumeration;

public enum Unit {
	G(UnitType.MASS),
	KG(UnitType.MASS),
	OZ(UnitType.MASS),
	LB(UnitType.MASS),

	L(UnitType.VOLUME),
	DL(UnitType.VOLUME),
	ML(UnitType.VOLUME),
 	PT(UnitType.VOLUME),
	CUP(UnitType.VOLUME),
	TABLESPOON(UnitType.VOLUME),
	TEASPOON(UnitType.VOLUME),

	C(UnitType.TEMPERATURE),
	F(UnitType.TEMPERATURE),

	PIECE(UnitType.PIECE),
	PINCH(UnitType.UNCOUNTABLE);
	

	private UnitType unitType;

	private Unit(UnitType unitType) {
		this.unitType = unitType;
	}

	public UnitType getUnitType() {
		return unitType;
	}

	public Unit getMetricUnit() {
		switch (this) {
			case OZ:
				return G;
			case LB:
				return KG;
			case F:
				return C;
			case PT:
				return L;
			case CUP:
				return DL;
			case TABLESPOON:
			case TEASPOON:
				return ML;
			default:
				return this;
		}
	}

	public Number convertToMetric(double amount) {
		switch (this) {
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

	public Unit getNonMetricUnit() {
		switch (this) {
			case KG:
				return LB;
			case G:
				return OZ;
			case L:
				return PT;
			case DL:
				return OZ;
			case ML:
				return TEASPOON;
			case C:
				return F;
			default:
				return this;
		}
	}

	public Number convertToNonMetric(double amount) {
		switch (this) {
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
