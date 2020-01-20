-- !Ups

CREATE TABLE school_classes
(
    id                int          NOT NULL AUTO_INCREMENT,
    grade             int          NOT NULL,
    name              varchar(255) NOT NULL,
    workbook_language varchar(10)  NOT NULL,
    is_operational    boolean      NOT NULL,
    for_school        int          NOT NULL,
    ruid              varchar(100) NOT NULL,
    last_update_time  timestamp    NOT NULL,
    is_deleted        boolean      NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE school_classes;
