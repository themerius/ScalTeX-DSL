
package de.htwg.scaltex.dsl;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class ScalTeXStandaloneSetup extends ScalTeXStandaloneSetupGenerated{

	public static void doSetup() {
		new ScalTeXStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

