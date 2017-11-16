# --- !Ups
create table "team" ("teamName" VARCHAR NOT NULL UNIQUE,"fullCapacity" BOOLEAN NOT NULL,"teamID" SERIAL NOT NULL PRIMARY KEY);

create table "people" ("name" VARCHAR NOT NULL,"team" INTEGER,"personID" SERIAL NOT NULL PRIMARY KEY);
alter table "people" add constraint "team_FK" foreign key("team") references "team"("teamID") on update NO ACTION on delete NO ACTION;


insert into "team" ("teamName", "fullCapacity")  values ('EMAC', 'false');
--insert into "team" ("teamName", "fullCapacity")  values ('RATE', 'false');

insert into "people" ("name", "team")  values ('Charlie', '1');
--insert into "people" ("name", "team")  values ('Yen', '2');

# --- !Downs
alter table "people" drop constraint "team_FK";
drop table "people";

drop table "team";