It's a mini-project based on Servlet API and JSP. Main purpose is to complete study of these two technologies.
As database was used Apache Derby 10.8 and as container - Apache Tomcat 7. Files are located as in the container.
To deploy application specify property "tomcat" to Tomcat directory where is also webapps subdirectory
in build-properties.xml file. Also specify property "servlet-api" to where is lib of Servlet API. In Ant start "setup".
It creates a file structure as needed for Tomcat, creates a database, tables and populates them, and moves structure
to Tomcat directory. To clean after webapp start "clean". Passwords for users are the same as their login names (admin admin).
Features of the mini-shop:
  - There are three types of user: administrator, manager, customer.
  - Administrator can register, remove administrators, managers and remove customers.
  - Administrator and manager can add, remove and edit items.
  - Customer can add/remove items to/from basket and form orders.
  - Only customer have basket.
  - User can edit himself.
  - Administrator can edit everybody.

August 5-20th, 2014.
