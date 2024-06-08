create table CARS
(
    ID             serial primary key,
    MODEL          varchar(100) not null,
    BRAND          varchar(50)  not null,
    ENGINE         varchar(50)  not null,
    FUEL_TYPE      varchar(50)  not null,
    DOORS          int          not null,
    COLOR          varchar(50)  not null,
    TRANSMISSION   varchar(50)  not null,
    SEATS          int          not null,
    YEAR           int          not null,
    LICENSE_PLATE  varchar(50)  not null,
    PRICE          float        not null,
    AVERAGE_RATING float,
    REVIEWS_COUNT  int,
    PICTURE_PATH varchar(100)
);
create table USERS
(
    ID              serial primary key,
    NAME            varchar(100) not null,
    LAST_NAME       varchar(100) not null,
    EMAIL           varchar(100) not null,
    PHONE           varchar(100) not null,
    PROFILE_PICTURE_PATH varchar(100),
    USERNAME        varchar(100) not null,
    PASSWORD        varchar(200) not null,
    ROLE            varchar(50)  not null
);
create table RATINGS
(
    ID        serial primary key,
    RATING    int  not null,
    COMMENT   text,
    TIMESTAMP date not null,
    CAR_ID    int  not null,
    USER_ID   int  not null,
    constraint FK_CARS foreign key (CAR_ID) references CARS (ID),
    constraint FK_USERS foreign key (USER_ID) references USERS (ID)
);
create table BOOKINGS
(
    ID         serial primary key,
    NAME       varchar(50)  not null,
    LAST_NAME  varchar(50)  not null,
    EMAIL      varchar(50)  not null,
    PHONE      varchar(50)  not null,
    START_DATE date         not null,
    END_DATE   date         not null,
    TIMESTAMP  date         not null,
    STATUS     varchar(100) not null,
    TOTAL      float        not null,
    RATING_ID  int,
    CAR_ID     int          not null,
    USER_ID    int          not null,
    foreign key (RATING_ID) references RATINGS (ID) on delete cascade,
    constraint FK_CARS foreign key (CAR_ID) references CARS (ID),
    constraint FK_USERS foreign key (USER_ID) references USERS (ID)
);
create table FLYWAY_SCHEMA_HISTORY
(
    "installed_rank" int           not null,
    "version"        varchar(50),
    "description"    varchar(200)  not null,
    "type"           varchar(20)   not null,
    "script"         varchar(1000) not null,
    "checksum"       int,
    "installed_by"   varchar(100)  not null,
    "installed_on"   timestamp     not null default current_timestamp,
    "execution_time" int           not null,
    "success"        boolean       not null
);