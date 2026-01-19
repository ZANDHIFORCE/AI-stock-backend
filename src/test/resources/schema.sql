CREATE TABLE IF NOT EXISTS pre_stock (
    ticker        VARCHAR(50) NOT NULL,
    date          VARCHAR(20) NOT NULL,
    id            VARCHAR(20) NOT NULL,
    name          VARCHAR(100),
    cur_price     VARCHAR(20),
    predict_price VARCHAR(20),
    predict_gap   VARCHAR(20),
    use_yn        VARCHAR(1) DEFAULT 'Y',
    PRIMARY KEY (ticker, date, id)
);