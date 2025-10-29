# Hotel Management System  (Java Web Application)

[![Open Source Love svg1](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](#)
[![GitHub Issues](https://img.shields.io/github/issues/thuannd-dev/hotel-management.svg?style=flat&label=Issues&maxAge=2592000)](https://www.github.com/thuannd-dev/hotel-management/issues)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat&label=Contributions&colorA=red&colorB=black)](#)

A **fully responsive** web-based Hotel Management System based on the **Clean Architecture** made using **Java Servlets**, **Java Server Pages (JSPs)**. Moreover authentication and authorization for users is implemented using Tomcat Roles. The web-application is also secured against **SQL Injection**, **Cross-site Request Forgery** and **Cross-Site Scripting** attacks.

## Clean architecture
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture.png)

## Architectural approach
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture_layers.png)

## Architectural reactive approach
-----------------
![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture_layers_details.png)

## Technologies Used

- **Frontend:** HTML, CSS, JavaScript, Jquery, Tailwind CSS, Java Server Pages (JSPs), AJAX
- **Backend:** Java Servlets, Java Models, Microsoft Access (Database)
- **Webservices:** SOAP (Mailer), XML
- **Security Features:** SQL Injection, Cross-Site Scripting (XSS), Tomcat Roles, Cross-site Request Forgery (CSRF) Protection, Input Validations, Hashing Passwords...

## Roles and Responsibilities of Each Actor:

Following roles are implemented:

- **Guest:** Customer who books and uses hotel rooms and services.
- **Receptionist:** Manages bookings, check-ins, check-outs, and generates bills.
- **Service Staff:** Provides additional services (spa, dining, cleaning, etc.).
- **Housekeeping Staff:** Maintains and updates room status (clean/dirty).
- **Manager:** Oversees hotel operations, monitors reports and revenue.
- **Administrator (System Admin):** Maintains system, manages users, security, and configurations.

## Interface

#### Home Pages

<p align="middle">
   <img src="https://drive.usercontent.google.com/download?id=15BVtH4Rx3XbIKYLSpZP8X5Ybfeeax3sB" width="400"/>
   <img src="https://drive.usercontent.google.com/download?id=1mxgscLOFVNn6iiXVZ4cRzevX13YDE3gb" width="400"/>
</p>

<p align="middle">
   <img src="https://drive.usercontent.google.com/download?id=1gh9bnFnfFoV-MLaBSSgzgznQfJpSYT83" width="400"/>
   <img src="https://drive.usercontent.google.com/download?id=1Kg8u0CxGcMoouD4XqvnESMCe5K1FEwqg" width="400"/>
</p>

#### Guest Booking and Additional Services

<p align="middle">
   <img src="https://drive.usercontent.google.com/download?id=15LidRW2rOjh4si6gfplweQ71n8u_Or48" width="400"/>
   <img src="https://drive.usercontent.google.com/download?id=1JVwt5HQ6XV6vY1px_TFggrDmb_eP4Y83" width="400"/>
</p>

##  Installation
### Clone the Repository

To clone the repository, use the following command in your terminal:

```bash
git clone https://github.com/thuannd-dev/hotel-management.git
```
###  Prerequisites
| Tool            | Recommended Version |
|----------------|---------------------|
| JDK            | 8 or above          |
| Apache Tomcat  | 9.x or 10.x         |
| NetBeans       | 12+ (with Java EE support) |
| IntelliJ IDEA  | Ultimate Edition (supports Java EE) |

---

###  Run with NetBeans

#### Step 1: Open the project
- Open **NetBeans**.
- Go to **File → Open Project**.
- Select the project folder → **Open**.

#### Step 2: Configure Tomcat (if not already added)
- Go to **Tools → Servers → Add Server**.
- Select **Apache Tomcat** → Next.
- Choose the Tomcat installation directory → Finish.

#### Step 3: Run the project
- Right-click the project → **Run** (or press `F6`).
- NetBeans will deploy the project automatically.
- Open browser and visit:
  http://localhost:8080/hotel-management/

### Run with IntelliJ IDEA (Ultimate Edition)

> ⚠ IntelliJ IDEA Community Edition does **not support Java Servlet/JSP**, so you must use the **Ultimate Edition**.

#### Step 1: Import the project
- Open **IntelliJ IDEA**.
- Go to **File → Open**.
- Select the project folder → click **OK**.

#### Step 2: Configure Tomcat
- Go to **Run → Edit Configurations**.
- Click **+** → select **Tomcat Server → Local**.
- Click **Configure...**, then select the Tomcat installation folder.
- Go to **Deployment** tab → click **+** → select `war exploded`.
- Set the **Application context** (e.g. `/hotel-management`).

#### Step 3: Run
- Click **Run (Shift + F10)**.
- Open browser and visit:
  http://localhost:8080/hotel-management/

## Team Members

| Name                    	                                  | UniID      	   | The following role has already been implemented      	        | Position                      	|
|------------------------------------------------------------|----------------|---------------------------------------------------------------|-------------------------------	|
| [**Nguyễn Dương Thuận**](https://github.com/thuannd-dev) 	 | **SE196615** 	 | **Guest, Service Staff, Housekeeping Staff, Administrator** 	 | **Member** 	|
| [**Nguyễn Ngọc Bảo Châu**](https://github.com/BaoChau478)   | **SE192259** 	 | **Receptionist, Manager** 	                                          | **Team Leader**                    	|


## Contributions Welcome

[![GitHub Issues](https://img.shields.io/github/issues/thuannd-dev/hotel-management.svg?style=flat&label=Issues&maxAge=2592000)](https://www.github.com/thuannd-dev/hotel-management/issues)

If you find any bugs, have suggestions, or face issues:

- Open an Issue in the Issues Tab to discuss them.
- Submit a Pull Request to propose fixes or improvements.
- Review Pull Requests from other contributors to help maintain the project's quality and progress.

This project thrives on community collaboration! Members are encouraged to take the initiative, support one another, and actively engage in all aspects of the project. Whether it’s debugging, fixing issues, or brainstorming new ideas, your contributions are what keep this project moving forward.

With modern AI tools, solving challenges and contributing effectively is easier than ever. Let’s work together to make this project the best it can be! 🚀

## License

[![MIT](https://img.shields.io/cocoapods/l/AFNetworking.svg?style=style&label=License&maxAge=2592000)](../master/LICENSE)

## References

- **JSP**: [Viem](https://www.tutorialspoint.com/jsp/index.htm)
- **Servlet**: [Viem](https://www.tutorialspoint.com/servlets/index.htm)
- **General**: 
  - **DigitalOcean - Pankaj Kumar**: [Viem](https://www.digitalocean.com/community/tutorials/servlet-jsp-tutorial)
  - **Oracle**: [Viem](https://docs.oracle.com/javaee/7/api/javax/servlet/Servlet.html)

Copyright (c) 2025-present, thuannd-dev
