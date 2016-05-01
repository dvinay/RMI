# RMI
<ul>
<li>Sample Remote Method Invocation Code - Calling another JVM functions(RMI)</li>
<li>Frontend was developed in JAVA UI components(JFrame).</li>
<li>Backend was developed in JAVA+Mysql Database.</li>
</ul>

# How to run?
<ul>
<li>Import project into eclipse IDE</li>
<li>Create a database(I used MySql port number 8889) name studentdb </li>
<li>Create a table name scores(name varchar(10), score int(5)) </li>
<li>Run database server - make sure all database connections are properly set</li>
<li>Run RegisterWithRMIServer.java file ==> It will creates RMI server(register JVM calls into RMI Registry)</li>
<li>Run StudentServerInterfaceClient.java file ==> It opens a small window application</li>
</ul>
