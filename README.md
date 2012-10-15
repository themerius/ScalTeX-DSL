# ScalTeX-DSL

DSL Experiments

## Anforderungen an die DSL

Was soll alles möglich sein (DSL), was soll alles möglich sein insgesamt (Komination aus DSL und Frontend).

  * Ein Dokument mit einer vollwertigen Programmiersprache zu erstellen; d.h. man kann auch Fähigkeiten benutzen die z.B. über LaTeX hinaus gehen
  * Ähnliche Referenzierungsmöglichkeiten wie bei LaTeX
  * Automatische Kapitelnummerierung (und in Zukunft ggf. ähnliche Möglichkeiten wie Fußnoten)
  * Wahl des (haupt) Dokumenten-Templates
  * Wahl von verschiedenen Seiten-Templates
  * Wahl verschiedener Entities (aus Template), mit verschiedenen Fähigkeiten (z.B. Section, Text, Figure)
  * ggf. Mischung von Entities aus verschiedenen Templates (z.B. Tabelle aus Template A und Figure aus Template B)
  * "portable" Dokumente, also von Template A auf Template B umziehen, ohne am "Dokument-Code" etwas zu verändern. Welche Voraussetzungen bzw. Einschränkungen sind dort gegeben?

### Wie sähe der Idealfall aus?

	Section
		Überschrift
	
	Text
		Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
		sed diam nonumy eirmod tempor invidunt ut labore et dolore
		magna aliquyam erat, sed diam voluptua. At vero eos et accusam et
		
	Subsection
		Unterüberschrift
	
	Text
		Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
		sed diam nonumy eirmod tempor invidunt ut labore et dolore
		magna aliquyam erat, sed diam voluptua. At vero eos et accusam et
		
		Auf Abb. picName.referenceNumber kann man erkennen…

	PythonScript named pyScriptName
		from matplotlib import pyplot
		from scaltex import return_to_document
		pyplot.plot(range(10))
		pic = pyplot.savefig("pic.png")  # Achtung Vereinfachung!
		return_to_document(pic)

	Figure named picName
		src = pyScriptName  # oder z.B. auch möglich "/home/pic.png"
		descr = "Beschreibung des Bildes"

Konfiguration während, davor oder danach? Trennung von Inhalt und Konfigurationen, sollte gegeben sein!

  * Trennung Pros: klare Struktur im Text, keine Unruhe durch Informationsüberflut, "Dokument-Code" wird portabler, höherer Entkopplungsgrad (die Konfiguration gibt z.B. die Struktur wieder und die Struktur kann leichter verändert werden beispielsweise die Reihenfolge von Textbausteinen oder Kapiteln)
  * Trennung Cons: Wenn der Benutzer einen (Konfigurations-)Fehler in einem Abschnitt gefunden hat, wird er dort auch suchen. (Falls man sich einen visuellen Editor dazu vorstellt, müsste man ggf. an mehreren Stellen suchen um etwas zu verändern.)

**Möglichkeiten**

1. während:

		Text named txtName with {grid: 1110, …}
			Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
			sed diam nonumy eirmod tempor invidunt ut labore et dolore
			magna aliquyam erat, sed diam voluptua. At vero eos et accusam et
	
	oder
	
		Text {
			named txtName
			with {
				grid: 1110,
				…
			}
		}
			Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
			sed diam nonumy eirmod tempor invidunt ut labore et dolore
			magna aliquyam erat, sed diam voluptua. At vero eos et accusam et

2. davor:

   Reihenfolge könnte durch `config` bestimmt werden und hätte keine Änderung an der gesamten Dokumentenstruktur zu Folge.

		config = {
			txtName: {
				grid: 1110, …
			},
			…
		}
		
		…

		Text named txtName
			Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
			sed diam nonumy eirmod tempor invidunt ut labore et dolore
			magna aliquyam erat, sed diam voluptua. At vero eos et accusam et

3. danach bzw. angehängt:

		Text
			Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
			sed diam nonumy eirmod tempor invidunt ut labore et dolore
			magna aliquyam erat, sed diam voluptua. At vero eos et accusam et
			
			[txtName] with {grid: 1110, …}

## Vor- und Nachteile bzw. Vergleich

* **Xtext**
	+ Hohe Flexibilität bei der Grammatik
	+ Kann jede Programmier- oder Markupsprache generieren
	+ Wenn Domäne sehr markant, also keine allgemeinen Sprachkonstrukte braucht, sehr ausdrucksstark bzw. lohnt sich der Aufwand
	+ Sehr gutes Toolkit
	+ Unittests auf Grammatik (und Generierung?) enthalten
	- General Purpose Konstrukte sind aufwendig zu erstellen, da sie explizit gebaut werden müssen und *nicht* schon vorhanden sind
* **Scala**
	+ Es ist noch innerhalb der DSL normales Scala nutzbar
	+ Für general Purpose Sprache sehr gute DSL-Fähigkeiten
	- Grammatik nicht so flexibel und frei