<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Classes</h2>
                <xsl:for-each select="classes/class">
                        <div>
                            <p>Name: <xsl:value-of select="name"/></p>
                            <p>ID: <xsl:value-of select="id"/></p>
                            <p>Description: <xsl:value-of select="description"/></p>
                            <p>Lecturers: <xsl:value-of select="lecturers"/></p>
                            <p>Practicants: <xsl:value-of select="practicants"/></p>
                            <p>Days available: <xsl:value-of select="days"/></p>
                        </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>