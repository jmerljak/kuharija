package si.merljak.magistrska.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class TableCreator {

	/** Recreates tables from model. */
	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

}
