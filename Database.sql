-- dropping everything first in case --
DROP DATABASE IF EXISTS gp_information_system;
-- creating the database and we call it gp_booking_system --
CREATE DATABASE  gp_information_system;

-- using our  gp_information_system database --
USE gp_information_system;

-- creating a doctor information table --
CREATE TABLE Doctor_Information (
    Doctor_ID VARCHAR(10) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    Surname VARCHAR(50) NOT NULL,
    DOB DATE NOT NULL,
    Specialisation VARCHAR(100) NOT NULL,
    Address TEXT NOT NULL,
    Phone_Number VARCHAR(15)
);
-- Create PatientInformation table --
CREATE TABLE PatientInformation (
    FirstName VARCHAR(50),
    Surname VARCHAR(50),
    DOB DATE,
    Occupation VARCHAR(50),
    Address VARCHAR(50),
    Emergency_Contact VARCHAR(50),
    Assigned_Doctor VARCHAR(50),
    Patient_ID VARCHAR(50) PRIMARY KEY,
    Allergies VARCHAR(50),
    Past_Surgeries VARCHAR(50),
    Conditions VARCHAR(50),
    Phone_Number VARCHAR(50),
    Gender VARCHAR(10),
    FOREIGN KEY (Assigned_Doctor) REFERENCES Doctor_Information(Doctor_ID)
);

-- creating the  BookingInformation table --
CREATE TABLE BookingInformation (
    FirstName VARCHAR(50),
    Surname VARCHAR(50),
    Doctor_Availability VARCHAR(50),
    Date_of_Booking DATE,
    Assigned_Doctor VARCHAR(50),
    Patient_ID VARCHAR(50),
    Time_of_Booking TIME,
    Booking_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (Patient_ID) REFERENCES PatientInformation (Patient_ID),
    FOREIGN KEY (Assigned_Doctor) REFERENCES Doctor_Information (Doctor_ID)
);


-- creating the  Admin Information Table --
CREATE TABLE AdminInformation(
    FirstName VARCHAR(255) NOT NULL,
    Surname VARCHAR(255) NOT NULL,
    Admin_ID VARCHAR(255) PRIMARY KEY
);

-- creating admin login table to check --
CREATE TABLE AdminLogin (
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    FOREIGN KEY (Username) REFERENCES AdminInformation (Admin_ID)
);

-- Inserting data into Doctor_Information --
INSERT INTO Doctor_Information (FirstName, Surname, DOB, Specialisation, Address, Doctor_ID, Phone_Number)
VALUES
('Brad', 'Lewis', '1970-04-12', 'General Medicine', '101 Health Rd, London, UK', 'D001', '+44 7894 123456'),
('Francis', 'Barrett', '1965-09-22', 'Neurology', '202 Brain Rd, Manchester, UK', 'D002', '+44 7412 890123'),
('Julian', 'Francis', '1980-06-15', 'Orthopedics', '303 Joint Ln, Liverpool, UK', 'D003', '+44 7700 654321'),
('Louis', 'John', '1973-07-05', 'Rheumatology', '1 Spine Way, London, UK', 'D004', '+44 7812 345678'),
('Amelia', 'Goddard', '1970-02-12', 'Neurology', '10 Neuro Ln, Oxford, UK', 'D005', '+44 7911 111222'),
('Rachael', 'Bradley', '1978-08-09', 'Psychiatry', '7 Mind Rd, York, UK', 'D006', '+44 7766 550011'),
('Naomi', 'Phillips', '1985-04-23', 'Gastroenterology', '15 Gut Ave, Bristol, UK', 'D007', '+44 7755 330022'),
('Amelia', 'Russell', '1982-01-19', 'Hematology', '9 Blood St, Leeds, UK', 'D008', '+44 7712 221122');

