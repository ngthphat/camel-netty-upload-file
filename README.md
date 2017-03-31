# camel-netty-upload-file

Apache Camel is one of my favorite open source frameworks in the Java environment. It enables easy integration of different applications which use several protocols and technologies. You can use Java, Spring XML, Scala or Groovy. Almost every technology you can imagine is available, for example, HTTP, FTP, JMS, EJB, JPA, RMI, JMS, JMX, LDAP, Netty, and much more . Besides, own custom components can be created very easily.

This example shows you how to handle multipart/form-data in camel. Assume that we need to write an application that allows user to upload multiple reports via RESTful Api. To solve this requirement, we need complete steps below:
1) Tell camel which component we want to handle REST request
2) Define REST signature 
3) Define a processor to extract data
4) Write unit test
5) Write Main class and test with Postman
