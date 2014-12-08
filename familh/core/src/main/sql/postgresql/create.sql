--------------------------------------------------------------------------------
-- Create schema
--------------------------------------------------------------------------------

-- localized_label
create sequence localized_label_sequence increment by 1 start 1;

create table localized_label (
    localized_label_ident               integer                     not null
  , locale                              character varying(8)        not null
  , label                               character varying(256)      not null

  , constraint localized_label_pkey primary key (localized_label_ident, locale)
);

-- familh_user_role
create table familh_user_role (
    role_id                             character varying(1)        not null
  , label                               character varying(256)      not null
  , constraint familh_user_role_pkey primary key (role_id)
);

-- TODO Add uniq constraint on email and nickname
-- familh_user
create sequence familh_user_sequence increment by 1 start 1;

create table familh_user (
    user_id                             integer                     not null
  , email                               character varying(256)      not null
  , firstname                           character varying(32)
  , lastname                            character varying(32)
  , nickname                            character varying(32)       not null
  , login                               character varying(32)
  , password                            character varying(32)
  , role_id                             character varying(1)        not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint user_pkey primary key (user_id)
  , constraint user_role foreign key (role_id) references familh_user_role (role_id)
);

-- access
create table access (
    access_id                           character varying(1)        not null
  , label                               character varying(256)      not null
  , constraint access_pkey primary key (access_id)
);

-- family tree
create sequence family_tree_sequence increment by 1 start 1;

create table family_tree (
    family_tree_id                      integer                     not null
  , ident                               character varying(12)
  , user_id                             integer                     not null
  , access_id                           character varying(1)        not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint family_tree_pkey primary key (family_tree_id)
  , constraint family_tree_familh_user_type foreign key (user_id) references familh_user (user_id)
  , constraint access_person foreign key (access_id) references access (access_id)
);

-- sex
create table sex (
    sex_id                              character varying(1)        not null
  , label                               character varying(256)      not null
  , constraint sex_pkey primary key (sex_id)
);

-- person
create sequence person_sequence increment by 1 start 1;

create table person (
    person_id                           integer                     not null
  , ident                               character varying(12)
  , sex_id                              character varying(1)
  , name_type_id                        character varying(1)

  , family_tree_id                      integer                     not null
  , user_id                             integer                     not null
  , access_id                           character varying(1)        not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint person_pkey primary key (person_id)
  , constraint sex_person foreign key (sex_id) references sex (sex_id)
  , constraint person_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint person_family foreign key (family_tree_id) references family_tree (family_tree_id)
  , constraint access_person foreign key (access_id) references access (access_id)
);

-- family
create sequence family_sequence increment by 1 start 1;

create table family (
    family_id                           integer                     not null
  , ident                               character varying(12)

  , family_tree_id                      integer                     not null
  , user_id                             integer                     not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint family_pkey primary key (family_id)
  , constraint family_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint family_family foreign key (family_tree_id) references family_tree (family_tree_id)
);

-- children_type
create table child_type (
    child_type_id                       character varying(1)        not null
  , label                               character varying(256)      not null
  , constraint child_type_pkey primary key (child_type_id)
);

-- children
create table child (
    family_id                           integer                     not null
  , person_id                           integer                     not null
  , child_type_id                       character varying(1)        not null

  , constraint child_pkey primary key (family_id, person_id)
  , constraint child_family foreign key (family_id) references family (family_id)
  , constraint child_person foreign key (person_id) references person (person_id)
  , constraint child_type foreign key (child_type_id) references child_type (child_type_id)
);

-- spouse role
create table spouse_role (
    spouse_role_id                      character varying(1)        not null
  , label                               character varying(256)      not null
  , constraint spouse_role_pkey primary key (spouse_role_id)
);

-- spouse
create table spouse (
    family_id                           integer                     not null
  , person_id                           integer                     not null
  , spouse_role_id                      character varying(1)        not null

  , constraint spouse_pkey primary key (family_id, person_id)
  , constraint spouse_family foreign key (family_id) references family (family_id)
  , constraint spouse_person foreign key (person_id) references person (person_id)
  , constraint spouse_role foreign key (spouse_role_id) references spouse_role (spouse_role_id)
);

-- name type
create table name_type (
    name_type_id                        character varying(1)        not null
  , label                               character varying(256)      not null

  , constraint name_type_pkey primary key (name_type_id)
);


-- name
create sequence name_sequence increment by 1 start 1;

create table name (
    name_id                             integer                     not null
  , firstname                           character varying(256)
  , lastname                            character varying(256)
  , name_type_id                        character varying(1)
  , person_id                           integer
  , user_id                             integer                     not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint name_pkey primary key (name_id)
  , constraint name_name_type foreign key (name_type_id) references name_type (name_type_id)
  , constraint name_person foreign key (person_id) references person (person_id)
  , constraint person_familh_user_type foreign key (user_id) references familh_user (user_id)
);

-- territorial_organisation_type
--create sequence territorial_organisation_type_sequence increment by 1 start 1;

