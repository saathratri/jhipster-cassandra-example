
CREATE TABLE IF NOT EXISTS set_entity_by_organization (
    organization_id uuid,
    tags set<text>,
  primary key (organization_id)
);
