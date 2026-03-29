
CREATE TABLE IF NOT EXISTS tag (
    id uuid,
    name text,
    description text,
    name_embedding VECTOR<FLOAT, 1536>,
    description_embedding VECTOR<FLOAT, 1536>,
  primary key (id)
);

CREATE CUSTOM INDEX IF NOT EXISTS tag_name_embedding_idx ON tag (name_embedding) USING 'StorageAttachedIndex';

CREATE CUSTOM INDEX IF NOT EXISTS tag_description_embedding_idx ON tag (description_embedding) USING 'StorageAttachedIndex';
