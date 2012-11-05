
package de.htwg.scaltex.latexdsl;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class LaTeXDSLStandaloneSetup extends LaTeXDSLStandaloneSetupGenerated{

	public static void doSetup() {
		new LaTeXDSLStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

