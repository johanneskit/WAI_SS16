Also es klappt alles so wie es soll.
Man kann sich einfach registrieren und das pw wird gehasht auf der DB gespeichert.
Beim Anmelden wird das pw gehasht und mit dem auf der DB verglichen.
ich habe einen user angelegt mit login: admin und pw: admin.
Dort wo die Weiterleitung zu einer anderen Seite stattfinden würde habe ich text ausgeben lassen.
Prioritaet ist momentan bei jedem user 3 und id wird noch keine vergeben.

Was ich noch als Problem sehe:

1.  Ich konnte beim Abfragen von Werten aus der DB keine PreparedStatements benutzen, weil ich immer
	Fehlermeldungen begleitet von Abstürzen des Programms bekam. Beim Einpflegen in die DB gab es keine
	Probleme komischerweise.

2.	Man kann in den Links immer die kompletten User-Daten(Name, Passwort) sehen.
	Finde ich persönlich nicht so schön.

3.	was mich wenig stört ist das die Oberfläche noch sehr einfach gehalten ist und man sehen kann,
	dass es vom w_beans Beispiel kommt.

4.	Statt jedes mal eine Verbindung aufzubauen kann man auch das JNDI verwenden.
	Ist mir leider erst am Ende aufgefallen. Ist an sich auch nicht so schlimm.
	Macht von der Funktionalität her keinen Unterschied.
	
Falls am Ende noch Puffer ist würde ich mich noch um die Punkte 1&2 und evtl. 3&4 kümmern.
Ich werde mich als nächstes um die Auswahlseite für den Admin kümmern.