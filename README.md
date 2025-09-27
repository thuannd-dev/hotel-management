# Hotel Management Project

## Introduction
This is a hotel management project built with Java EE, JSP, HTML, CSS, and uses Owl Carousel for dynamic UI components. The project is intended for learning purposes and demonstrates basic features of a hotel website.

## Implemented Features
- Modern, responsive homepage layout.
- Sections: About, Blog, Gallery, Home, Rooms, Service, Testimonials.
- Owl Carousel used for gallery, testimonials, facilities, etc.
- Each carousel uses a separate class to avoid configuration conflicts.
- Optimized CSS/JS imports to ensure good performance on various screen sizes.

## Issues Encountered and Solutions
- **Owl Carousel Conflict:** All carousels used the same .owl-carousel class, causing the last script to override the configuration and display only one image. Fixed by assigning separate classes such as .gallery-carousel, .testimonials-carousel, .facilities-carousel and initializing each carousel with its own script.
- **Image Path Error:** Some places used public/images instead of public/image, causing images not to display. Fixed by correcting all paths to match the folder structure.

## Folder Structure
- `web/public/image/`: Contains all images used on the website.
- `web/public/css/`: Contains style.css.
- `web/public/js/`: Contains JS files such as jquery.countdown.js.
- `web/WEB-INF/views/`: Contains JSP files for each section and layout.
- `src/`: Contains Java source code.

## Build and Run Instructions
1. Open the project with NetBeans or any Java EE-supported IDE.
2. Build the project using Ant or your IDE.
3. Deploy to a server (Tomcat).
4. Access via browser to check the interface and features.

## Notes
- If you encounter image or carousel errors, check the class names and paths.
- Files in the build/ folder will be updated automatically when you rebuild the project.

---
**Author:** Thuận Đẹp Trai
**Review Date:** 28/09/2025
