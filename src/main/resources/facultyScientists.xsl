<?xml version="1.0" encoding="UTF-8"?>

<!--
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
-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="/*">
        <html>
            <head>
                <title>Scientists</title>
                <link href="styles.css" rel="stylesheet" media="all" />
            </head>
            <body style="text-alignment: center;">
                <table><xsl:apply-templates select="scientist"/></table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="scientist[1]">
        <tr><xsl:apply-templates select="*" mode="header"/></tr>
        <xsl:call-template name="standardRow"/>
    </xsl:template>

    <xsl:template match="scientist" name="standardRow">
        <tr><xsl:apply-templates select="*"/></tr>
    </xsl:template>

    <xsl:template match="scientist/*">
        <td><xsl:apply-templates select="node()"/></td>
    </xsl:template>

    <xsl:template match="scientist/*" mode="header">
        <th><xsl:value-of select="name()"/></th>
    </xsl:template>
</xsl:stylesheet>