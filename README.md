# Praktikum SS 2026 Termin 1 (Darstellung-Komponente)

## GitLab Token For IDE Use

glpat-IkCQeZPpDcGwEnkG7dYQCm86MQp1OjN0Nwk.01.0z01ekrbd

## Create Or Change Your settings.xml

in

```
~/.m2/settings.xml
```

with following content:

```
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns:="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		<server>
			<id>gitlab-fhms</id>
			<username>gitlab+deploy-token</username>
			<password>...</password>
		</server>
	</servers>
</settings>
```