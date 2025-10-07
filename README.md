# Hotel Management Project

## Issues Encountered and Solutions
- **Image Path Error:** Some places used public/images instead of public/image, causing images not to display. Fixed by correcting all paths to match the folder structure.
- **ClassNotFoundException: com.microsoft.sqlserver.jdbc.SQLServerDriver Error:** Please add jdbc.jar to the lib of tomcat. Because i use pool connection and it managed by tomcat.
- **java.lang.ClassNotFoundException: org.apache.jsp.home_jsp Description The server encountered an unexpected condition that prevented it from fulfilling the request.** : Please read notes below to fix it.
## Notes
- You must use  session="false" in jsp file to avoid container (Tomcat) create session when load JSP
- Ex: 
```<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>```
- And may be you meet error because you want to get session from request but session is null.
- Ex: ```java.lang.ClassNotFoundException: org.apache.jsp.home_jsp Description The server encountered an unexpected condition that prevented it from fulfilling the request.```
- So you should get session like this:```HttpSession session = request.getSession(false);```
- If you use ```request.getSession()``` it will auto create session if session is null
- So you should use ```request.getSession(false)``` to avoid auto create session
- I don't want auto create session, because i create a authentication filter to check login status by check exist session id in browser. If you haven't login, you will be redirect to login page with a error message.
- You should test in anonymous tab, beacause some browser have Session Restore feature.

![img_1.png](img_1.png)
---
**Author:** Thuận Đẹp Trai
