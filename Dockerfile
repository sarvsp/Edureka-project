#Use centOS 7.9 as the base image
FROM centos:7.9.2009

# Install software-properties-common package required fontconfig, OpenJDK 11 JRE, and OpenJDK 11 JDK for yum repository
RUN yum update -y && \
    yum install -y epel-release && \
    yum install -y java-11-openjdk-devel fontconfig wget && \
    yum clean all

# Set environment variables
ENV CATALINA_HOME /opt/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

# Download and extract Apache Tomcat
WORKDIR /tmp
RUN wget -q https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.19/bin/apache-tomcat-10.1.19.tar.gz  && \
    tar -xf apache-tomcat-10.1.19.tar.gz -C /opt && \
    mv /opt/apache-tomcat-10.1.19 $CATALINA_HOME && \
    rm -f apache-tomcat-10.1.19.tar.gz

# Copy the WAR file to the webapps directory
COPY taregt/ABCtechnologies-1.0.war $CATALINA_HOME/webapps/

# Expose port 8080
EXPOSE 8080

# Start Tomcat
ENTRYPOINT ["catalina.sh", "run"]

