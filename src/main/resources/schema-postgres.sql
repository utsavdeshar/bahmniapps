-- DROP DATABASE IF EXISTS INSURANCE;
-- CREATE DATABASE INSURANCE;

-- \C INSURANCE;

CREATE USER insurance;
CREATE DATABASE insurance;
GRANT ALL PRIVILEGES ON database insurance to insurance;

Create schema insurance;

ALTER SCHEMA insurance OWNER TO insurance;

CREATE TABLE insurance.action (
    id numeric(10,0) NOT NULL,
    code character varying(10) NOT NULL,
    description character varying(256) NOT NULL,
    type character varying(10) NOT NULL,
    lastupdated timestamp(6) without time zone
);


ALTER TABLE insurance.action OWNER TO insurance;

DROP TABLE IF EXISTS FHIR_RESOURCE;
CREATE TABLE FHIR_RESOURCE ( 
	id serial PRIMARY KEY not null, 
	ts TIMESTAMP, version VARCHAR(5), 
	resource_type VARCHAR(10),
	status VARCHAR(20), 
	resource JSONB );
	
	ALTER TABLE insurance.FHIR_RESOURCE OWNER TO insurance;

  