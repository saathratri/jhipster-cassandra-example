
CREATE TABLE IF NOT EXISTS saathratri_entity (
    entity_id uuid,
    entity_name text,
    entity_description text,
    entity_cost decimal,
    created_id uuid,
    created_time_id uuid,
  primary key (entity_id)
);
