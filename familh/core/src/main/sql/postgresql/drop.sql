-- event involved person
drop table if exists event_involved_person cascade;

-- event involved person role
drop table if exists event_involved_person_role cascade;

-- event
drop sequence if exists event_sequence;
drop table if exists event cascade;

-- event type
drop table if exists event_type cascade;

-- name
drop sequence if exists name_sequence;
drop table if exists name cascade;

-- name-type
drop table if exists name_type cascade;

-- spouse
drop table if exists spouse cascade;

-- spouse role
drop table if exists spouse_role cascade;

-- children
drop table if exists children cascade;

-- family
drop sequence if exists family_sequence;
drop table if exists family cascade;

-- person
drop sequence if exists person_sequence;
drop table if exists person cascade;

-- sex
drop table if exists sex cascade;

-- family tree
drop sequence if exists family_tree_sequence;
drop table if exists family_tree cascade;

-- familh_user
drop sequence if exists familh_user_sequence;
drop table if exists familh_user cascade;
