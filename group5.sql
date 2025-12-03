-- ============================================================================
-- CMPE-343 Project 2: Role-Based Contact Management System
-- Database Schema and Sample Data
-- Group: 5
-- ============================================================================

-- Drop existing database if exists and create new one
DROP DATABASE IF EXISTS contact_management;
CREATE DATABASE contact_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE contact_management;

-- ============================================================================
-- TABLE: users
-- Stores user accounts with hashed passwords and role information
-- ============================================================================
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================================================
-- TABLE: contacts
-- Stores contact information with comprehensive fields
-- ============================================================================
CREATE TABLE contacts (
    contact_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name VARCHAR(100) NOT NULL,
    nickname VARCHAR(100),
    phone_primary VARCHAR(20) NOT NULL,
    phone_secondary VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE,
    linkedin_url VARCHAR(255),
    birth_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_first_name (first_name),
    INDEX idx_last_name (last_name),
    INDEX idx_email (email),
    INDEX idx_phone (phone_primary),
    INDEX idx_birth_date (birth_date)
) ENGINE=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================================================
-- INSERT REQUIRED USERS
-- Password hashes are simple SHA-256 hashes of the passwords for simplicity
-- tt:tt, jd:jd, sd:sd, man:man
-- ============================================================================

INSERT INTO users (username, password_hash, name, surname, role) VALUES
('tt', '0e07cf830957701d43c183f1515f63e6b68027e528f43ef52b1527a520ddec82', 'Ahmet', 'Yılmaz', 'Tester'),
('jd', 'ad3e69e9aa860657cc6476770fe253d08198746b9fcf9dc3186b47eb85c30335', 'Ayşe', 'Demir', 'Junior Developer'),
('sd', '03042cf8100db386818cee4ff0f2972431a62ed78edbd09ac08accfabbefd818', 'Mehmet', 'Öztürk', 'Senior Developer'),
('man', '48b676e2b107da679512b793d5fd4cc4329f0c7c17a97cf6e0e3d1005b600b03', 'Zeynep', 'Şahin', 'Manager');

-- ============================================================================
-- INSERT 50+ SAMPLE CONTACTS
-- Includes Turkish names and various realistic data
-- ============================================================================

