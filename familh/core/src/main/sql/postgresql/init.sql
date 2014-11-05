-- TODO translate
insert into child_type values ('L', 'database.child.type.legitimate');
insert into child_type values ('N', 'database.child.type.natural');


insert into spouse_role values ('F', 'database.spouse.role.father');
insert into spouse_role values ('M', 'database.spouse.role.mother');

insert into event_involved_person_role values ('OTHE', 'database.event.involved.person.other');
insert into event_involved_person_role values ('PRIN', 'database.event.involved.person.principal');
insert into event_involved_person_role values ('MAYO', 'database.event.involved.person.mayor');
insert into event_involved_person_role values ('WITN', 'database.event.involved.person.witness');

insert into event_type values ('DEAT', 'database.event.type.death');
insert into event_type values ('BIRT', 'database.event.type.birth');
insert into event_type values ('MARR', 'database.event.type.marriage');
insert into event_type values ('OTHE', 'database.event.type.other');

insert into name_type values ('B', 'database.name.type.birthName');
insert into name_type values ('M', 'database.name.type.marriedName');
insert into name_type values ('A', 'database.name.type.aka');

insert into access values ('P', 'database.access.private');
insert into access values ('O', 'database.access.protected');
insert into access values ('U', 'database.access.public');


insert into sex values ('M', 'database.sex.male');
insert into sex values ('F', 'database.sex.female');

insert into familh_user_role values ('A', 'database.user.role.administrator');
insert into familh_user_role values ('U', 'database.user.role.user');

insert into file_format values ('GED', 'database.file.format.gedcom');