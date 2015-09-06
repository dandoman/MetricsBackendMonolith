Steps:

Build war: mvn clean compile package test -P ${env} (dev/uat/staging/prod)

Run locally: mvn tomcat7:run

Deploy to server:

On server:
rm ~/MetricsService.war
rm -rf /usr/share/tomcat7/webapps/MetricsService*
sudo cp ~/Metricservice.war /usr/share/tomcat7/webapps/
sudo chown ec2-user /usr/share/tomcat7/webapps/MetricsService.war
sudo rm -rf /usr/share/tomcat7/webapps/MetricsService

Connect to db with psql

psql -d integ -U integ -h integ.cezc1rn98646.us-west-2.rds.amazonaws.com -W