INSERT INTO contacts (first_name, middle_name, last_name, nickname, phone_primary, phone_secondary, email, linkedin_url, birth_date) VALUES
('Ahmet', NULL, 'Yılmaz', 'Memo', '05551234567', '02161234567', 'ahmet.yilmaz@email.com', 'https://www.linkedin.com/in/ahmetyilmaz', '1990-03-15'),
('Mehmet', 'Ali', 'Kaya', 'Meto', '05552345678', NULL, 'mehmet.kaya@email.com', 'https://www.linkedin.com/in/mehmetkaya', '1985-07-22'),
('Ayşe', NULL, 'Demir', 'Ayşe', '05553456789', '02162345678', 'ayse.demir@email.com', NULL, '1992-11-08'),
('Fatma', 'Nur', 'Çelik', 'Fatma', '05554567890', NULL, 'fatma.celik@email.com', 'https://www.linkedin.com/in/fatmacelik', '1988-05-17'),
('Ali', NULL, 'Yıldız', 'Ali', '05555678901', '02163456789', 'ali.yildiz@email.com', NULL, '1995-09-30'),
('Zeynep', NULL, 'Şahin', 'Zeyno', '05556789012', NULL, 'zeynep.sahin@email.com', 'https://www.linkedin.com/in/zeynepsahin', '1991-12-25'),
('Mustafa', 'Kemal', 'Aydın', 'Mustafa', '05557890123', '02164567890', 'mustafa.aydin@email.com', 'https://www.linkedin.com/in/mustafaaydin', '1987-02-14'),
('Emine', NULL, 'Arslan', 'Emine', '05558901234', NULL, 'emine.arslan@email.com', NULL, '1993-06-19'),
('Hasan', NULL, 'Polat', 'Haso', '05559012345', '02165678901', 'hasan.polat@email.com', 'https://www.linkedin.com/in/hasanpolat', '1989-10-11'),
('Hatice', 'Sultan', 'Koç', 'Hatice', '05550123456', NULL, 'hatice.koc@email.com', NULL, '1994-04-28'),
('İbrahim', NULL, 'Kurt', 'İbo', '05551234560', '02166789012', 'ibrahim.kurt@email.com', 'https://www.linkedin.com/in/ibrahimkurt', '1986-08-05'),
('Elif', NULL, 'Özkan', 'Elif', '05552345671', NULL, 'elif.ozkan@email.com', 'https://www.linkedin.com/in/elifozkan', '1996-01-20'),
('Hüseyin', NULL, 'Özer', 'Hüso', '05553456782', '02167890123', 'huseyin.ozer@email.com', NULL, '1990-07-13'),
('Ramazan', 'Emre', 'Güneş', 'Rama', '05554567893', NULL, 'ramazan.gunes@email.com', 'https://www.linkedin.com/in/ramazangunes', '1992-03-09'),
('Selin', NULL, 'Işık', 'Selin', '05555678904', '02168901234', 'selin.isik@email.com', 'https://www.linkedin.com/in/selinisik', '1991-11-27'),
('Can', NULL, 'Yurt', 'Can', '05556789015', NULL, 'can.yurt@email.com', NULL, '1988-09-16'),
('Deniz', NULL, 'Aktaş', 'Deniz', '05557890126', '02169012345', 'deniz.aktas@email.com', 'https://www.linkedin.com/in/denizaktas', '1995-05-22'),
('Ece', 'Defne', 'Çakır', 'Ece', '05558901237', NULL, 'ece.cakir@email.com', NULL, '1993-02-18'),
('Burak', NULL, 'Taş', 'Burak', '05559012348', '02160123456', 'burak.tas@email.com', 'https://www.linkedin.com/in/buraktas', '1987-12-07'),
('Gizem', NULL, 'Yavuz', 'Gizem', '05550123467', NULL, 'gizem.yavuz@email.com', 'https://www.linkedin.com/in/gizemyavuz', '1994-08-31'),
('Emre', NULL, 'Erdoğan', 'Emre', '05551234578', '02161234560', 'emre.erdogan@email.com', NULL, '1990-06-14'),
('Betül', NULL, 'Aksoy', 'Betül', '05552345689', NULL, 'betul.aksoy@email.com', 'https://www.linkedin.com/in/betulaksoy', '1989-04-03'),
('Cem', 'Berk', 'Güler', 'Cem', '05553456790', '02162345671', 'cem.guler@email.com', 'https://www.linkedin.com/in/cemguler', '1997-10-29'),
('Dilara', NULL, 'Yılmaz', 'Dila', '05554567801', NULL, 'dilara.yilmaz@email.com', NULL, '1992-01-12'),
('Eren', NULL, 'Kara', 'Eren', '05555678912', '02163456782', 'eren.kara@email.com', 'https://www.linkedin.com/in/erenkara', '1986-07-25'),
('Gamze', NULL, 'Çetin', 'Gamze', '05556789023', NULL, 'gamze.cetin@email.com', NULL, '1995-03-19'),
('Oğuz', 'Kaan', 'Şeker', 'Oğuz', '05557890134', '02164567893', 'oguz.seker@email.com', 'https://www.linkedin.com/in/oguzseker', '1991-11-08'),
('Merve', NULL, 'Acar', 'Merve', '05558901245', NULL, 'merve.acar@email.com', 'https://www.linkedin.com/in/merveacar', '1988-05-23'),
('Kerem', NULL, 'Yüksel', 'Kerem', '05559012356', '02165678904', 'kerem.yuksel@email.com', NULL, '1994-09-17'),
('Seda', NULL, 'Öztürk', 'Seda', '05550123478', NULL, 'seda.ozturk@email.com', 'https://www.linkedin.com/in/sedaozturk', '1990-12-02'),
('Tolga', NULL, 'Demir', 'Tolga', '05551234589', '02166789015', 'tolga.demir@email.com', 'https://www.linkedin.com/in/tolgademir', '1987-02-28'),
('Neslihan', 'Pınar', 'Aydın', 'Nesli', '05552345690', NULL, 'neslihan.aydin@email.com', NULL, '1993-08-15'),
('Baran', NULL, 'Koçak', 'Baran', '05553456701', '02167890126', 'baran.kocak@email.com', 'https://www.linkedin.com/in/barankocak', '1989-06-21'),
('Canan', NULL, 'Özdemir', 'Canan', '05554567812', NULL, 'canan.ozdemir@email.com', NULL, '1996-10-07'),
('Derya', NULL, 'Türk', 'Derya', '05555678923', '02168901237', 'derya.turk@email.com', 'https://www.linkedin.com/in/deryaturk', '1991-04-13'),
('Barış', 'Can', 'Sezer', 'Barış', '05556789034', NULL, 'baris.sezer@email.com', 'https://www.linkedin.com/in/barissezer', '1985-11-30'),
('Tuğba', NULL, 'Kaplan', 'Tuğba', '05557890145', '02169012348', 'tugba.kaplan@email.com', NULL, '1992-07-04'),
('Furkan', NULL, 'Doğan', 'Furkan', '05558901256', NULL, 'furkan.dogan@email.com', 'https://www.linkedin.com/in/furkandogan', '1988-01-26'),
('Nazlı', NULL, 'Şen', 'Nazlı', '05559012367', '02160123478', 'nazli.sen@email.com', 'https://www.linkedin.com/in/nazlisen', '1995-09-11'),
('Serkan', NULL, 'Bayram', 'Serkan', '05550123489', NULL, 'serkan.bayram@email.com', NULL, '1990-05-19'),
('Pelin', 'Sude', 'Yaman', 'Pelin', '05551234590', '02161234578', 'pelin.yaman@email.com', 'https://www.linkedin.com/in/pelinyaman', '1987-12-14'),
('Onur', NULL, 'Arslan', 'Onur', '05552345601', NULL, 'onur.arslan@email.com', 'https://www.linkedin.com/in/onurarslan', '1994-03-27'),
('Burcu', NULL, 'Çolak', 'Burcu', '05553456712', '02162345689', 'burcu.colak@email.com', NULL, '1989-08-09'),
('Murat', NULL, 'Güven', 'Murat', '05554567823', NULL, 'murat.guven@email.com', 'https://www.linkedin.com/in/muratguven', '1993-02-05'),
('Ebru', NULL, 'Özkan', 'Ebru', '05555678934', '02163456790', 'ebru.ozkan@email.com', 'https://www.linkedin.com/in/ebruozkan', '1991-10-21'),
('Alper', 'Kaan', 'Yıldırım', 'Alper', '05556789045', NULL, 'alper.yildirim@email.com', NULL, '1986-06-18'),
('İpek', NULL, 'Özgür', 'İpek', '05557890156', '02164567801', 'ipek.ozgur@email.com', 'https://www.linkedin.com/in/ipekozgur', '1995-01-08'),
('Kaan', NULL, 'Çağlar', 'Kaan', '05558901267', NULL, 'kaan.caglar@email.com', 'https://www.linkedin.com/in/kaancaglar', '1992-11-24'),
('Pınar', NULL, 'Korkmaz', 'Pınar', '05559012378', '02165678912', 'pinar.korkmaz@email.com', NULL, '1988-07-16'),
('Sinan', NULL, 'Tekin', 'Sinan', '05550123490', NULL, 'sinan.tekin@email.com', 'https://www.linkedin.com/in/sinantekin', '1994-04-01'),
('Şebnem', 'Ece', 'Uzun', 'Şebnem', '05551234501', '02166789023', 'sebnem.uzun@email.com', 'https://www.linkedin.com/in/sebnemu', '1990-09-12'),
('Volkan', NULL, 'Akın', 'Volkan', '05552345612', NULL, 'volkan.akin@email.com', NULL, '1987-05-29'),
('Yasemin', NULL, 'Kılıç', 'Yasemin', '05553456723', '02167890134', 'yasemin.kilic@email.com', 'https://www.linkedin.com/in/yaseminkilic', '1993-12-06'),
('Özgür', NULL, 'Başaran', 'Özgür', '05554567834', NULL, 'ozgur.basaran@email.com', 'https://www.linkedin.com/in/ozgurbasaran', '1989-03-22'),
('Defne', NULL, 'Akar', 'Defne', '05555678945', '02168901245', 'defne.akar@email.com', NULL, '1996-08-14'),
('Umut', 'Barış', 'Durmaz', 'Umut', '05556789056', NULL, 'umut.durmaz@email.com', 'https://www.linkedin.com/in/umutdurmaz', '1991-02-11');

-- ============================================================================
-- VERIFICATION QUERIES
-- ============================================================================

-- Count users
SELECT 'Total Users:' as Info, COUNT(*) as Count FROM users;

-- Count contacts
SELECT 'Total Contacts:' as Info, COUNT(*) as Count FROM contacts;

-- Display all users
SELECT 'All Users:' as Info;
SELECT user_id, username, name, surname, role FROM users ORDER BY user_id;

-- Display contact statistics
SELECT 'Contact Statistics:' as Info;
SELECT
    COUNT(*) as total_contacts,
    COUNT(linkedin_url) as with_linkedin,
    COUNT(*) - COUNT(linkedin_url) as without_linkedin,
    ROUND(AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())), 1) as average_age
FROM contacts;

-- ============================================================================
-- FIX: GRANT PERMISSIONS TO 'myuser'
-- This part gives the Java code permission to access the database
-- ============================================================================

-- 1. Just give the permissions (The user already exists)
GRANT ALL PRIVILEGES ON contact_management.* TO 'myuser'@'localhost';

-- 2. Save the changes
FLUSH PRIVILEGES;

-- ============================================================================
-- END OF SQL FILE
-- ============================================================================
