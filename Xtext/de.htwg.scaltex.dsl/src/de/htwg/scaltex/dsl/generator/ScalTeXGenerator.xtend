/*
 * generated by Xtext
 */
package de.htwg.scaltex.dsl.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IFileSystemAccess
import de.htwg.scaltex.dsl.scalTeX.Entity
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ScalTeXGenerator implements IGenerator {
	
	@Inject extension IQualifiedNameProvider
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		for(e: resource.allContents.toIterable.filter(typeof(Entity))) {
      		fsa.generateFile(
        		e.fullyQualifiedName.toString("/") + ".java",
        		e.compile)
    	}
	}

	def compile(Entity e) '''
		/* Auto generated java file */
		
		// you've written this:
		/*
		«e.text.toString()»
		*/
		«FOR f : e.text»
			"«f»"
		«ENDFOR»
	'''

}