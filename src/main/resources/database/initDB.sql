CREATE TABLE IF NOT EXISTS movies
(
    id    SERIAL PRIMARY KEY ,
    name  VARCHAR,
    year BIGINT,
    imdb  VARCHAR,
    imdb_rating double precision
);