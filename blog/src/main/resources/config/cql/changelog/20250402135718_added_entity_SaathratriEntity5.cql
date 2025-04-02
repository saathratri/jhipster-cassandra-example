
CREATE TABLE IF NOT EXISTS saathratri_entity_5 (
    organization_id uuid,
    entity_type text,
    entity_id uuid,
    add_on_id uuid,
    add_on_type text,
      add_on_details_text map<text, text>,
      add_on_details_decimal map<text, decimal>,
      add_on_details_boolean map<text, boolean>,
          add_on_details_big_int map<text, bigint>,  
  primary key (
    (organization_id, entity_type, entity_id),
 add_on_id  ))
  WITH CLUSTERING ORDER BY (
add_on_id desc);
