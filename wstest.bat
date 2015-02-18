echo off

set BASE=C:\projects\AEPSIWSTestClient\

rem - the output path is set here instead of the beans.xml so we can inject it into the beans and also into Log4j.
set OUTPATH=C:/projectdata/brookes/AEPSIWSTestClient/

set PBASE=%BASE%bin\
set CBASE=%PBASE%lib\
set WSCP=%BASE%config;%PBASE%classes;%CBASE%activation.jar;%CBASE%aepsiWS.jar;%CBASE%aopalliance.jar;%CBASE%commons-logging.jar;%CBASE%cxf-2.1.1.jar;%CBASE%geronimo-annotation_1.0_spec-1.1.1.jar;%CBASE%geronimo-ws-metadata_2.0_spec-1.1.2.jar;%CBASE%jaxb-impl-2.1.6.jar;%CBASE%jaxws-api.jar;%CBASE%jsdk-24.jar;%CBASE%log4j-1.2.13.jar;%CBASE%mail.jar;%CBASE%neethi-2.0.4.jar;%CBASE%saaj-api-1.3.jar;%CBASE%saaj-impl-1.3.jar;%CBASE%spring-context-2.0.8.jar;%CBASE%spring-web.jar;%CBASE%spring.jar;%CBASE%wsdl4j-1.6.1.jar;%CBASE%wstest.jar;%CBASE%wstx-asl-3.2.4.jar;%CBASE%xml-resolver-1.2.jar;%CBASE%XmlSchema-1.4.2.jar;
java -Dlogger.path=%OUTPATH% -Djava.endorsed.dirs=%BASE%endorsed -cp %WSCP% com.brookes.webservices.test.TestWS
echo on