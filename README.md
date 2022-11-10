# adidas-app

This project allow to manage emails in a queue. We have these modules:<br/>
<br/>
<br/>
<b>base-domain</b>: It contains the bussines logic. Only interfaces, without implementation. It's our domain tier.<br/>
<b>base-infraestructure</b>: It contains features that depending of libraries about comunicatión like Kafka <br/>
<br/>
<b>adiclub-service</b>: Contains the implementation for managing members. It allows to creating, reading and deleting members.<br/>
<b>email-service</b>: It's the responsive to sending emails. <br/>
<b>priorite-sale-service</b>: It manage the priority of sending manage. It's posible to configuring how many emils send each minute. Also, it manages the table of sales<br/>

<br/>
<b>public-service</b>: It's the public interface to access our system.<br/>
<br/>
<b>admin-ui</b>: A simple interactive dashboard for managing ours tables.<br/>
<br/>
<b>server-config</b>: It use Spring-Cloud-Config for centralizating the system configuration. This configuration is in another repository: https://github.com/jorgerubira/adidas-config <br/>
<b>eureka-server</b>: It's a discoverer of service Rest. When our public-service start, eureka register his IP and Port for external applications <br/>

<hr/>
There is a file docker-composer for deploy this modules in a Docker. It's important server-config start before the other modules because this module has the configuratión.<br/>
If you want to practice, you can use the admin-ui:<br/>








