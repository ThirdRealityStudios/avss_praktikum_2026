# Praktikum SS 2026 Termin 2 (Loan Management System)

## About

Goal is it to advance the loan management system from the 1st appointment.

## Getting started

### Project Structure

According to the appointment exercise,
a Layered Architecture / Schichtenarchitektur is required in different implementations / ways.

### GitLab Token For IDE Use

glpat-IkCQeZPpDcGwEnkG7dYQCm86MQp1OjN0Nwk.01.0z01ekrbd

### Troubleshooting For Windows 11 Users (Maven Certificate Issue)

Download the certificate from git.fh-muenster.de (using your browser e.g.).
You wil receive a .pem from the CA (which is normally enough).

Then execute the following command:

```
"%JAVA_HOME%\bin\keytool.exe" -importcert ^ -alias harica-root ^ -file "%JAVA_HOME%\bin\git-fh-muenster-de.pem" ^ -keystore "%JAVA_HOME%\lib\security\cacerts" ^ -storepass changeit
```

Make sure %JAVA_HOME% is set properly and your IDE has set correct language levels and JDK paths.

### Exercise PDF

[Download here](artifact/26SS-SWA-P02%20Schichtenarchitekturen.pdf)

### UML Component Diagram

![alt text](artifact/UML_Component_Diagram_JPG.jpg)

### UML Sequence Diagram

![alt text](artifact/UML_Sequence_Diagram_JPG.jpg)

## Rating Of Diagrams (ChatGPT)

This evaluation applies only to the UML design artifacts created for practical assignment part 2
(component diagram and sequence diagram). Source code implementation is not part of this evaluation.

### Evaluation Summary

Strengths:
- clear separation of presentation, application and data layer responsibilities
- consistent request-response interaction model across all layers
- correct use of DTO abstractions for inter-layer communication
- presentation responsibilities (console input/output) remain isolated in the presentation layer
- component and sequence diagrams are architecturally aligned
- inverted dependency direction required by the assignment is explicitly respected

Possible Improvements:
- replace generic `update()` operations with explicit request/response method names
- split large controller components into smaller service-oriented components
- add a short architectural note explaining the intentionally inverted dependency model

### Estimated UML Grade (Exercise 2 only)

Architecture quality: very good  
UML consistency: very good  
Layer separation: very good  
Documentation clarity: good to very good

Estimated overall grade: **1.3 - 1.7** (German grading scale)

Assumption:
final UML notation (interfaces, stereotypes, dependencies and message flows) is formally correct.