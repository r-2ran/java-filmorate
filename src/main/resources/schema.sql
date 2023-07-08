CREATE TABLE IF NOT EXISTS "users" (
  "user_id" integer,
  "name" varchar,
  "e_mail" varchar,
  "login" varchar,
  "birthday" date
);

CREATE TABLE IF NOT EXISTS "films" (
  "film_id" integer,
  "name" varchar,
  "description" varchar(200),
  "release_date" date,
  "likes_id" integer,
  "genre_id" integer,
  "mpa_id" integer
);

CREATE TABLE IF NOT EXISTS "mpa" (
  "mpa_id" integer,
  "mpa_name" varchar
 );
