## 码农社区

### 资料
[Spring 文档](https://spring.io.guides)  
[Spring Web](https://spring.io.guides/gs/serving-web-content/)   
[Bootstrap](https://v3.bootcss.com/)  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app)  
[Spring Boot文档](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/)  
[MyBatis-Spring-Boot-Starter](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[Lombok](https://projectlombok.org/setup/maven)  
[Spring MVC文档](https://docs.spring.io/spring-framework/docs/4.2.4.RELEASE/spring-framework-reference/html/mvc.html)

### 使用到的技术栈
SpringBoot  
Git / Github  
Bootstrap(设计前端样式)  
Github Authorization(登录方式)  
IDEA(开发工具)  
Refactor(重构)  
Maven(项目管理)  
MySQL & H2(数据库)  
MyBatis  
Properties(Spring MVC配置文件)  
Session & Cookies  
Dev Tools(热部署)  
Pagination(分页展示)  
Interceptor(拦截器)  
MBG(快速生成SQL)  
Exception Handler(异常处理)  
Markdown(支持markdown格式)

### 工具
[Git](https://git-scm.com/download)  
[Visual-paradigm](https://www.visual-paradigm.com)

#### 相关命令
git add .  
git commit -m "message"  
git push  
mvn flyway:migrate  
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate  

### 部署
#### 依赖
- git
- jdk
- maven
- mysql

#### 步骤
- yum update
- yum install git
- mkdir App
- cd App
- git clone git@github.com:ChenxiLiLi/community.git
- yum install maven 
- mvn clean compile package
- mvn -v
- java -version
- cp src/main/resources/application.properties src/main/resources/application-production.properties
- mvn package 重新打包
- mvn clean compile flyway:migrate 使用flyway创建数据库 
- mvn clean compile flyway:repair 修复数据库问题 
- java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar 运行项目
- ps -aux | grep java 检查当前进程是否存在
- 39.107.236.198 公网IP
- mvn clean compile flyway:migrate -Pdev 使用本地的profile配置

