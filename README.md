# rest-base
Based on the spring boot built web infrastructure, including the commonly used functions in web development, such as: cache (redis), log, transaction, JPA, shiro, security, common tools, swagger2 online interface documentation, cross-domain support, etc. Rapid project development within the company based on the project.

# Operation mode
1. Create a database base locally, and adjust the mysql and redis configurations in application-local.properties.
2. After creating the database, run BaseApplication directly. After the project starts, execute init.sql and index.sql in the doc directory in the database.
3. Log in at http://localhost:8070/login/submit?username=admin&pwd=admin2017
4. Visit http://localhost:8070/swagger-ui.html to view the interface documentation.
5. Access the http://localhost:8070/admin/user/list test interface;

# redis installation method
$ wget http://download.redis.io/releases/redis-4.0.2.tar.gz
$ tar xzf redis-4.0.2.tar.gz
$ cd redis-4.0.2
$ make

Modify the Redis configuration file redis.conf and change the configuration:
Daemonize yes
Requirepass test!@#$%

$ src/redis-server
Src/redis-cli


#简书博客
Http://www.jianshu.com/u/1182bf416662

# csdn博客
Http://blog.csdn.net/u014411966