<!--
  Save this file in `/src/main/xslt`
-->
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="#all">

    <xsl:output method="html" encoding="utf-8" indent="yes"
                media-type="text/html" undeclare-prefixes="no" version="5.0" />

    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates select="catalog"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="catalog">
        <h1>Catalog</h1>
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre(s)</th>
                    <th>Publish date</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <xsl:apply-templates select="book"/>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template match="book">
        <tr>
            <td><xsl:value-of select="title"/></td>
            <td><xsl:value-of select="author"/></td>
            <td><xsl:value-of select="genre"/></td>
            <td><xsl:value-of select="publish_date"/></td>
            <td><xsl:value-of select="description"/></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>