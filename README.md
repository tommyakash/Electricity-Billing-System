# Electricity Billing System

![Java](https://img.shields.io/badge/Language-Java-red)
![JavaFX](https://img.shields.io/badge/GUI-JavaFX-blue)
![License](https://img.shields.io/badge/License-Open%20Source-blue)

## Overview
The **Electricity Billing System** is a Java-based application with a **JavaFX GUI**, designed to manage electricity billing efficiently.  
It allows storing user information, calculating electricity bills, and maintaining records in a persistent manner.

---

## Features
- Add and manage user details.
- Calculate electricity bills based on consumption.
- Store data persistently in a file (`users.dat`).
- Graphical user interface using JavaFX.
- Easy execution with `run.bat` or VS Code.

---

## Technologies Used
- **Java** – Object-Oriented Programming concepts.
- **JavaFX** – GUI interface.
- **File Handling** – Storing and retrieving user records.
- **VS Code** – IDE for development.
- **Batch Script (`run.bat`)** – Quick execution on Windows.

---

## Project Structure
ElectricityBillingSystem/
│
├─ ElectricityBillingSystem.java # Main Java source file
├─ run.bat # Batch script to run the program
├─ users.dat # Data file for storing user records
├─ .gitignore # Git ignore configuration
└─ README.md # Project documentation

## How to Run

### Using VS Code
1. Open the project in **VS Code**.
2. Make sure **JavaFX SDK** is installed and configured in VS Code.
3. Run `ElectricityBillingSystem.java` directly from the editor.  

### Using Command Line
1. Make sure JavaFX SDK is downloaded (e.g., `javafx-sdk-24.0.2`).
2. Compile the program:

```bash
javac --module-path path_to_javafx_lib --add-modules javafx.controls,javafx.fxml ElectricityBillingSystem.java
Run the program:


Copy code
java --module-path path_to_javafx_lib --add-modules javafx.controls,javafx.fxml ElectricityBillingSystem
Replace path_to_javafx_lib with the path to your JavaFX SDK lib folder.

Using run.bat
You can modify run.bat to include JavaFX module path if needed.

Notes
Compiled .class files and the JavaFX SDK folder are ignored in Git.

Ensure Java and JavaFX are correctly set up in your environment variables or VS Code.

License
This project is open source and available for educational and personal use.

Copy code

If you want, I can also **write a ready-to-use `run.bat` file** for Windows that automatically runs the JavaFX application, so you don’t need to type the module path every time.  

Do you want me to do that?

