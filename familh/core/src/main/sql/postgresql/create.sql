--------------------------------------------------------------------------------
-- Create schema
--------------------------------------------------------------------------------

-- familh_user
create sequence familh_user_sequence increment by 1 start 1;

create table familh_user (
    user_id                 integer                     not null
  , email                   character varying(255)      not null
  , firstname               character varying(255)
  , lastname                character varying(255)
  , login                   character varying(32)
  , password                character varying(255)

  , version                 smallint default 0
  , created                 timestamp                   not null
  , updated                 timestamp                   not null

  , constraint user_pkey primary key (user_id)
);

-- family tree
create sequence family_tree_sequence increment by 1 start 1;

create table family_tree (
    family_tree_id          integer                     not null
  , ident                   character varying(12)
  , user_id                 integer                     not null

  , version                 smallint default 0
  , created                 timestamp                   not null
  , updated                 timestamp                   not null

  , constraint family_tree_pkey primary key (family_tree_id)
  , constraint family_tree_familh_user_type foreign key (user_id) references familh_user (user_id)
);

-- sex
create table sex (
    sex_id              character varying(1) not null
  , label               character varying(255) not null
  , constraint sex_pkey primary key (sex_id)
);

-- person
create sequence person_sequence increment by 1 start 1;

create table person (
    person_id               integer                     not null
  , ident                   character varying(12)
  , sex_id                  character varying(1)
  , name_type_id            character varying(1)

  , family_tree_id          integer                     not null
  , user_id                 integer                     not null

  , version                 smallint default 0
  , created                 timestamp                   not null
  , updated                 timestamp                   not null

  , constraint person_pkey primary key (person_id)
  , constraint sex_person foreign key (sex_id) references sex (sex_id)
  , constraint person_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint person_family foreign key (family_tree_id) references family_tree (family_tree_id)
);

-- family
create sequence family_sequence increment by 1 start 1;

create table family (
    family_id               integer                     not null
  , ident                   character varying(12)

  , family_tree_id          integer                     not null
  , user_id                 integer                     not null

  , version                 smallint default 0
  , created                 timestamp                   not null
  , updated                 timestamp                   not null

  , constraint family_pkey primary key (family_id)
  , constraint family_familh_user foreign key (user_id) references familh_user (user_id)
  , constraint family_family foreign key (family_tree_id) references family_tree (family_tree_id)
);

-- children
create table children (
    family_id               integer                     not null
  , person_id               integer                     not null

  , constraint children_pkey primary key (family_id, person_id)
  , constraint children_family foreign key (family_id) references family (family_id)
  , constraint children_person foreign key (person_id) references person (person_id)
);

-- spouse role
create table spouse_role (
    spouse_role_id      character varying(1) not null
  , label               character varying(255) not null
  , constraint spouse_role_pkey primary key (spouse_role_id)
);

-- spouse
create table spouse (
    family_id               integer                     not null
  , person_id               integer                     not null
  , spouse_role_id          character varying(1)        not null

  , constraint spouse_pkey primary key (family_id, person_id)
  , constraint spouse_family foreign key (family_id) references family (family_id)
  , constraint spouse_person foreign key (person_id) references person (person_id)
);

-- name type
create table name_type (
    name_type_id        character varying(1) not null
  , label               character varying(255) not null

  , constraint name_type_pkey primary key (name_type_id)
);


-- name
create sequence name_sequence increment by 1 start 1;

create table name (
    name_id                 integer                     not null
  , firstname               character varying(255)
  , lastname                character varying(255)
  , name_type_id            character varying(1)
  , person_id               integer
  , user_id                 integer                     not null

  , version                 smallint default 0
  , created                 timestamp                   not null
  , updated                 timestamp                   not null

  , constraint name_pkey primary key (name_id)
  , constraint name_name_type foreign key (name_type_id) references name_type (name_type_id)
  , constraint name_person foreign key (person_id) references person (person_id)
  , constraint person_familh_user_type foreign key (user_id) references familh_user (user_id)
);

-- event type
create table event_type (
    event_type_id       character varying(4) not null
  , label               character varying(255) not null
  , constraint event_type_pkey primary key (event_type_id)
);

-- event
create sequence event_sequence increment by 1 start 1;

create table event (
    event_id                integer                     not null
  , ident                   character varying(12)
  , event_type_id           character varying(4)
  , person_id               integer                     null
  , user_id                 integer                     not null

  , version                 smallint default 0
  , created                 timestamp                   not null
  , updated                 timestamp                   not null

  , constraint event_pkey primary key (event_id)
  , constraint name_event_type foreign key (event_type_id) references event_type (event_type_id)
  , constraint person_familh_user_type foreign key (user_id) references familh_user (user_id)
);

-- event involved person role
create table event_involved_person_role (
    event_involved_person_role_id   character varying(4) not null
  , label                           character varying(255) not null
  , constraint event_involved_person_role_pkey primary key (event_involved_person_role_id)
);

-- event involved person

create table event_involved_person (
    event_id                        integer                     not null
  , person_id                       integer                     not null
  , event_involved_person_role_id   character varying(4)        not null


  , constraint event_involved_person_pkey primary key (event_id, person_id)
  , constraint event_person_role_type foreign key (event_involved_person_role_id) references event_involved_person_role (event_involved_person_role_id)
);