package com.hoopladigital.guice;

import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

public class SampleMyBatisModule extends MyBatisModule {

	@Override
	protected void initialize() {
//		addInterceptorClass(XRayMybatisInterceptor.class);
		bindTransactionFactoryType(JdbcTransactionFactory.class);
		bindDataSourceProviderType(PooledDataSourceProvider.class);
		addMapperClasses("com.hoopladigital.mapper");
		addSimpleAliases("com.hoopladigital.bean");
		final String environment = System.getProperty("environment", "LOCAL");
		bindConstant().annotatedWith(Names.named("mybatis.environment.id")).to(environment);
	}

}
