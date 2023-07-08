CREATE TABLE IF NOT EXISTS "users" (
  "user_id" integer PRIMARY KEY,
  "name" varchar,
  "e_mail" varchar,
  "login" varchar,
  "birthday" date
);

CREATE TABLE IF NOT EXISTS  "films" (
  "fiilm_id" integer PRIMARY KEY,
  "name" varchar,
  "description" varchar(200),
  "release_date" date,
  "likes_id" integer,
  "genre_id" integer,
  "mpa_id" integer
);

CREATE TABLE IF NOT EXISTS  "mpa" (
  "mpa_id" integer PRIMARY KEY,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS  "friends_id" (
  "user_id" integer PRIMARY KEY,
  "is_friends" boolean,
  "friends_id" integer
);

CREATE TABLE IF NOT EXISTS  "genre" (
  "genre_id" int PRIMARY KEY,
  "genre_name" varchar
);

CREATE TABLE IF NOT EXISTS  "likes" (
  "likes_id" integer PRIMARY KEY,
  "user_id" integer
);

ALTER TABLE "likes" ADD FOREIGN KEY ("likes_id") REFERENCES "films" ("likes_id");

ALTER TABLE "friends_id" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

CREATE TABLE IF NOT EXISTS "films_genre" (
  "films_genre_id" integer,
  "genre_genre_id" int,
  PRIMARY KEY ("films_genre_id", "genre_genre_id")
);

ALTER TABLE "films_genre" ADD FOREIGN KEY ("films_genre_id") REFERENCES "films" ("genre_id");

ALTER TABLE "films_genre" ADD FOREIGN KEY ("genre_genre_id") REFERENCES "genre" ("genre_id");


ALTER TABLE "mpa" ADD FOREIGN KEY ("mpa_id") REFERENCES "films" ("mpa_id");
