DROP DATABASE employees;

CREATE DATABASE employees; 

\connect employees

CREATE SCHEMA data;

CREATE TABLE employee (
    employee_id SERIAL NOT NULL PRIMARY KEY ON DELETE CASCADE,
    full_name text NOT NULL,
    position text NOT NULL,
    adress text,
    date_of_birth date NOT NULL,
    education text,
    education_degree text,
    phone_number varchar(11) NOT NULL,
    work_experience integer NOT NULL, 
    CONSTRAINT correct_work_experience CHECK (work_experience >= 0 AND work_experience <= 100)
);

CREATE TYPE status AS ENUM (
    'Активно', 'Не активно'
);

CREATE TABLE project (
    project_id serial NOT NULL PRIMARY KEY ON DELETE CASCADE,
    project_name text NOT NULL,
    project_status status NOT NULL,
    start_date date NOT NULL,
    finish_date date,
    CONSTRAINT finish_after_start CHECK (finish_date IS NULL OR finish_date >= start_date)
);

CREATE TYPE policy_type AS ENUM (
    'По роли в проекте', 'По должности', 'По особому случаю'
);

CREATE TABLE payment_policy(
    policy_id serial NOT NULL PRIMARY KEY ON DELETE CASCADE,
    policy_type policy_type NOT NULL,
    position text,
    project_id integer REFERENCES project,
    project_role text,
    special_occasion text,
    payment numeric NOT NULL,
    status status NOT NULL,
    CONSTRAINT positive_payment CHECK(payment > 0),
    CONSTRAINT correct_policy CHECK ((position is not NULL and project_id is NULL and project_role is NULL and special_occasion is NULL) or
                                 (project_id is not NULL and position is NULL and special_occasion is NULL) or
                                 (special_occasion is not NULL and position is NULL and project_id is NULL and project_role is NULL))
);

CREATE TABLE assign_payment (
    assign_id serial NOT NULL PRIMARY KEY,
    employee_id integer NOT NULL REFERENCES employee,
    policy_id integer NOT NULL REFERENCES payment_policy,
    payment numeric NOT NULL,
    payment_date date NOT NULL,
    CONSTRAINT positive_payment CHECK(payment > 0)
);

CREATE TABLE assign_to_project (
    assign_id serial NOT NULL PRIMARY KEY,
    employee_id integer NOT NULL REFERENCES employee,
    project_id integer NOT NULL REFERENCES project,
    project_role text NOT NULL,
    assign_status status NOT NULL,
    start_date date NOT NULL,
    finish_date date,
    CONSTRAINT finish_after_start CHECK (finish_date IS NULL OR finish_date >= start_date)
);

CREATE FUNCTION check_position() RETURNS trigger AS $check_position$
    BEGIN
        IF NOT EXISTS(
			SELECT * FROM payment_policy
            WHERE payment_policy.position = NEW.position 
        ) THEN
            RAISE EXCEPTION 'This position is out of payment policies';
        END IF;

        RETURN NEW; 
            
    END;
$check_position$ LANGUAGE plpgsql;

CREATE FUNCTION check_role() RETURNS trigger AS $check_role$
    BEGIN
        IF NOT EXISTS(
			SELECT * FROM payment_policy
            WHERE payment_policy.project_id = NEW.project_id AND  
                  payment_policy.project_role = NEW.project_role
        ) THEN
            RAISE EXCEPTION 'This role is out of payment policies';
        END IF;

        RETURN NEW; 
            
    END;
$check_role$ LANGUAGE plpgsql;
	
CREATE TRIGGER check_position
BEFORE INSERT OR UPDATE OF position ON employee
FOR EACH ROW
EXECUTE FUNCTION check_position();

CREATE TRIGGER check_role
BEFORE INSERT OR UPDATE OF project_role ON assign_to_project
FOR EACH ROW
EXECUTE FUNCTION check_role();

ALTER DATABASE employees SET search_path=data;