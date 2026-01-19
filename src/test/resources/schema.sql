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