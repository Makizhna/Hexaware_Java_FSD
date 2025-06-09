
create database careercrafter;
use careercrafter;


create table users (
    id int auto_increment primary key,
    email varchar(100) not null unique,
    password varchar(255) not null,
    role enum('job_seeker', 'employer') not null
);


create table job_seekers (
    user_id int primary key,
    name varchar(100) not null,
    dob date,
    phone varchar(15),
    education text,
    experience text,
    skills text,
    resume_url varchar(255),
    foreign key (user_id) references users(id) on delete cascade
);


create table employers (
    user_id int primary key,
    name varchar(100) not null,
    position varchar(100),
    company_name varchar(100),
    location varchar(100),
    foreign key (user_id) references users(id) on delete cascade
);

create table jobs (
    job_id int auto_increment primary key,
    title varchar(100) not null,
    description text not null,
    location varchar(100),
    posted_by int,
    foreign key (posted_by) references employers(user_id) on delete cascade
);


create table applications (
    id int auto_increment primary key,
    job_id int,
    job_seeker_id int,
    resume_url varchar(255),
    status varchar(50) default 'applied',
    foreign key (job_id) references jobs(job_id) on delete cascade,
    foreign key (job_seeker_id) references job_seekers(user_id) on delete cascade
);

create table interview_schedule (
    id int auto_increment primary key,
    application_id int,
    interview_date datetime,
    mode varchar(50),
    status varchar(50) default 'scheduled',
    foreign key (application_id) references applications(id) on delete cascade
);


create table saved_jobs (
    id int auto_increment primary key,
    job_seeker_id int,
    job_id int,
    foreign key (job_seeker_id) references job_seekers(user_id) on delete cascade,
    foreign key (job_id) references jobs(job_id) on delete cascade
);



insert into users (email, password, role) values
('ravikumar@gmail.com', 'ravi123', 'employer'),
('neha_singh@gmail.com', 'neha123', 'employer'),
('karthik12@gmail.com', 'karthik123', 'job_seeker'),
('poojamehta7890@gmail.com', 'pooja123', 'job_seeker');


insert into employers (user_id, name, position, company_name, location) values
(1, 'Ravi Kumar', 'HR Manager', 'TechNova', 'Bangalore'),
(2, 'Neha Singh', 'Recruiter', 'MuSigma', 'Hyderabad');


insert into job_seekers (user_id, name, dob, phone, education, experience, skills, resume_url) values
(3, 'Karthik R', '1997-03-12', '9876543210', 'B.Tech CSE', '2 years in Java', 'Java, Spring Boot', 'resumes/karthik_resume.pdf'),
(4, 'Pooja Mehta', '1999-07-28', '9123456789', 'MCA', '1 year in React', 'React, JavaScript', 'resumes/pooja_resume.pdf');


insert into jobs (title, description, location, posted_by) values
('Java Developer', 'Work on Spring Boot APIs.', 'Chennai', 1),
('React Frontend Developer', 'Build UI in React.', 'Hyderabad', 2);


insert into applications (job_id, job_seeker_id, resume_url, status) values
(1, 3, 'resumes/karthik_resume.pdf', 'under review'),
(2, 4, 'resumes/pooja_resume.pdf', 'applied');

insert into interview_schedule (application_id, interview_date, mode, status) values
(1, '2025-06-10 10:00:00', 'online', 'scheduled'),
(2, '2025-06-12 15:30:00', 'offline', 'scheduled');


insert into saved_jobs (job_seeker_id, job_id) values
(3, 1),
(4, 2);


