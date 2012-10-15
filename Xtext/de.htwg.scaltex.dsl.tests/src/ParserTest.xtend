import org.eclipse.xtext.junit4.XtextRunner
import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.InjectWith
import de.htwg.scaltex.dsl.ScalTeXInjectorProvider
import static org.junit.Assert.*
import com.google.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import de.htwg.scaltex.dsl.scalTeX.Scaltex
import org.junit.Test

@InjectWith(typeof(ScalTeXInjectorProvider))
@RunWith(typeof(XtextRunner))
class ParserTest {
  @Inject
  ParseHelper<Scaltex> parser

  @Test 
  def void parseDomainmodel() {
    val model = parser.parse(
      '''Section """
           Text...
      """''')
    val entity = model.entities.head
    assertEquals(entity.name, "Section")
    assertEquals(entity.text.head, '''"""
           Text...
      """'''.toString())
  }
}