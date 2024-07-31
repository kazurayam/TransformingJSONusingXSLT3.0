# TransformingJSONusingXSLT3.0
https://www.saxonica.com/papers/xmlprague-2016mhk.pdf

## Getting started with XSLT in Java, Gradle

https://gist.github.com/clauret/57607655ca58341ead47ebe454be7e93

https://www.baeldung.com/java-extensible-stylesheet-language-transformations

## Which distribution of Saxon?

According to
https://www.saxonica.com/documentation9.5/conformance/xslt30.html

>Saxon-HE does not implement any XSLT 3.0 features.
>Saxon-PE implements a selection of XSLT 3.0 (and XPath 3.0) features, with the exception of schema-awareness and streaming.

I tried to get Saxon-PE, but I couldn't.

```
Execution failed for task ':app:compileJava'.
> Could not resolve all files for configuration ':app:compileClasspath'.
   > Could not find com.saxonica:Saxon-PE:12.2.
     Searched in the following locations:
       - https://repo.maven.apache.org/maven2/com/saxonica/Saxon-PE/12.2/Saxon-PE-12.2.pom
     Required by:
         project :app
```

https://www.saxonica.com/download/java.xml

>To use Saxon-PE or Saxon-EE you need a license key. If you don't have a current license you can request a 30-day evaluation license.

So, I need to get a license key. 

XSLT3.0を使うにはSaxon-Professional Editionが必要だとのこと。
Saxon-PEは無料ではない。30日間のトライアル期間を過ぎてからも使えるには使えるのだが、無作為に選ばれたタイミングで動かなくなる、とか出力の中に*********が挿入されるとかのことが行われる。嫌がらせみたいなものか。まあ、本番利用するのでなければ受忍限度の内だと思うが。

licenseをもらった。licファイルをsrc/test/resourcesディレクトリに配置した。これでclasspathにのるはず。

これでもCould not find com.saxonica:Saxon-PE:12.2のエラーが消えない。12.1と12.0を試したがやはり失敗。 誰も試していないのかもしれない。

Saxon-PEはMaven Centralレポジトリに公開されていない。Saxonicaが自分でMavenレポジトリを立ててそこで公開していた。次のようにbuild.gradleを修正した。

```
repositories {
// Use Maven Central for resolving dependencies.
mavenCentral()
maven {
url 'https://dev.saxonica.com/maven/'
}
}

dependencies {
// https://mvnrepository.com/artifact/com.saxonica/Saxon-PE
testImplementation 'com.saxonica:Saxon-PE:12.5'
```

うーん。まだだめだ。gradle testするとpassするのだが、出力されたファイルの中を見ると期待通りでない。XSLT3.0のinitial-templateが有効になっていない。


IntelliJ IDEAのProject Structureを見ると、Saxon-PE 12.5が参照できていることは間違いない。なのにXSLT3.0が有効になっていないのはなぜ？何かSaxonにconfigurationを与えなければならないのかもしれない。

https://www.saxonica.com/documentation9.5/conformance/xslt30.html

>XSLT 3.0 features are not available unless explicitly requested. The request can be by setting -xsltversion:3.0 on the command line, by calling setXsltLanguageVersion() on the XsltCompiler object, or by use of the configuration setting FeatureKeys.XSLT_VERSION. Setting version="3.0" on the xsl:stylesheet element is recommended, but is not sufficient on its own.


うーん、敷居が高い。もうやめた。
