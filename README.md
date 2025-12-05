# Project Overview

A comprehensive contact management system with role-based access control, implementing the four pillars of Object-Oriented Programming (OOP) with MySQL database integration. The system features colorful terminal output, Turkish character support, ASCII animations, and input validation.
# Table of Contents

- [Project Overview](#project-overview)
- [Table of Contents](#table-of-contents)
- [Instruction to Start Using the Application](#instructions-to-start-using-the-application)
- [Generating the Javadoc](#generating-the-javadoc)
# Instructions to Start Using the Application

First, clone and cd into the repository.

```bash
git clone https://github.com/mekecemre/CMPE-343-OOP-Project-2.git
cd CMPE-343-OOP-Project-2/
```
## Prerequisites to Run the Project

- JDK 8 or higher
- MySQL Connector (already included)
- MySQL for Windows, MariaDB for Linux
## Instruction For Windows
### Install MySQL and Start the Service

1. Download MySQL Installer from: https://dev.mysql.com/downloads/installer/
2. Run the installer and follow the steps of the installation wizard. (You can choose the defaults and keep selecting next.)
3. Set root password when prompted
4. Start MySQL service by typing in CMD the following:

```cmd
   net start MySQL80
```
### Add MySQL Installation Path to the PATH Environment Variable

1. Right-click on the **Start** button and select **System**.
2. Click on **Advanced system settings** on the left.
3. In the Advanced tab, click on the **Environment Variables** button.
4. In the Environment Variables window, locate the **Path** variable in the **System variables** section and select it, then click **Edit**.
5. Click **New** and add the path to the MySQL `bin` directory, for example: `C:\Program Files\MySQL\MySQL Server X.X\bin`
6. Click **OK** to close the dialogs and apply the changes.
7. Open a new CMD Window for the change to take effect
### Create the Database User (Optional, as it's already included within the group5.sql file)

Execute the following in CMD:

```bash
mysql -u root -p
# Enter the root password you set during installation
```

Enter the following when prompted:

```sql
-- Create user for the application (REQUIRED by project specs)
CREATE USER 'myuser'@'localhost' IDENTIFIED BY '1234';

-- Grant privileges on the contact_management database (REQUIRED)
GRANT ALL PRIVILEGES ON contact_management.* TO 'myuser'@'localhost';

-- Apply the changes (REQUIRED)
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```
### Import Database Schema and Data (Optional, as it's already handled)

**Note:** The database `contact_management` will be created automatically by the SQL script.

```bash
# Navigate to project directory
cd CMPE-343-OOP-Project-2

# Import the SQL file (REQUIRED)
mysql -u myuser -p1234 < group5.sql

# Verify import (VERIFICATION - optional but recommended)
mysql -u myuser -p1234 -e "USE contact_management; SELECT COUNT(*) as users FROM users; SELECT COUNT(*) as contacts FROM contacts;"
```

Expected output if verification is successful:
- users: 4
- contacts: 56
### Compile and Run the Project

```cmd
compile.bat
or
.\compile.bat
```

```cmd 
run.bat
or
.\run.bat
```
## Instructions For Arch Linux
### Install MariaDB and Start the Service

```bash
# Install MariaDB (MySQL compatible)
sudo pacman -S mariadb
# Or using AUR helper:
paru -S mariadb

# Initialize the MySQL data directory (REQUIRED - do this before first start)
sudo mariadb-install-db --user=mysql --basedir=/usr --datadir=/var/lib/mysql

# Start MariaDB service (REQUIRED - if not already running)
sudo systemctl start mariadb

# Enable MariaDB to start on boot (REQUIRED)
sudo systemctl enable mariadb

# Verify it's running (VERIFICATION - optional but recommended)
sudo systemctl status mariadb
# Should show: "Active: active (running)"

# Secure the installation (OPTIONAL - recommended for security)
# If you run this, you'll use: sudo mysql -u root -p (with password prompt)
# If you skip this, you'll use: sudo mysql (without password)
sudo mysql_secure_installation
# Answer: Y (set root password), Y (remove anonymous users), 
#         Y (disallow root login remotely), Y (remove test database), 
#         Y (reload privilege tables)
```
### Create the Database User

Execute the following in your terminal:

```bash
sudo mysql
```

Enter the following when prompted:

```sql
-- Create user for the application (REQUIRED by project specs)
CREATE USER 'myuser'@'localhost' IDENTIFIED BY '1234';

-- Grant privileges on the contact_management database (REQUIRED)
GRANT ALL PRIVILEGES ON contact_management.* TO 'myuser'@'localhost';

-- Apply the changes (REQUIRED)
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```
### Import Database Schema and Data

**Note:** The database `contact_management` will be created automatically by the SQL script.

```bash
# Navigate to project directory
cd CMPE-343-OOP-Project-2

# Import the SQL file (REQUIRED)
mysql -u myuser -p1234 < group5.sql

# Verify import (VERIFICATION - optional but recommended)
mysql -u myuser -p1234 -e "USE contact_management; SELECT COUNT(*) as users FROM users; SELECT COUNT(*) as contacts FROM contacts;"
```

Expected output if verification is successful:
- users: 4
- contacts: 56
### Compile and Run the Project

```bash
./compile.sh
```

```bash
./run.sh
```
# Generating the Javadoc
## Linux

```bash
# cd into the /src directory
cd CMPE-343-OOP-Project-2/src

# Generate the Javadoc
javadoc -d ../docs \
    -encoding UTF-8 \
    -charset UTF-8 \
    -docencoding UTF-8 \
    -author -version \
    Main.java models/*.java managers/*.java utils/*.java roles/*.java

# Javadoc will be generated in docs/ directory
# Open docs/index.html in a browser
```
## Windows

- cd into the src folder and generate it

```cmd
cd CMPE-343-OOP-Project-2\src
javadoc -d ..\docs -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -author -version Main.java models\*.java managers\*.java utils\*.java roles\*.java
```
