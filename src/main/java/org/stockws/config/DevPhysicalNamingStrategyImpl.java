package org.stockws.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class DevPhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl {

	private static final long serialVersionUID = 1L;
	
	private final PhysicalNamingStrategyStandardImpl INSTANCE = new PhysicalNamingStrategyStandardImpl();


	@Override
	public Identifier toPhysicalTableName(Identifier name,
			JdbcEnvironment context) {
		return INSTANCE.toPhysicalTableName(new Identifier("dev." + name.getText(), name.isQuoted()), context);
	}

}
