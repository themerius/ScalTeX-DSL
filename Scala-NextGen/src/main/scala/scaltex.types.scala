package scaltex.types {

import scaltex.buildtools.{Areal, EntityBinding => EB, Page}

  package academic {

    trait EntityBinding extends EB {
      def § (h: String)(implicit areal: Areal)
      def §§ (h: String)(implicit areal: Areal)
      def §§§ (h: String)(implicit areal: Areal)
      def §§§§ (h: String)(implicit areal: Areal)
      def txt (t: String)(implicit areal: Areal)
      def figure (src: String, desc: String)(implicit areal: Areal)
    }

    trait Pages {
      val A4: Page
      val A4horizontal: Page
    }

    package areals {
      trait TitlePage
      trait TableOfContents
      trait Document
    }

  }

}