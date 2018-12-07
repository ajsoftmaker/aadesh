package com.aadesh;

public class ApplicationContext {

	private static ApplicationContext instance = null;
	private AadeshWebConfiguration config;

	private ApplicationContext(AadeshWebConfiguration config) {
		this.config = config;
	}

	public static ApplicationContext init(AadeshWebConfiguration config) {
		instance = new ApplicationContext(config);
		return getInstance();
	}

	public static ApplicationContext getInstance() {
		if (instance == null) {
			throw new RuntimeException("Application Context is not initialized !");
		}

		return instance;
	}

	public AadeshWebConfiguration getConfig() {
		return config;
	}

}
