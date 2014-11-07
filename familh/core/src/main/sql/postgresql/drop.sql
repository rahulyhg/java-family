-- note
drop sequence if exists note_sequence;
drop table if exists note cascade;

-- file_format
drop table if exists file_format cascade;

-- import
drop table if exists import cascade;

-- event involved person
drop table if exists event_involved_person cascade;

-- event involved person role
drop table if exists event_involved_person_role cascade;

-- event
drop sequence if exists event_sequence;
drop table if exists event cascade;

-- address
drop sequence if exists address_sequence;
drop table if exists address cascade;

-- user city
drop table if exists user_city cascade;

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

-- child
drop table if exists child cascade;

-- child_type
drop table if exists child_type cascade;

-- family
drop sequence if exists family_sequence;
drop table if exists family cascade;

-- person
drop sequence if exists person_sequence;
drop table if exists person cascade;

-- access
drop table if exists access cascade;

-- sex
drop table if exists sex cascade;

-- family tree
drop sequence if exists family_tree_sequence;
drop table if exists family_tree cascade;

-- familh_user
drop sequence if exists familh_user_sequence;
drop table if exists familh_user cascade;

-- familh_user_role
drop table if exists familh_user_role cascade;

-- localized_label
drop sequence if exists localized_label_sequence;
drop table if exists localized_label cascade;
