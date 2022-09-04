CREATE TABLE films (
    id          serial PRIMARY KEY,
    title       varchar(40) NOT NULL,
    did         integer NOT NULL,
    date_prod   date,
    kind        varchar(40)
);


CREATE TABLE cinemas (
    id          serial PRIMARY KEY,
    title       varchar(40) NOT NULL,
    extra json not null
);
