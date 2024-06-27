CREATE TABLE notification
(
    id          INT PRIMARY KEY,
    notifier_id VARCHAR(20) NOT NULL REFERENCES member (id),
    listener_id VARCHAR(20) NOT NULL REFERENCES member (id),
    message     VARCHAR(255)                       NOT NULL,
    type        VARCHAR(30)                        NOT NULL,
    source_uri  VARCHAR(255)                       NOT NULL,
    read_status TINYINT                            NOT NULL,
    create_date DATETIME                           NOT NULL
)