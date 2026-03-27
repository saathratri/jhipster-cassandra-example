
CREATE TABLE IF NOT EXISTS blog (
    category text,
    blog_id uuid,
    handle text,
    content text,
  primary key (
    (category),
 blog_id  ))
  WITH CLUSTERING ORDER BY (
blog_id desc);
