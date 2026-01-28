CREATE TABLE IF NOT EXISTS stock (
    ticker        VARCHAR(50) NOT NULL,
    date          VARCHAR(20) NOT NULL,
    id            VARCHAR(20) NOT NULL,
    name          VARCHAR(100),
    curprice      VARCHAR(20),
    predictprice  VARCHAR(20),
    predictgap    VARCHAR(20),
    useyn         VARCHAR(1) DEFAULT 'Y',
    PRIMARY KEY (ticker, date, id)
);

CREATE TABLE IF NOT EXISTS users (
   userid varchar NOT NULL,
   "password" varchar NOT NULL,
   email varchar NOT NULL,
   nickname varchar NOT NULL,
   "role" varchar DEFAULT 'USER' NOT NULL,
   createdd varchar NULL,
   updatedd varchar NULL,
   birthdd varchar NOT NULL,
   gender varchar NOT NULL,
   "name" varchar NOT NULL,
   CONSTRAINT users_unique UNIQUE (userid, "password", "name", email)
);