--create table territorial_organisation_type (
--    territorial_organisation_type_id    integer                     not null
--  , name_id                             integer                     not null
--  , parent_id                           integer                     null
--
--  , family_tree_id                      integer                     null
--  , user_id                             integer                     null
--
--  , version                             smallint default 0
--  , created                             timestamp                   not null    default now()
--  , updated                             timestamp                   not null    default now()
--
--  , constraint territorial_organisation_type_pkey primary key (territorial_organisation_type_id)
--  , constraint territorial_organisation_type_parent foreign key (parent_id) references territorial_organisation_type (territorial_organisation_type_id)
--  , constraint territorial_organisation_type_familh_user foreign key (user_id) references familh_user (user_id)
--  , constraint territorial_organisation_type_family_tree foreign key (family_tree_id) references family_tree (family_tree_id)
--  , constraint territorial_organisation_type_name foreign key (name_id) references localized_label (localized_label_ident)
--);

-- territorial_organisation
--create sequence territorial_organisation_sequence increment by 1 start 1;

--create table territorial_organisation (
--    territorial_organisation_id integer                     not null
--  , name_id                     integer                     not null
--  , type_id                     character varying(64)       not null
--  , parent_id                   integer                     null
--
--  , family_tree_id              integer                     null
--  , user_id                     integer                     null
--
--  , version                     smallint default 0
--  , created                             timestamp                   not null    default now()
--  , updated                             timestamp                   not null    default now()
--
--  , constraint territorial_organisation_pkey primary key (territorial_organisation_id)
--  , constraint territorial_organisation_parent foreign key (parent_id) references territorial_organisation (territorial_organisation_id)
--  , constraint territorial_organisation_familh_user foreign key (user_id) references familh_user (user_id)
--  , constraint territorial_organisation_family_tree foreign key (family_tree_id) references family_tree (family_tree_id)
--  , constraint territorial_organisation_name foreign key (name_id) references localized_label (localized_label_ident)
--  , constraint territorial_organisation_type foreign key (type_id) references localized_label (localized_label_ident)
--);


-- user_city
create table user_city (
    user_city_id                        integer                     not null
  , name_id                             integer                     not null

  , family_tree_id                      integer                     not null
  , user_id                             integer                     not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint user_city_pkey primary key (user_city_id)
  , constraint user_city_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint user_city_family_tree foreign key (family_tree_id) references family_tree (family_tree_id)
--  , constraint city_name foreign key (name_id) references localized_label (localized_label_ident)
);


-- Address
create table address (
    address_id                          integer                     not null
  , city_id                             integer                     not null

  , family_tree_id                      integer                     not null
  , user_id                             integer                     not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint address_pkey primary key (address_id)
  , constraint address_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint address_family_tree foreign key (family_tree_id) references family_tree (family_tree_id)
);

-- event type
create table event_type (
    event_type_id                       character varying(4)        not null
  , label                               character varying(256)      not null
  , constraint event_type_pkey primary key (event_type_id)
);

-- event
create sequence event_sequence increment by 1 start 1;

create table event (
    event_id                            integer                     not null
  , ident                               character varying(12)
  , event_type_id                       character varying(4)
  , person_id                           integer                     null
  , user_id                             integer                     not null

  , version                             smallint                                default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint event_pkey primary key (event_id)
  , constraint name_event_type foreign key (event_type_id) references event_type (event_type_id)
  , constraint person_familh_user_type foreign key (user_id) references familh_user (user_id)
);

-- event involved person role
create table event_involved_person_role (
    event_involved_person_role_id       character varying(4)        not null
  , label                               character varying(256)      not null
  , constraint event_involved_person_role_pkey primary key (event_involved_person_role_id)
);

-- event involved person

create table event_involved_person (
    event_id                            integer                     not null
  , person_id                           integer                     not null
  , event_involved_person_role_id       character varying(4)        not null


  , constraint event_involved_person_pkey primary key (event_id, person_id)
  , constraint event_person_role_type foreign key (event_involved_person_role_id) references event_involved_person_role (event_involved_person_role_id)
);

-- file format
create table file_format (
    file_format_id                      character varying(8)        not null
  , label                               character varying(256)      not null
  , constraint file_format_pkey primary key (file_format_id)
);

-- import
create table import (
    import_id                           integer                     not null
  , user_id                             integer                     not null
  , filename                            character varying(256)      not null
  , file_format_id                      character varying(8)        not null

  , version                             smallint                    not null    default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint import_pkey primary key (import_id)
  , constraint person_familh_user_type foreign key (user_id) references familh_user (user_id)
  , constraint import_file_format foreign key (file_format_id) references file_format (file_format_id)
);

-- Note
create sequence note_sequence increment by 1 start 1;

create table note (
    note_id                             integer                     not null
  , ident                               character varying(8)        null
  , content                             text                        not null

  , family_tree_id                      integer                     not null
  , user_id                             integer                     not null

  , version                             smallint                    not null    default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint note_pkey primary key (note_id)
  , constraint note_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint note_family foreign key (family_tree_id) references family_tree (family_tree_id)
);

-- TODO change content to binary lob
-- Document
create sequence document_sequence increment by 1 start 1;

create table document (
    document_id                         integer                     not null
  , ident                               character varying(8)        null
  , content                             text                        not null
  , contenttype                         character varying(64)       not null

  , family_tree_id                      integer                     not null
  , user_id                             integer                     not null

  , version                             smallint                    not null    default 0
  , created                             timestamp                   not null    default now()
  , updated                             timestamp                   not null    default now()

  , constraint document_pkey primary key (document_id)
  , constraint document_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint document_family foreign key (family_tree_id) references family_tree (family_tree_id)
);
