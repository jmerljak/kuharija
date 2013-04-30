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
	PAIR(UnitType.PIECE),
	PINCH(UnitType.UNCOUNTABLE),
	SOME(UnitType.UNCOUNTABLE);

	private UnitType unitType;

	private Unit(UnitType unitType) {
		this.unitType = unitType;
	}

	public UnitType getUnitType() {
		return unitType;
	}
}