-- Inserting patients fake data --
INSERT INTO PatientInformation (FirstName, Surname, DOB, Occupation, Address, Emergency_Contact, Assigned_Doctor, Patient_ID, Allergies, Past_Surgeries, Conditions, Phone_Number, Gender)
VALUES
('Amber', 'Coleman', '2003-01-20', 'Personnel officer', '016 Smith field, Ianshire, KA3 3XJ', '+44121 496 0863', 'D001', 'AC-0839KJ', 'Eggs', 'Hernia repair', 'Psoriasis', '+441154960606', 'Female'),
('Rosie', 'Carter', '1940-11-22', 'Retired', '18 Miller inlet, North Frances, HR87 3GX', '+44121 496 0373', 'D002', 'RC-7783HU', 'Mold', 'Hip replacement', 'Multiple sclerosis', '0292018397', 'Female'),
('Edward', 'West', '1948-12-03', 'Retired', 'Flat 0, Clark hill, Derekberg, B51 9HP', '+44117496023', 'D003', 'ER-4527PP', 'Soy', 'Knee replacement', 'Eczema', '01414960322', 'Male'),
('Luke', 'David', '1983-05-13', 'Radio broadcast assistant', '0 Declan pike, West Cameron, E6 3ZW', '+442920180081', 'D004', 'LD-7900IJ', 'none', 'none', 'Multiple sclerosis', '+441184860606', 'Male'),
('Hazel', 'Turner', '1966-10-01', 'Control and instrumentation engineer', 'Flat 31z, Amanda rapids, New Sara, NR74 8AP', '+441144960694', 'D005', 'HT-67790OK', 'Peanuts', 'Laparoscopic hysterectomy', 'Epilepsy', '+441164730793', 'Female'),
('Timothy', 'Stokes', '1977-11-06', 'Petroleum engineer', '66 Howard island, South Patriciaville, N68 5GY', '+441174930695', 'D005', 'TS-5534BN', 'none', 'none', 'Asthma', '+441284760604', 'Male'),
('Kathleen', 'Storey', '1975-10-16', 'Trade mark attorney', 'Flat 1, Stewart island, Declanland, N63', '+441473930595', 'D006', 'KS-3221TY', 'Lactose', 'Kidney replacement', 'Immunodeficiency', '+447700900123', 'Female'),
('Gali', 'Bradley', '1975-11-03', 'Horticultural therapist', '777 Todd cliff, Georgeland, S0E 0PY', '+442425050893', 'D006', 'GB-6109BN', 'none', 'none', 'Anxiety disorder', '+441144960109', 'Female'),
('Michelle', 'Carter', '1965-10-16', 'Aeronautical engineer', '4 Woods groves, South Brenda, S1J', '+43214430372', 'D007', 'MC-5009RW', 'none', 'none', 'Ulcerative Colitis', '+447911123456', 'Female'),
('Naz', 'Reiz', '1970-10-01', 'Police officer', 'Flat 1, Stewart island, Declanland, N63 7ED', '01184960868', 'D008', 'NR-6702TV', 'none', 'Blood transfusion', 'Sickle Cell Disease', '07777654321', 'Male');

-- ADDING FAKE BOOKING INFORMATION --
INSERT INTO BookingInformation (FirstName, Surname, Doctor_Availability, Date_of_Booking, Assigned_Doctor, Patient_ID, Time_of_Booking) VALUES
('Rosie', 'Carter', 'Available', '2026-01-10', 'D002', 'RC-7783HU', '09:30:00'),
('Edward', 'West', 'Available', '2026-01-15', 'D003', 'ER-4527PP', '11:00:00');


-- Fake Admin user  --
INSERT INTO AdminInformation (FirstName, Surname, Admin_ID)
VALUES ('Margaret', 'Lever', 'Admin');

-- Admin login fake data --
INSERT INTO AdminLogin (Username, Password)
VALUES ('Admin', 'Password');


-- TESTING DATABASE QUERIES: LOCATING DOCTORS FROM DOCTOR INFORMATION WHO LIVE/ ARE BASED IN LONDON
SELECT * FROM Doctor_Information WHERE Address LIKE '%London%';

-- TESTING DATABASE QUERIES: JOINING A TABLE WITH DOCTORS AND THEIR PATIENTS(FIRST NAME, SURNAME)
SELECT P.FirstName AS PatientFirstName, P.Surname AS PatientSurname, D.FirstName AS DoctorFirstName, D.Surname AS DoctorSurname
FROM Doctor_Information AS D
JOIN PatientInformation AS P
ON D.Assigned_Patient = P.Patient_ID;


