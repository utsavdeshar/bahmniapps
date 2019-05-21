CREATE TABLE IF NOT EXISTS FHIR_RESOURCE ( 
	id serial PRIMARY KEY not null, 
	ts TIMESTAMP, version VARCHAR(5), 
	resource_type VARCHAR(10),
	status VARCHAR(20), 
	resource JSONB );

  