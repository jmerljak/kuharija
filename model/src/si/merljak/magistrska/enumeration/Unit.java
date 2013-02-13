package si.merljak.magistrska.enumeration;

public enum Unit {
	G(UnitType.MASS),
	L(UnitType.VOLUME),
	C(UnitType.TEMPERATURE),
	K(UnitType.TEMPERATURE),
	PIECE(UnitType.PIECE),
	PINCH(UnitType.UNCOUNTABLE);
	

	private UnitType unitType;

	private Unit(UnitType unitType) {
		this.unitType = unitType;
	}

	public UnitType getUnitType() {
		return unitType;
	}
}
