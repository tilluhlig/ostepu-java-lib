# SQaLibur-OSTEPU-lib

Diese Bibliothek bietet die Möglichkeit, ein Java-Servlet als Komponente in OSTEPU einzubinden und in eine Veranstaltung als ``Verarbeitung`` einzutragen. Dazu müssen die Dateien ``data/Commands.json`` und
``data/Component.json`` entsprechend im Servlet ausgefüllt sein.

## Systembefehle
Diese Befehle werden größtenteils von OSTEPU gefordert, um überhaupt eine Anbindung an das Hauptsystem zur erhalten.

### POST /control
Dieser Befehl nimmt die Komponentenkonfiguration vom Installationsassistenten von OSTEPU entgegen und schreibt damit die ``CConfig.json`` von SQaLibur.

### GET /control
Damit lässt sich die aktuelle ``CConfig.json`` von SQaLibur abfragen und damit die momentane Konfiguration der Komponente.

### GET /component
Diese Anfrage liefert den Inhalt der ``Component.json`` von SQaLibur und damit die statischen Festlegungen der Komponente, welche zudem etwas eingeschränkter von ``GET /info/commands`` und ``GET /info/links`` ausgegeben werden.

### GET /info/commands
Dieser Befehl liefert eine Liste der von SQaLibur angebotenen Aufrufe und liest dazu die ``Commands.json`` aus.

### GET /info/links
Hiermit lässt sich eine Liste der von SQaLibur ausgehenden Anbindungen bestimmen, also die Aufrufe, welche SQaLibur an OSTEPU richten möchte.

### GET /info/de
Man erhält eine kurze Beschreibung der Komponente in Deutsch  mittels dieses Befehls.

### GET /help/de/...
Für die Hilfeseiten von SQaLibur als Erweiterung in der Kursverwaltung oder als Verarbeitung beim Erstellen einer Übungsserien werden über die Hilfebefehle weitere Beschreibungen und Bilder angeboten.

Die von dieser Bibliothek angebotenen Funktionen müssen im Hauptprojekt des Webservice in der ``web.xml`` zugänglich gemacht werden.

Dieser Servlet-Eintrag und das Mapping dienen lediglich der Installation unseres Servlet als Komponente in OSTEPU. Zudem sind nun alle Dateien im Unterordner
``help/`` als Hilfedateien für das Hauptsystem verfügbar.

``` XML
<servlet>
    <servlet-name>CCONFIG</servlet-name>
    <servlet-class>ostepu.cconfig.cconfig</servlet-class>
     <load-on-startup>1</load-on-startup>
    <init-param>
        <param-name>listings</param-name>
        <param-value>false</param-value>
    </init-param>
    <init-param>
        <param-name>readonly</param-name>
        <param-value>false</param-value>
    </init-param>
</servlet>
```

``` XML
<servlet-mapping>
    <servlet-name>CCONFIG</servlet-name>
    <url-pattern>/info/*</url-pattern>
    <url-pattern>/help/*</url-pattern>
    <url-pattern>/control</url-pattern>
</servlet-mapping>
```

Wenn der Webservice auch als ``Verarbeitung`` zu einer Veranstaltung hinzu installiert werden können soll, dann müssen wir auf die Installation in eine Veranstaltung reagieren können.
Dazu registrieren wir noch die Pfade ``/course/*`` und ``/link/*``.

``` XML
<servlet>
    <servlet-name>PROCESS</servlet-name>
    <servlet-class>ostepu.process.process</servlet-class>
     <load-on-startup>3</load-on-startup>
    <init-param>
        <param-name>listings</param-name>
        <param-value>false</param-value>
    </init-param>
    <init-param>
        <param-name>readonly</param-name>
        <param-value>false</param-value>
    </init-param>
</servlet>
```

``` XML
<servlet-mapping>
    <servlet-name>PROCESS</servlet-name>
    <url-pattern>/course/*</url-pattern>
    <url-pattern>/link/*</url-pattern>
</servlet-mapping>
```

Damit OSTEPU uns auch als Erweiterung für eine Veranstaltung auf dem Schirm hat, müssen wir die ``data/Component.json`` des Webservice noch
um einen ``connector`` erweitern (wird dort unter connector hinzugefügt.

``` JSON
"connector": [
    {
        "name": "extension",
        "target": "LExtension"
    }
```
