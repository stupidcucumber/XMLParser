<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Scientists</h2>
                <xsl:for-each select="scientists/scientist">
                    <div>
                        <p>Name: <xsl:value-of select="name"/></p>
                        <p>ID: <xsl:value-of select="id"/></p>
                        <p>Degree: <xsl:value-of select="degree"/></p>
                        <p>Fields of study: <xsl:value-of select="fields"/></p>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>